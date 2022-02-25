package domain.movie.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import domain.movie.models.Movie;
import domain.movie.models.MovieScore;
import domain.movie.repositories.MovieScoreRepository;
import domain.user.models.User;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class MovieScoreServiceTest {
    @InjectMocks
    private MovieScoreServiceImpl movieScoreService;

    private final PodamFactory podamFactory = new PodamFactoryImpl();

    @Mock
    private MovieScoreRepository movieScoreRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should score to movie with success")
    void should_score_to_move_with_success() {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        User user = podamFactory.manufacturePojo(User.class);
        Integer score = 1;
        MovieScore movieScore = podamFactory.manufacturePojo(MovieScore.class);
        movieScore.setScore(score);
        Mockito.when(movieScoreRepository.findByUserEmailAndMovieId(user.getEmail(), movie.getId()))
                .thenReturn(movieScore);
        Mockito.when(movieScoreRepository.save(movieScore)).thenReturn(movieScore);

        Assertions.assertDoesNotThrow(() -> movieScoreService.scoreMovie(movie, user, score));
    }

    @Test
    @DisplayName("should_get_movie_score_with_success")
    void should_get_movie_score_with_success() {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        User user = podamFactory.manufacturePojo(User.class);
        MovieScore movieScore = podamFactory.manufacturePojo(MovieScore.class);
        Mockito.when(movieScoreRepository.findByUserEmailAndMovieId(user.getEmail(), movie.getId()))
                .thenReturn(movieScore);
        assert movieScoreService.getMovieScore(movie.getId(), user.getEmail()).equals(movieScore);
    }
}
