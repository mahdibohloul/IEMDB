package infrastructure.startup;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import infrastructure.AppConfig;
import infrastructure.beans.ComponentsConfiguration;
import infrastructure.dataprovider.services.DataProvider;
import infrastructure.runner.ApplicationRunner;

public class ApplicationStartup {
    private static AnnotationConfigApplicationContext context;

    public static void start() {
        registerComponents();
        setEnvironment();
        getContext().refresh();
    }

    private ApplicationStartup() {
    }

    public static void run(ApplicationRunner applicationRunner) {
        AppConfig appConfig = getContext().getBean(AppConfig.class);
        if (appConfig.getPopulateDatabase()) {
            populateDatabase();
        }
        applicationRunner.run();
        applicationRunner.stop();
    }

    public static void run(String applicationRunnerName) {
        ApplicationRunner applicationRunner = (ApplicationRunner) getContext().getBean(applicationRunnerName);
        run(applicationRunner);
    }

    public static void stop() {
        getContext().close();
    }

    public static AnnotationConfigApplicationContext getContext() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext();
        }
        return context;
    }

    private static void registerComponents() {
        getContext().register(ComponentsConfiguration.class);
    }

    private static void populateDatabase() {
        long startTime = System.currentTimeMillis();
        Logger logger = LoggerFactory.getLogger(ApplicationStartup.class.getSimpleName());
        logger.info("Database population started");
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
        logger.info("Database populated in " + (endTime - startTime) + " ms");
    }

    private static void setEnvironment() {
        getContext().getEnvironment().addActiveProfile("tomcat");
    }
}
