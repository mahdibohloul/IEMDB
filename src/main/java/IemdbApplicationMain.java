import infrastructure.startup.ApplicationStartup;

public class IemdbApplicationMain {

    public static void main(String[] args) {
        ApplicationStartup applicationStartup = new ApplicationStartup();
        applicationStartup.start();
    }
}