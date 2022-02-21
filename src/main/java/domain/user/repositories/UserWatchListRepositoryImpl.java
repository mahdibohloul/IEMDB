package domain.user.repositories;

import domain.user.models.UserWatchList;
import infrastructure.store.InMemoryStore;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public class UserWatchListRepositoryImpl implements UserWatchListRepository {
    private final InMemoryStore<UserWatchList, Integer> store = new InMemoryStore<>();

    @Override
    public UserWatchList insert(UserWatchList entity) {
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public UserWatchList update(UserWatchList entity) {
        return insert(entity);
    }

    @Override
    public UserWatchList findById(Integer integer) {
        return store.get(integer);
    }

    @Override
    public void delete(UserWatchList entity) {
        store.remove(entity.getId());
    }

    @Override
    public Integer generateId() {
        return store.getLastId(Integer::compareTo) + 1;
    }

    @Override
    public UserWatchList findByUserEmailAndMovieId(String email, Integer movieId) {
        return store.getAll().stream()
                .filter(userWatchList -> userWatchList.getUserEmail().equals(email) &&
                                         userWatchList.getMovieId().equals(movieId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Stream<UserWatchList> findAllByUserEmail(String email) {
        return store.getAll().stream()
                .filter(userWatchList -> userWatchList.getUserEmail().equals(email));
    }
}
