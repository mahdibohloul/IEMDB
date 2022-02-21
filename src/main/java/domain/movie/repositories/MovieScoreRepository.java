package domain.movie.repositories;

import java.util.stream.Stream;

import domain.movie.models.MovieScore;
import infrastructure.store.InMemoryRepository;

public interface MovieScoreRepository extends InMemoryRepository<MovieScore, Integer> {
    Stream<MovieScore> findAllByUserEmail(String email);

    Stream<MovieScore> findAllByMovieId(Integer movieId);

    MovieScore findByUserEmailAndMovieId(String email, Integer movieId);

}
