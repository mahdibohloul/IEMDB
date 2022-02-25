package domain.movie.services;

import domain.movie.exceptions.InvalidRateScoreException;
import domain.movie.models.Movie;
import domain.movie.models.MovieScore;
import domain.movie.repositories.MovieScoreRepository;
import domain.user.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.awt.*;

public class MovieScoreServiceTest {
    @InjectMocks
    private MovieScoreServiceImpl movieScoreService;

    private PodamFactory podamFactory = new PodamFactoryImpl();

    @Mock
    private MovieScoreRepository movieScoreRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private static final Integer SCORE_UPPER_LIMIT = 10;
    private static final Integer SCORE_LOWER_LIMIT = 1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should score to movie with success")
    void should_score_to_move_with_success() throws InvalidRateScoreException {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        User user = podamFactory.manufacturePojo(User.class);
        Integer score = 5;
        // TODO: right?
        movieScoreService.scoreMovie(movie, user, score);
        MovieScore movieScore = movieScoreService.getMovieScore(movie.getId(), user.getEmail());

        assert movieScore.movieId.equals(score);
    }

    @Test
    @DisplayName("should_get_movie_score_with_success")
    void should_get_movie_score_with_success() {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        User user = podamFactory.manufacturePojo(User.class);
        Assertions.assertDoesNotThrow(() -> movieScoreService.getMovieScore(movie.getId(), user.getEmail()));
        //TODO: right?
        assert movieScoreService.getMovieScore(movie.getId(), user.getEmail()).equals(0);
    }

    @Test
    @DisplayName("should_save_movie_score_with_success")
    void should_save_movie_score_with_success() throws InvalidRateScoreException {
        User user = podamFactory.manufacturePojo(User.class);

        Movie firstMovie = podamFactory.manufacturePojo(Movie.class);

        movieScoreService.scoreMovie(firstMovie, user, 5);

        Assertions.assertDoesNotThrow(() -> movieScoreService.findAllByMovieId(firstMovie.getId()));
        //TODO: right?
        assert movieScoreService.findAllByMovieId(firstMovie.getId()).findFirst().get().score.equals(5);
    }
}
