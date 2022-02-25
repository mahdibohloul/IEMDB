package domain.movie.services;

import application.models.response.MovieResponseModel;
import domain.actor.exceptions.ActorNotFoundException;
import domain.comment.services.CommentService;
import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.movie.repositories.MovieRepository;
import domain.user.models.User;
import infrastructure.time.services.TimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.yaml.snakeyaml.util.EnumUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MovieServiceTest {
    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

//    private final CommentService commentService;
//    private final TimeService timeService;
    private final PodamFactory podamFactory = new PodamFactoryImpl();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should insert movie with success")
    void should_insert_movie_with_success() throws ActorNotFoundException {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        Mockito.when(movieRepository.save(movie)).thenReturn(movie);
        assert movieService.insertMovie(movie).equals(movie);
    }

    @Test
    @DisplayName("should find movie by id with success")
    void should_find_movie_by_id_with_success() throws MovieNotFoundException {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        Mockito.when(movieRepository.findById(movie.getId())).thenReturn(movie);
        Assertions.assertDoesNotThrow(() -> movieService.findMovieById(movie.getId()));
        assert movieService.findMovieById(movie.getId()).equals(movie);
    }


    @Test
    @DisplayName("should add comment with success")
    void should_add_comment_with_success() throws MovieNotFoundException {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        User user = podamFactory.manufacturePojo(User.class);
        String text = "GOOD!";
        Assertions.assertDoesNotThrow(() -> movieService.addComment(movie, text, user));
        // TODO: i dk what to do!
//        assert movieService.addComment(movie, text, user).equals(movie);
    }

    @Test
    @DisplayName("should get movies with success")
    void should_get_movies_with_success(){
        List ids = new ArrayList();

        Movie firstMovie = podamFactory.manufacturePojo(Movie.class);
        ids.add(firstMovie.getId());

        Movie secondMove = podamFactory.manufacturePojo(Movie.class);
        ids.add(secondMove.getId());

        Assertions.assertDoesNotThrow(() -> movieService.searchMovies(ids, null, null, null,
                null, null, null, null, null));

        MovieResponseModel movies = (MovieResponseModel) movieService.searchMovies(ids, null, null,
                null, null, null, null, null, null);


//        TODO: IDK what to do?
    }
}

