package domain.movie.services;

import java.util.stream.Stream;

import domain.actor.exceptions.ActorNotFoundException;
import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.movie.valueobjects.MovieSearchModel;
import domain.user.models.User;

public interface MovieService {
    Movie insertMovie(Movie movie) throws ActorNotFoundException;

    Movie findMovieById(Integer id) throws MovieNotFoundException;

    Integer addComment(Movie movie, String text, User user) throws MovieNotFoundException;

    Stream<Movie> searchMovies(MovieSearchModel searchModel);
}
