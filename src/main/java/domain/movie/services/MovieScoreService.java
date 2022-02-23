package domain.movie.services;

import domain.movie.exceptions.InvalidRateScoreException;
import domain.movie.models.Movie;
import domain.movie.models.MovieScore;
import domain.user.models.User;

import java.util.stream.Stream;

public interface MovieScoreService {
    void scoreMovie(Movie movie, User user, Integer score) throws InvalidRateScoreException;

    MovieScore getMovieScore(Integer movieId, String userEmail);

    Stream<MovieScore> findAllByMovieId(Integer movieId);
}
