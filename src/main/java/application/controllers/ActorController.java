package application.controllers;

import application.models.request.ActorRequestModel;
import domain.actor.models.Actor;
import domain.actor.services.ActorService;
import org.springframework.stereotype.Controller;

import java.text.ParseException;

@Controller
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    public void addActor(ActorRequestModel actorRequestModel) throws ParseException {
        Actor actor = actorRequestModel.toActor();
        actorService.insertActor(actor);
    }
}
