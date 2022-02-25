package domain.actor.services;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import domain.actor.exceptions.ActorNotFoundException;
import domain.actor.models.Actor;
import domain.actor.repositories.ActorRepository;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public Actor insertActor(Actor actor) {
        return actorRepository.save(actor);
    }

    @Override
    public Stream<Actor> searchActors(List<Integer> ids) {
        return actorRepository.searchActors(ids);
    }

    @Override
    public Actor findActorById(Integer id) throws ActorNotFoundException {
        Actor actor = actorRepository.findById(id);
        if (actor == null) {
            throw new ActorNotFoundException(id);
        }
        return actor;
    }

    @Override
    public Boolean existsActorById(Integer id) {
        return actorRepository.existsById(id);
    }
}
