package domain.actor.services;

import org.springframework.stereotype.Service;

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
        return actorRepository.insert(actor);
    }
}
