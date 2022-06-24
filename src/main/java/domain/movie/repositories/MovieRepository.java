package domain.movie.repositories;

import java.util.stream.Stream;

import domain.movie.models.Movie;
import domain.movie.valueobjects.MovieSearchModel;
import infrastructure.store.InMemoryRepository;

public interface MovieRepository extends InMemoryRepository<Movie, Integer> {
    Stream<Movie> searchMovies(MovieSearchModel searchModel);
}
