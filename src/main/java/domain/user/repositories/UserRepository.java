package domain.user.repositories;

import domain.user.models.User;
import infrastructure.store.InMemoryRepository;

public interface UserRepository extends InMemoryRepository<User, Integer> {
    User findByEmail(String email);
}
