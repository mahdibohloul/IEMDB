package domain.movie.services;

import domain.movie.models.Movie;
import domain.movie.models.MovieRate;
import domain.movie.models.MovieScore;
import domain.movie.repositories.MovieRateRepository;
import domain.user.services.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class MovieRateService {

    @InjectMocks
    private MovieRateServiceImpl movieRateService;

    @Mock
    private final MovieRateRepository movieRateRepository;

    @Mock
    private final MovieScoreService movieScoreService;

    @Mock
    private final Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    private final PodamFactory podamFactory = new PodamFactoryImpl();


    public MovieRateService(MovieRateRepository movieRateRepository, MovieScoreService movieScoreService, Logger logger) {
        this.movieRateRepository = movieRateRepository;
        this.movieScoreService = movieScoreService;
        this.logger = logger;
    }

    @Test
    @DisplayName("should update movie rate with successfully")
    void should_update_movie_rate_with_success() {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        MovieRate movieRate = podamFactory.manufacturePojo(MovieRate.class);
        Mockito.when(movieRateRepository.findByMovieId(movie.getId())).thenReturn(movieRate);
        movieRateService.updateMovieRate(movie.getId());
    }

    void should_get_movie_rate_with_success() {
        Movie movie = podamFactory.manufacturePojo(Movie.class);
        MovieRate movieRate = podamFactory.manufacturePojo(MovieRate.class);
        Mockito.when(movieRateRepository.findByMovieId(movie.getId())).thenReturn(movieRate);
        movieRateService.getMovieRate(movie).equals(0);
    }
}
