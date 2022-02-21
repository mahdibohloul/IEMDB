package domain.actor.repositories;

import domain.actor.models.Actor;
import infrastructure.store.InMemoryRepository;

public interface ActorRepository extends InMemoryRepository<Actor, Integer> {
}
