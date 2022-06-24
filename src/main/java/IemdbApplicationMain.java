import infrastructure.runner.ApplicationTomcatRunner;
import infrastructure.startup.ApplicationStartup;

public class IemdbApplicationMain {

    public static void main(String[] args) {
        ApplicationStartup.start();
        ApplicationStartup.run(ApplicationTomcatRunner.class.getSimpleName());
    }
}
