package domain.movie.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import domain.movie.models.Movie;
import domain.movie.models.MovieRate;
import domain.movie.models.MovieScore;
import domain.movie.repositories.MovieRateRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class MovieRateServiceTest {

    @InjectMocks
    private MovieRateServiceImpl movieRateService;

    @Mock
    private MovieRateRepository movieRateRepository;

    @Mock
    private MovieScoreService movieScoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    private final PodamFactory podamFactory = new PodamFactoryImpl();


    @Test
    @DisplayName("should update movie rate with successfully")
    void should_update_movie_rate_with_success() {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        MovieRate movieRate = podamFactory.manufacturePojo(MovieRate.class);
        List<MovieScore> movieScores = podamFactory.manufacturePojo(List.class, MovieScore.class);
        Double average = movieScores.stream().map(MovieScore::getScore).map(Integer::doubleValue)
                .mapToDouble(Double::doubleValue).average().orElse(0.0);
        movieRate.setAvgRate(average);

        Mockito.when(movieRateRepository.findByMovieId(movie.getId())).thenReturn(Optional.of(movieRate));
        Mockito.when(movieRateRepository.save(movieRate)).thenReturn(movieRate);
        movieRateService.updateMovieRate(movie.getId());
    }

    @Test
    @DisplayName("should get movie rate with success")
    void should_get_movie_rate_with_success() {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        MovieRate movieRate = podamFactory.manufacturePojo(MovieRate.class);
        Mockito.when(movieRateRepository.findByMovieId(movie.getId())).thenReturn(Optional.of(movieRate));
        assert movieRateService.getMovieRate(movie).equals(movieRate.getAvgRate());
    }
}
