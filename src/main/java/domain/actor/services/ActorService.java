package domain.actor.services;

import domain.actor.models.Actor;

import java.util.List;
import java.util.stream.Stream;

public interface ActorService {
    Actor insertActor(Actor actor);

    Stream<Actor> searchActors(List<Integer> ids);
}
