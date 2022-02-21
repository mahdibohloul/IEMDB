package infrastructure.startup;

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

    public void stop() {
        context.close();
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }

    private void registerComponents() {
        context.register(ComponentsConfiguration.class);
    }

    private void refreshApplication() {
        while (true) {

        }
    }
}
