package domain.movie.repositories;

import domain.movie.models.Movie;
import domain.movie.valueobjects.MovieSearchModel;
import infrastructure.store.InMemoryRepository;

import java.util.stream.Stream;

public interface MovieRepository extends InMemoryRepository<Movie, Integer> {
    Stream<Movie> searchMovies(MovieSearchModel searchModel);
}
