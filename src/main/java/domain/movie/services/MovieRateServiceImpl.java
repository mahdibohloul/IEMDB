package domain.movie.services;

import domain.movie.models.Movie;
import domain.movie.models.MovieRate;
import domain.movie.models.MovieScore;
import domain.movie.repositories.MovieRateRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class MovieRateServiceImpl implements MovieRateService {
    private final MovieRateRepository movieRateRepository;
    private final MovieScoreService movieScoreService;

    public MovieRateServiceImpl(MovieRateRepository movieRateRepository, MovieScoreService movieScoreService) {
        this.movieRateRepository = movieRateRepository;
        this.movieScoreService = movieScoreService;
    }

    @Override
    public void updateMovieRate(Integer movieId) {
        MovieRate movieRate = getMovieRate(movieId);
        if (movieRate == null) {
            movieRate = new MovieRate();
            movieRate.setMovieId(movieId);
        }
        Stream<Double> scores = movieScoreService.findAllByMovieId(movieId)
                .mapToInt(MovieScore::getScore)
                .boxed()
                .map(Integer::doubleValue);
        movieRate.setAvgRate(calculateMovieRate(scores));
        movieRateRepository.save(movieRate);
    }

    @Override
    public Double getMovieRate(Movie movie) {
        return movieRateRepository.findByMovieId(movie.getId()).getAvgRate();
    }

    private Double calculateMovieRate(Stream<Double> scores) {
        return scores.mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private MovieRate getMovieRate(Integer movieId) {
        return movieRateRepository.findByMovieId(movieId);
    }
}
