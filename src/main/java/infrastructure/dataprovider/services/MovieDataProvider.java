package infrastructure.dataprovider.services;

import domain.actor.exceptions.ActorNotFoundException;
import domain.movie.models.Movie;
import domain.movie.services.MovieService;
import infrastructure.AppConfig;
import infrastructure.dataprovider.models.MovieModel;
import infrastructure.http.services.HttpClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Stream;

@Component
@Order(value = 2)
public class MovieDataProvider implements DataProvider<MovieModel> {
    private final HttpClient httpClient;
    private final MovieService movieService;
    private final AppConfig appConfig;
    private final String MOVIES_URL = "movies";

    public MovieDataProvider(HttpClient httpClient, MovieService movieService, AppConfig appConfig) {
        this.httpClient = httpClient;
        this.movieService = movieService;
        this.appConfig = appConfig;
    }


    @Override
    public Stream<MovieModel> provide() throws IOException {
        String requestUrl = appConfig.getDataProviderUrl() + MOVIES_URL;
        MovieModel[] movieModels = httpClient.get(requestUrl, MovieModel[].class);
        return Stream.of(movieModels);
    }

    @Override
    public void populateData(Stream<MovieModel> data) {
        data.map(this::toMovie).forEach(movie -> {
            try {
                movieService.insertMovie(movie);
            } catch (ActorNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private Movie toMovie(MovieModel movieModel) {
        Movie movie = new Movie();
        movie.setId(movieModel.getId());
        movie.setName(movieModel.getName());
        movie.setSummary(movieModel.getSummary());
        movie.setReleaseDate(movieModel.getReleaseDate());
        movie.setDirector(movieModel.getDirector());
        movie.setWriters(movieModel.getWriters());
        movie.setCast(movieModel.getCast());
        movie.setImdbRate(movieModel.getImdbRate());
        movie.setDuration(movieModel.getDuration());
        movie.setAgeLimit(movieModel.getAgeLimit());
        return movie;
    }
}
