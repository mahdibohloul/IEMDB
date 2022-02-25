package infrastructure.runner;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import framework.router.commandline.CommandLineRouter;

@Service(value = "ApplicationCommandLineRunner")
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
}