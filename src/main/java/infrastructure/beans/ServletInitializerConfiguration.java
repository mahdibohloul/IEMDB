package infrastructure.beans;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import infrastructure.runner.ApplicationTomcatRunner;
import infrastructure.startup.ApplicationStartup;

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
