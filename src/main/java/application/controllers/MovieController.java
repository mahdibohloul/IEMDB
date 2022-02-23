package application.controllers;

import application.handlers.MovieHandler;
import application.models.request.CommentRequestModel;
import application.models.response.MovieDetailResponseModel;
import application.models.response.MoviesResponseModel;
import application.models.request.MovieRequestModel;
import application.models.request.MovieScoreRequestModel;
import domain.movie.exceptions.InvalidRateScoreException;
import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.movie.services.MovieScoreService;
import domain.movie.services.MovieService;
import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;
import domain.user.services.UserService;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.List;

@Controller
public class MovieController {
    private final MovieService movieService;
    private final UserService userService;
    private final MovieScoreService movieScoreService;
    private final MovieHandler movieHandler;

    public MovieController(MovieService movieService, UserService userService,
            MovieScoreService movieScoreService,
            MovieHandler movieHandler) {
        this.movieService = movieService;
        this.userService = userService;
        this.movieScoreService = movieScoreService;
        this.movieHandler = movieHandler;
    }

    public void addMove(MovieRequestModel movieRequestModel) throws ParseException {
        Movie movie = movieRequestModel.toMovie();
        movieService.insertMovie(movie);
    }

    public void addComment(CommentRequestModel commentRequestModel)
            throws MovieNotFoundException, UserNotFoundException {
        Movie movie = movieService.findMovieById(commentRequestModel.getMovieId());
        User user = userService.findUserByEmail(commentRequestModel.getUserEmail());
        movieService.addComment(movie, commentRequestModel.getText(), user);
    }

    public void rateMovie(MovieScoreRequestModel movieScoreRequestModel)
            throws MovieNotFoundException, UserNotFoundException, InvalidRateScoreException {
        Movie movie = movieService.findMovieById(movieScoreRequestModel.getMovieId());
        User user = userService.findUserByEmail(movieScoreRequestModel.getUserEmail());
        movieScoreService.scoreMovie(movie, user, movieScoreRequestModel.getScore());
    }

    public MoviesResponseModel getMoviesList() {
        return movieHandler.getMovieList(null, null, null, null, null, null, null, null, null);
    }

    public MovieDetailResponseModel getMovieById(Integer id) throws MovieNotFoundException {
        return movieHandler.findMovieById(id);
    }

    public MoviesResponseModel getMoviesByGenre(String genre) {
        return movieHandler.getMovieList(null, null, null, null, List.of(genre), null, null, null, null);
    }


}
