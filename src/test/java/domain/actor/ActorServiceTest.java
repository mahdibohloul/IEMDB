package domain.actor;

import domain.actor.exceptions.ActorNotFoundException;
import domain.actor.models.Actor;
import domain.actor.repositories.ActorRepository;
import domain.actor.services.ActorServiceImpl;
import domain.comment.exceptions.InvalidVoteValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

public class ActorServiceTest {
    @InjectMocks
    private ActorServiceImpl actorService;

    @Mock
    private final ActorRepository actorRepository;

    public ActorServiceTest(ActorServiceImpl actorService, ActorRepository actorRepository) {
        this.actorService = actorService;
        this.actorRepository = actorRepository;
    }

    private final PodamFactory podamFactory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should insert actor with success")
    void should_insert_actor_with_success() throws InvalidVoteValueException {
        Actor actor = podamFactory.manufacturePojo(Actor.class);

        Mockito.when(actorRepository.save(actor)).thenReturn(actor);
        assert actorService.insertActor(actor).equals(actor);
    }

    @Test
    @DisplayName("should search actor with success")
    void should_search_actor_with_success() throws InvalidVoteValueException {
        Actor actor = podamFactory.manufacturePojo(Actor.class);
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(actor.getId());
        List<Integer> actors = new ArrayList<Integer>();
        Mockito.when(actorRepository.findById(actor.getId())).thenReturn(actor);
//        TODO: IDK HAVE  CAN I TEST LISTS
        assert actorService.searchActors(ids).equals(actors);
    }

    @Test
    @DisplayName("should find actor by id with success")
    void should_find_actor_by_id_with_success() throws InvalidVoteValueException, ActorNotFoundException {
        Actor actor = podamFactory.manufacturePojo(Actor.class);

        Mockito.when(actorRepository.findById(actor.getId())).thenReturn(actor);
        assert actorService.findActorById(actor.getId()).equals(actor);
    }

    @Test
    @DisplayName("should check exist actor by id with success")
    void should_exist_actor_by_id_with_success() throws InvalidVoteValueException, ActorNotFoundException {
        Actor actor = podamFactory.manufacturePojo(Actor.class);

        Mockito.when(actorRepository.findById(actor.getId())).thenReturn(actor);
        Mockito.when(actorRepository.save(actor)).thenReturn(actor);
        actor = actorService.insertActor(actor);
        assert actorService.existsActorById(actor.getId()).equals(true);
    }

}
