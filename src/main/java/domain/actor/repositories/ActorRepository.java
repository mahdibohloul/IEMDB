package domain.actor.repositories;

import java.util.List;
import java.util.stream.Stream;

import domain.actor.models.Actor;
import infrastructure.store.InMemoryRepository;

public interface ActorRepository extends InMemoryRepository<Actor, Integer> {
    Stream<Actor> searchActors(List<Integer> ids);

    Boolean existsById(Integer id);
}
