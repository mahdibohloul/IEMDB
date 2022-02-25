package application.controllers;

import java.text.ParseException;

import org.springframework.stereotype.Controller;

import application.models.request.AddActorRequestModel;
import application.models.response.GenericResponseModel;
import domain.actor.models.Actor;
import domain.actor.services.ActorService;
import framework.router.commandline.exceptions.InvalidCommandException;

@Controller
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    public GenericResponseModel addActor(AddActorRequestModel addActorRequestModel) {
        GenericResponseModel response = new GenericResponseModel();
        try {
            Actor actor = addActorRequestModel.toActor();
            actorService.insertActor(actor);
            response.setSuccessfulResponse("actor added successfully");
        } catch (ParseException e) {
            response.setUnsuccessfulResponse(new InvalidCommandException("Invalid date format").toString());
        }
        return response;
    }
}
