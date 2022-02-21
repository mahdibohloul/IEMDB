package domain.movie.services;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import domain.movie.models.MovieRate;
import domain.movie.models.MovieScore;
import domain.movie.repositories.MovieRateRepository;

@Service
public class MovieRateServiceImpl implements MovieRateService {
    private final MovieRateRepository movieRateRepository;
    private final MovieScoreService movieScoreService;
    private final Logger logger;

    public MovieRateServiceImpl(MovieRateRepository movieRateRepository, MovieScoreService movieScoreService, Logger logger) {
        this.movieRateRepository = movieRateRepository;
        this.movieScoreService = movieScoreService;
        this.logger = logger;
    }

    @Override
    public void updateMovieRate(Integer movieId) {
        MovieRate movieRate = getMovieRate(movieId);
        logger.info("Updating movie rate for movie: {}", movieRate);
        if (movieRate == null) {
            movieRate = new MovieRate();
            movieRate.setMovieId(movieId);
        }
        Stream<Double> scores = movieScoreService.findAllByMovieId(movieId)
                .mapToInt(MovieScore::getScore)
                .boxed()
                .map(Integer::doubleValue);
        logger.info("Scores: {}", scores);
        movieRate.setAvgRate(calculateMovieRate(scores));
        movieRateRepository.save(movieRate);
    }

    private Double calculateMovieRate(Stream<Double> scores) {
        return scores.reduce(0.0, Double::sum) / scores.count();
    }

    private MovieRate getMovieRate(Integer movieId) {
        return movieRateRepository.findByMovieId(movieId);
    }
}
