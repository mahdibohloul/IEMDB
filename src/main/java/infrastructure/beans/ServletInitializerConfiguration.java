package infrastructure.beans;

import infrastructure.runner.ApplicationTomcatRunner;
import infrastructure.startup.ApplicationStartup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletInitializerConfiguration implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationStartup.start();
        ApplicationStartup.run(ApplicationTomcatRunner.class.getSimpleName());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
