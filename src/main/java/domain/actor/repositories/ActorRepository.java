package domain.actor.repositories;

import domain.actor.models.Actor;
import infrastructure.store.InMemoryRepository;

import java.util.List;
import java.util.stream.Stream;

public interface ActorRepository extends InMemoryRepository<Actor, Integer> {
    Stream<Actor> searchActors(List<Integer> ids);
}
