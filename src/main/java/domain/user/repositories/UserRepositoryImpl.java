package domain.user.repositories;

import domain.user.models.User;
import infrastructure.store.InMemoryStore;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final InMemoryStore<User, Integer> store = new InMemoryStore<>();

    @Override
    public User insert(User entity) {
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public User update(User entity) {
        return insert(entity);
    }

    @Override
    public User findById(Integer integer) {
        return store.get(integer);
    }

    @Override
    public void delete(User entity) {
        store.remove(entity.getId());
    }

    @Override
    public Integer generateId() {
        return store.getLastId(Integer::compare) + 1;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> optionalUser = store.getAll().stream().filter(x -> x.getEmail().equalsIgnoreCase(email)).findFirst();
        return optionalUser.orElse(null);
    }
}
