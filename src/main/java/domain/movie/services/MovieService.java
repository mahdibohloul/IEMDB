package domain.movie.services;

import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.user.models.User;

import java.util.List;
import java.util.stream.Stream;

public interface MovieService {
    Movie insertMovie(Movie movie);

    Movie findMovieById(Integer id) throws MovieNotFoundException;

    void addComment(Movie movie, String text, User user) throws MovieNotFoundException;

    Stream<Movie> searchMovies(List<Integer> ids, List<String> names, List<String> directors, List<String> writers,
            List<String> genres,
            Double imdbRateGt, Double imdbRateLt, Integer ageLimitGt, Integer ageLimitLt);
}
