package domain.movie.repositories;

import java.util.Optional;

import domain.movie.models.MovieRate;
import infrastructure.store.InMemoryRepository;

public interface MovieRateRepository extends InMemoryRepository<MovieRate, Integer> {
    Optional<MovieRate> findByMovieId(Integer movieId);
}
