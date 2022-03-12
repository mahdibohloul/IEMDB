package infrastructure.runner;

import infrastructure.startup.ApplicationStartup;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component("ApplicationTomcatRunner")
@Profile("tomcat")
public class ApplicationTomcatRunner implements ApplicationRunner {
    @Override
    public void run() {

    }

    @Override
    public void stop() {
        Runtime.getRuntime().addShutdownHook(new Thread(ApplicationStartup::stop));
    }
}
