import infrastructure.runner.ApplicationJavalinRunner;
import infrastructure.startup.ApplicationStartup;

public class IemdbApplicationMain {

    public static void main(String[] args) {
        ApplicationStartup.start();
        ApplicationStartup.run(ApplicationJavalinRunner.class.getSimpleName());
    }
}
