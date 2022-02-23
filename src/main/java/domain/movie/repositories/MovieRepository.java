package domain.movie.repositories;

import domain.movie.models.Movie;
import infrastructure.store.InMemoryRepository;

import java.util.List;
import java.util.stream.Stream;

public interface MovieRepository extends InMemoryRepository<Movie, Integer> {
    Stream<Movie> searchMovies(List<Integer> ids, List<String> names, List<String> directors, List<String> writers,
            List<String> genres,
            Double imdbRateGt, Double imdbRateLt, Integer ageLimitGt, Integer ageLimitLt);
}
