package domain.movie.repositories;

import java.util.List;
import java.util.stream.Stream;

import domain.movie.models.Movie;
import infrastructure.store.InMemoryRepository;

public interface MovieRepository extends InMemoryRepository<Movie, Integer> {
    Stream<Movie> searchMovies(List<Integer> ids,
            List<String> names,
            List<String> directors,
            List<String> writers,
            List<String> genres,
            List<Integer> cast,
            Double imdbRateGt,
            Double imdbRateLt,
            Integer ageLimitGt,
            Integer ageLimitLt,
            Integer yearGt,
            Integer yearLt);
}
