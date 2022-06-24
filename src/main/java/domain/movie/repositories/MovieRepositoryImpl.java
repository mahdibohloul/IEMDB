package domain.movie.repositories;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import domain.movie.models.Movie;
import domain.movie.valueobjects.MovieSearchModel;
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
    public Stream<Movie> searchMovies(MovieSearchModel searchModel) {
        Stream<Movie> movies = store.getAll().stream();
        if (searchModel.getIds() != null) {
            movies = movies.filter(movie -> searchModel.getIds().contains(movie.getId()));
        }
        if (searchModel.getNames() != null) {
            movies = movies.filter(
                    movie -> searchModel.getNames().stream()
                            .anyMatch(name -> movie.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(
                                    Locale.ROOT))));
        }
        if (searchModel.getDirectors() != null) {
            movies = movies.filter(
                    movie -> searchModel.getDirectors().stream()
                            .anyMatch(director -> movie.getDirector().equalsIgnoreCase(director)));
        }
        if (searchModel.getWriters() != null) {
            movies = movies.filter(
                    movie -> movie.getWriters().stream()
                            .anyMatch(mw -> searchModel.getWriters().stream().anyMatch(mw::equalsIgnoreCase)));
        }
        if (searchModel.getGenres() != null) {
            movies = movies.filter(
                    movie -> movie.getGenres().stream()
                            .anyMatch(mg -> searchModel.getGenres().stream().anyMatch(mg::equalsIgnoreCase))
            );
        }
        if (searchModel.getCast() != null) {
            movies = movies.filter(
                    movie -> movie.getCast().stream().anyMatch(searchModel.getCast()::contains)
            );
        }
        if (searchModel.getImdbRateGt() != null) {
            movies = movies.filter(movie -> movie.getImdbRate() > searchModel.getImdbRateGt());
        }
        if (searchModel.getImdbRateLt() != null) {
            movies = movies.filter(movie -> movie.getImdbRate() < searchModel.getImdbRateLt());
        }
        if (searchModel.getAgeLimitGt() != null) {
            movies = movies.filter(movie -> movie.getAgeLimit() > searchModel.getAgeLimitGt());
        }
        if (searchModel.getAgeLimitLt() != null) {
            movies = movies.filter(movie -> movie.getAgeLimit() < searchModel.getAgeLimitLt());
        }
        if (searchModel.getYearGt() != null) {
            movies = movies.filter(movie -> {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(movie.getReleaseDate());
                return calendar.get(Calendar.YEAR) > searchModel.getYearGt();
            });
        }
        if (searchModel.getYearLt() != null) {
            movies = movies.filter(movie -> getYear(movie.getReleaseDate()) < searchModel.getYearLt());
        }
        Comparator<Movie> movieComparable = null;
        switch (searchModel.getSortOption()) {
            case IMDB -> movieComparable = Comparator.comparing(Movie::getImdbRate);
            case RELEASE_DATE_YEAR -> movieComparable = Comparator.comparing(movie -> getYear(movie.getReleaseDate()));
        }

        switch (searchModel.getSortOrder()) {
            case ASCENDING -> {
            }
            case DESCENDING -> movieComparable = movieComparable.reversed();
        }

        movies = movies.sorted(movieComparable);


        return movies;
    }

    private Integer getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
}
