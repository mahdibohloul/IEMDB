package domain.movie.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import domain.movie.models.MovieRate;
import infrastructure.store.InMemoryStore;

@Repository
public class MovieRateRepositoryImpl implements MovieRateRepository {
    private final InMemoryStore<MovieRate, Integer> store = new InMemoryStore<>();

    @Override
    public Optional<MovieRate> findByMovieId(Integer movieId) {
        return store.getAll().stream().filter(movieRate -> movieRate.getMovieId().equals(movieId)).findFirst();
    }

    @Override
    public MovieRate insert(MovieRate entity) {
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public MovieRate update(MovieRate entity) {
        return insert(entity);
    }

    @Override
    public MovieRate findById(Integer integer) {
        return store.get(integer);
    }

    @Override
    public void delete(MovieRate entity) {
        store.remove(entity.getId());
    }

    @Override
    public Integer generateId() {
        return store.getLastId(Integer::compare) + 1;
    }
}
