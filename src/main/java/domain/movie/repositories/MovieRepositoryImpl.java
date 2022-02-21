package domain.movie.repositories;

import org.springframework.stereotype.Repository;

import domain.movie.models.Movie;
import infrastructure.store.InMemoryStore;

import java.util.List;
import java.util.stream.Stream;

@Repository
public class MovieRepositoryImpl implements MovieRepository {
    private final InMemoryStore<Movie, Integer> store = new InMemoryStore<>();

    @Override
    public Movie insert(Movie entity) {
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Movie update(Movie entity) {
        return insert(entity);
    }

    @Override
    public Movie findById(Integer integer) {
        return store.get(integer);
    }

    @Override
    public void delete(Movie entity) {
        store.remove(entity.getId());
    }

    @Override
    public Integer generateId() {
        return store.getLastId(Integer::compare) + 1;
    }

    @Override
    public Stream<Movie> searchMovies(List<Integer> ids, List<String> names, List<String> directors,
            List<String> writers, Double imdbRateGt, Double imdbRateLt, Integer ageLimitGt, Integer ageLimitLt) {
        Stream<Movie> movies = store.getAll().stream();
        if (ids != null && !ids.isEmpty()) {
            movies = movies.filter(movie -> ids.contains(movie.getId()));
        }
        if (names != null && !names.isEmpty()) {
            movies = movies.filter(movie -> names.stream().anyMatch(name -> movie.getName().equalsIgnoreCase(name)));
        }
        if (directors != null && !directors.isEmpty()) {
            movies = movies.filter(
                    movie -> directors.stream().anyMatch(director -> movie.getDirector().equalsIgnoreCase(director)));
        }
        if (writers != null && !writers.isEmpty()) {
            movies = movies.filter(
                    movie -> writers.stream()
                            .anyMatch(writer -> movie.getWriters().stream().anyMatch(mw -> writers.stream().anyMatch(
                                    mw::equalsIgnoreCase)))
            );
        }
        if (imdbRateGt != null) {
            movies = movies.filter(movie -> movie.getImdbRate() > imdbRateGt);
        }
        if (imdbRateLt != null) {
            movies = movies.filter(movie -> movie.getImdbRate() < imdbRateLt);
        }
        if (ageLimitGt != null) {
            movies = movies.filter(movie -> movie.getAgeLimit() > ageLimitGt);
        }
        if (ageLimitLt != null) {
            movies = movies.filter(movie -> movie.getAgeLimit() < ageLimitLt);
        }

        return movies;
    }
}
