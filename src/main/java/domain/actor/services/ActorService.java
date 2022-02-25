package domain.actor.services;

import java.util.List;
import java.util.stream.Stream;

import domain.actor.exceptions.ActorNotFoundException;
import domain.actor.models.Actor;

public interface ActorService {
    Actor insertActor(Actor actor);

    Stream<Actor> searchActors(List<Integer> ids);

    Actor findActorById(Integer id) throws ActorNotFoundException;

    Boolean existsActorById(Integer id);
}
