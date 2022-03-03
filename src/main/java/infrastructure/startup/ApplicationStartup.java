package infrastructure.startup;

import infrastructure.AppConfig;
import infrastructure.beans.ComponentsConfiguration;
import infrastructure.dataprovider.services.DataProvider;
import infrastructure.runner.ApplicationRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.io.IOException;

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
        AppConfig appConfig = context.getBean(AppConfig.class);
        if (appConfig.getPopulateDatabase()) {
            populateDatabase();
        }
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

    private void populateDatabase() {
        long startTime = System.currentTimeMillis();
        context.getBeansOfType(DataProvider.class).values().stream()
                .sorted(AnnotationAwareOrderComparator.INSTANCE)
                .forEach(dataProvider -> {
                    try {
                        dataProvider.populateData(dataProvider.provide());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        long endTime = System.currentTimeMillis();
        System.out.println("Database populated in " + (endTime - startTime) + " ms");
    }

}
