package infrastructure.runner;

import framework.router.commandline.CommandLineRouter;
import infrastructure.startup.ApplicationStartup;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service(value = "ApplicationCommandLineRunner")
@Profile("cli")
public class ApplicationCommandLineRunner implements ApplicationRunner {
    private final CommandLineRouter commandLineRouter;

    public ApplicationCommandLineRunner(CommandLineRouter commandLineRouter) {
        this.commandLineRouter = commandLineRouter;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String command;
        while (true) {
            command = scanner.nextLine();
            if (command.isEmpty() || command.isBlank() || command.equals("exit"))
                break;
            System.out.println(commandLineRouter.route(command));
        }
    }

    @Override
    public void stop() {
        ApplicationStartup.stop();
    }
}
