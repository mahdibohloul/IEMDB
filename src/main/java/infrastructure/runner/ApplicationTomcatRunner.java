package infrastructure.runner;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import infrastructure.startup.ApplicationStartup;

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
