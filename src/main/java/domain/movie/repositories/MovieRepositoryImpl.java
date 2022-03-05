package domain.movie.repositories;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import domain.movie.models.Movie;
import infrastructure.store.InMemoryStore;

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
    public Stream<Movie> searchMovies(List<Integer> ids,
            List<String> names,
            List<String> directors,
            List<String> writers,
            List<String> genres,
            List<Integer> cast,
            Double imdbRateGt,
            Double imdbRateLt,
            Integer ageLimitGt,
            Integer ageLimitLt,
            Integer yearGt,
            Integer yearLt) {
        Stream<Movie> movies = store.getAll().stream();
        if (ids != null) {
            movies = movies.filter(movie -> ids.contains(movie.getId()));
        }
        if (names != null) {
            movies = movies.filter(movie -> names.stream().anyMatch(name -> movie.getName().equalsIgnoreCase(name)));
        }
        if (directors != null) {
            movies = movies.filter(
                    movie -> directors.stream().anyMatch(director -> movie.getDirector().equalsIgnoreCase(director)));
        }
        if (writers != null) {
            movies = movies.filter(
                    movie -> movie.getWriters().stream()
                            .anyMatch(mw -> writers.stream().anyMatch(mw::equalsIgnoreCase)));
        }
        if (genres != null) {
            movies = movies.filter(
                    movie -> movie.getGenres().stream()
                            .anyMatch(mg -> genres.stream().anyMatch(mg::equalsIgnoreCase))
            );
        }
        if (cast != null) {
            movies = movies.filter(
                    movie -> movie.getCast().stream().anyMatch(cast::contains)
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
        if (yearGt != null) {
            movies = movies.filter(movie -> {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(movie.getReleaseDate());
                return calendar.get(Calendar.YEAR) > yearGt;
            });
        }
        if (yearLt != null) {
            movies = movies.filter(movie -> {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(movie.getReleaseDate());
                return calendar.get(Calendar.YEAR) < yearLt;
            });
        }


        return movies;
    }
}
