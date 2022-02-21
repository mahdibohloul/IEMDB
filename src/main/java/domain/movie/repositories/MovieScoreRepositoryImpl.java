package domain.movie.repositories;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import domain.movie.models.MovieScore;
import infrastructure.store.InMemoryStore;

@Repository
public class MovieScoreRepositoryImpl implements MovieScoreRepository {
    private final InMemoryStore<MovieScore, Integer> store = new InMemoryStore<>();

    @Override
    public Stream<MovieScore> findAllByUserEmail(String email) {
        return store.getAll().stream().filter(movieScore -> movieScore.getUserEmail().equals(email));
    }

    @Override
    public Stream<MovieScore> findAllByMovieId(Integer movieId) {
        return store.getAll().stream().filter(movieScore -> movieScore.getMovieId().equals(movieId));
    }

    @Override
    public MovieScore findByUserEmailAndMovieId(String email, Integer movieId) {
        return store.getAll().stream()
                .filter(movieScore -> movieScore.getUserEmail().equals(email) && movieScore.getMovieId().equals(movieId))
                .findFirst().orElse(null);
    }

    @Override
    public MovieScore insert(MovieScore entity) {
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public MovieScore update(MovieScore entity) {
        return insert(entity);
    }

    @Override
    public MovieScore findById(Integer integer) {
        return store.get(integer);
    }

    @Override
    public void delete(MovieScore entity) {
        store.remove(entity.getId());
    }

    @Override
    public Integer generateId() {
        return store.getLastId(Integer::compare) + 1;
    }
}
