package infrastructure.startup;

import infrastructure.runner.ApplicationRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import infrastructure.beans.ComponentsConfiguration;

//TODO make this class a singleton
public class ApplicationStartup {
    private final AnnotationConfigApplicationContext context;

    public ApplicationStartup() {
        context = new AnnotationConfigApplicationContext();
    }

    public void start() {
        registerComponents();
        context.refresh();
    }

    public void run(ApplicationRunner applicationRunner) {
        applicationRunner.run();
        stop();
    }

    public void run(String applicationRunnerName) {
        ApplicationRunner applicationRunner = (ApplicationRunner) context.getBean(applicationRunnerName);
        run(applicationRunner);
    }

    public void stop() {
        context.close();
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }

    private void registerComponents() {
        context.register(ComponentsConfiguration.class);
    }

}
