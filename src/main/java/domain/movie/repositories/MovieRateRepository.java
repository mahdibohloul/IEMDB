package domain.movie.repositories;

import domain.movie.models.MovieRate;
import infrastructure.store.InMemoryRepository;

public interface MovieRateRepository extends InMemoryRepository<MovieRate, Integer> {
    MovieRate findByMovieId(Integer movieId);
}
