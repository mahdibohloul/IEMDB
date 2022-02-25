package domain.actor.services;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import domain.actor.exceptions.ActorNotFoundException;
import domain.actor.models.Actor;
import domain.actor.repositories.ActorRepository;
import infrastructure.BaseEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class ActorServiceTest {
    @InjectMocks
    private ActorServiceImpl actorService;

    @Mock
    private ActorRepository actorRepository;

    private final PodamFactory podamFactory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should insert actor with success")
    void should_insert_actor_with_success() {
        Actor actor = podamFactory.manufacturePojo(Actor.class);

        Mockito.when(actorRepository.save(actor)).thenReturn(actor);
        assert actorService.insertActor(actor).equals(actor);
    }

    @Test
    @DisplayName("should search actor with success")
    void should_search_actor_with_success() {
        List<Actor> actors = podamFactory.manufacturePojo(List.class, Actor.class);
        List<Integer> ids = actors.stream().map(BaseEntity::getId).toList();
        Mockito.when(actorRepository.searchActors(ids))
                .thenReturn(actors.stream());
        Assertions.assertArrayEquals(actorService.searchActors(ids).toArray(), actors.toArray());
    }

    @Test
    @DisplayName("should find actor by id with success")
    void should_find_actor_by_id_with_success() throws ActorNotFoundException {
        Actor actor = podamFactory.manufacturePojo(Actor.class);

        Mockito.when(actorRepository.findById(actor.getId())).thenReturn(actor);
        assert actorService.findActorById(actor.getId()).equals(actor);
    }

    @Test
    @DisplayName("should check exist actor by id with success")
    void actor_should_exist_with_success() {
        Actor actor = podamFactory.manufacturePojo(Actor.class);
        Mockito.when(actorRepository.existsById(actor.getId())).thenReturn(true);
        assert actorService.existsActorById(actor.getId()).equals(true);
    }

}
