package domain.movie.services;

import domain.actor.exceptions.ActorNotFoundException;
import domain.actor.services.ActorService;
import domain.comment.models.Comment;
import domain.comment.services.CommentService;
import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.movie.repositories.MovieRepository;
import domain.movie.valueobjects.MovieSearchModel;
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
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;


public class MovieServiceTest {
    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private CommentService commentService;

    @Mock
    private TimeService timeService;

    @Mock
    private ActorService actorService;

    private final PodamFactory podamFactory = new PodamFactoryImpl();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should insert movie with success")
    void should_insert_movie_with_success() throws ActorNotFoundException {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        if (movie.getCast() != null) {
            for (Integer id : movie.getCast())
                Mockito.when(actorService.existsActorById(id)).thenReturn(true);
        }
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
    void should_add_comment_with_success() {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        User user = podamFactory.manufacturePojo(User.class);
        String text = podamFactory.manufacturePojo(String.class);
        Long currentTimestamp = podamFactory.manufacturePojo(Long.class);
        Comment comment = new Comment(user.getEmail(), text, currentTimestamp);
        Comment savedComment = new Comment(user.getEmail(), text, currentTimestamp);
        savedComment.setId(podamFactory.manufacturePojo(Integer.class));
        Mockito.when(timeService.getCurrentTimestamp()).thenReturn(currentTimestamp);
        Mockito.when(commentService.insertComment(comment)).thenReturn(savedComment);
        assert movieService.addComment(movie, text, user).equals(savedComment.getId());
    }

    @Test
    @DisplayName("should get movies with success")
    void should_get_movies_with_success() {
        List<Movie> movies = podamFactory.manufacturePojo(List.class, Movie.class);
        List<Integer> ids = movies.stream().map(Movie::getId).toList();
        MovieSearchModel searchModel = new MovieSearchModel();
        searchModel.setIds(ids);
        Mockito.when(movieRepository.searchMovies(searchModel)).thenReturn(movies.stream());
        List<Movie> searchedMovies = movieService.searchMovies(searchModel).toList();
        Assertions.assertArrayEquals(searchedMovies.toArray(), movies.toArray());
    }
}
