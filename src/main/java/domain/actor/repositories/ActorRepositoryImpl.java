package domain.actor.repositories;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import domain.actor.models.Actor;
import infrastructure.store.InMemoryStore;

@Repository
public class ActorRepositoryImpl implements ActorRepository {
    private final InMemoryStore<Actor, Integer> store = new InMemoryStore<>();


    @Override
    public Actor insert(Actor entity) {
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Actor update(Actor entity) {
        return insert(entity);
    }

    @Override
    public Actor findById(Integer integer) {
        return store.get(integer);
    }

    @Override
    public void delete(Actor entity) {
        store.remove(entity.getId());
    }

    @Override
    public Integer generateId() {
        return store.getLastId(Integer::compare) + 1;
    }

    @Override
    public Stream<Actor> searchActors(List<Integer> ids) {
        Stream<Actor> actorStream = store.getAll().stream();
        if (ids != null) {
            actorStream = actorStream.filter(actor -> ids.contains(actor.getId()));
        }
        return actorStream;
    }

    @Override
    public Boolean existsById(Integer id) {
        return store.get(id) != null;
    }
}
