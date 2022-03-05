package application.controllers;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Controller;

import application.handlers.MovieHandler;
import application.models.request.*;
import application.models.response.GenericResponseModel;
import application.models.response.MovieDetailResponseModel;
import application.models.response.MoviesResponseModel;
import domain.actor.exceptions.ActorNotFoundException;
import domain.movie.models.Movie;
import domain.movie.services.MovieScoreService;
import domain.movie.services.MovieService;
import domain.user.models.User;
import domain.user.services.UserService;
import framework.router.commandline.exceptions.InvalidCommandException;
import infrastructure.exceptions.IemdbException;

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

    public GenericResponseModel addMovie(AddMovieRequestModel addMovieRequestModel) {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            Movie movie = addMovieRequestModel.toMovie();
            movieService.insertMovie(movie);
            responseModel.setSuccessfulResponse("movie added successfully");
        } catch (ParseException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException("invalid date format").toString());
        } catch (ActorNotFoundException e) {
            responseModel.setUnsuccessfulResponse(e.toString());
        }
        return responseModel;
    }

    public GenericResponseModel addComment(AddCommentRequestModel addCommentRequestModel) {
        GenericResponseModel response = new GenericResponseModel();
        try {
            Movie movie = movieService.findMovieById(addCommentRequestModel.getMovieId());
            User user = userService.findUserByEmail(addCommentRequestModel.getUserEmail());
            Integer commentId = movieService.addComment(movie, addCommentRequestModel.getText(), user);
            response.setSuccessfulResponse(MessageFormat.format("comment with id {0} added successfully", commentId));
        } catch (IemdbException e) {
            response.setUnsuccessfulResponse(e.toString());
        }
        return response;
    }

    public GenericResponseModel rateMovie(RateMovieRequestModel rateMovieRequestModel) {
        GenericResponseModel response = new GenericResponseModel();
        try {
            Movie movie = movieService.findMovieById(rateMovieRequestModel.getMovieId());
            User user = userService.findUserByEmail(rateMovieRequestModel.getUserEmail());
            movieScoreService.scoreMovie(movie, user, rateMovieRequestModel.getScore());
            response.setSuccessfulResponse("movie rated successfully");
        } catch (IemdbException ex) {
            response.setUnsuccessfulResponse(ex.toString());
        }
        return response;
    }

    public MoviesResponseModel getMoviesList() {
        return movieHandler.getMovieList(null,
                null, null, null, null,
                null, null, null, null, null, null, null);
    }

    public GenericResponseModel getMovieById(GetMovieByIdRequestModel getMovieByIdRequestModel) {
        GenericResponseModel response = new GenericResponseModel();
        try {
            MovieDetailResponseModel movieDetail = movieHandler.findMovieById(getMovieByIdRequestModel.getMovieId());
            response.setSuccessfulResponse(movieDetail);
        } catch (IemdbException e) {
            response.setUnsuccessfulResponse(e.toString());
        }
        return response;
    }

    public MoviesResponseModel getMoviesByGenre(GetMoviesByGenreRequestModel getMoviesByGenreRequestModel) {
        return movieHandler.getMovieList(null, null, null, null, null,
                List.of(getMoviesByGenreRequestModel.getGenre()), null,
                null, null, null, null, null);
    }

    public MoviesResponseModel getMoviesByYear(GetMoviesByYearRequestModel getMoviesByYearRequestModel) {
        return movieHandler.getMovieList(null, null, null, null,
                null, null, null, null, null, null,
                getMoviesByYearRequestModel.getStartYear(),
                getMoviesByYearRequestModel.getEndYear());
    }


}
