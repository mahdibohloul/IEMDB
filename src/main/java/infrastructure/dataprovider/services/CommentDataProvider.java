package infrastructure.dataprovider.services;

import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.movie.services.MovieService;
import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;
import domain.user.services.UserService;
import infrastructure.AppConfig;
import infrastructure.dataprovider.models.CommentModel;
import infrastructure.http.services.HttpClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Stream;

@Component
@Order(value = 3)
public class CommentDataProvider implements DataProvider<CommentModel> {
    private final HttpClient httpClient;
    private final AppConfig appConfig;
    private final MovieService movieService;
    private final UserService userService;
    private final static String COMMENTS_URL = "comments";

    public CommentDataProvider(HttpClient httpClient, AppConfig appConfig,
            MovieService movieService, UserService userService) {
        this.httpClient = httpClient;
        this.appConfig = appConfig;
        this.movieService = movieService;
        this.userService = userService;
    }


    @Override
    public Stream<CommentModel> provide() throws IOException {
        String requestUrl = appConfig.getDataProviderUrl() + COMMENTS_URL;
        CommentModel[] commentModels = httpClient.get(requestUrl, CommentModel[].class);
        return Stream.of(commentModels);
    }

    @Override
    public void populateData(Stream<CommentModel> data) {
        data.forEach(commentModel -> {
            try {
                User user = userService.findUserByEmail(commentModel.getUserEmail());
                Movie movie = movieService.findMovieById(commentModel.getMovieId());
                movieService.addComment(movie, commentModel.getText(), user);
            } catch (UserNotFoundException | MovieNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
