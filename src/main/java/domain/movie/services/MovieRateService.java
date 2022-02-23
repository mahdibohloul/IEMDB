package domain.movie.services;

import domain.movie.models.Movie;

public interface MovieRateService {
    void updateMovieRate(Integer movieId);
    Double getMovieRate(Movie movie);
}
