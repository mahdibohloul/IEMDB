package domain.user.repositories;

import domain.user.models.UserWatchList;
import infrastructure.store.InMemoryRepository;

import java.util.stream.Stream;

public interface UserWatchListRepository extends InMemoryRepository<UserWatchList, Integer> {
    UserWatchList findByUserEmailAndMovieId(String email, Integer movieId);

    Stream<UserWatchList> findAllByUserEmail(String email);
}
