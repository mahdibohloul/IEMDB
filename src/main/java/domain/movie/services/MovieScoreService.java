package domain.movie.services;

import domain.movie.exceptions.InvalidRateScoreException;
import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.MovieScore;
import domain.user.exceptions.UserNotFoundException;

import java.util.stream.Stream;

public interface MovieScoreService {
    void scoreMovie(Integer movieId, String userEmail, Integer score) throws InvalidRateScoreException, MovieNotFoundException, UserNotFoundException;

    MovieScore getMovieScore(Integer movieId, String userEmail);

    Stream<MovieScore> findAllByMovieId(Integer movieId);
}
