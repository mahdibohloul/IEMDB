import application.controllers.ActorController;
import application.models.request.ActorRequestModel;
import infrastructure.startup.ApplicationStartup;

public class IemdbApplicationMain {

    public static void main(String[] args) {
        ApplicationStartup applicationStartup = new ApplicationStartup();
        applicationStartup.start();
        ActorController actorController = applicationStartup.getContext().getBean(ActorController.class);
        ActorRequestModel model = new ActorRequestModel("tom hardy", "1977-09-15", "British");
        model.setId(1);
        try {
            actorController.addActor(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
