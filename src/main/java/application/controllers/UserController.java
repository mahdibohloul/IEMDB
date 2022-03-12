package application.controllers;

import application.handlers.MovieHandler;
import application.models.request.*;
import application.models.response.GenericResponseModel;
import application.models.response.GetUserWatchListResponseModel;
import application.models.response.MovieResponseModel;
import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.movie.services.MovieService;
import domain.user.exceptions.DuplicateUserEmailException;
import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;
import domain.user.models.UserWatchList;
import domain.user.services.UserService;
import domain.user.services.UserWatchListService;
import framework.router.commandline.exceptions.InvalidCommandException;
import infrastructure.exceptions.IemdbException;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class UserController {

    private final UserService userService;
    private final UserWatchListService userWatchListService;
    private final MovieService movieService;
    private final MovieHandler movieHandler;

    public UserController(UserService userService, UserWatchListService userWatchListService,
            MovieService movieService, MovieHandler movieHandler) {
        this.userService = userService;
        this.userWatchListService = userWatchListService;
        this.movieService = movieService;
        this.movieHandler = movieHandler;
    }

    public GenericResponseModel addUser(AddUserRequestModel addUserRequestModel) {
        GenericResponseModel response = new GenericResponseModel();
        try {
            User user = addUserRequestModel.toUser();
            userService.insertUser(user);
            response.setSuccessfulResponse("user added successfully");
        } catch (ParseException e) {
            response.setUnsuccessfulResponse(new InvalidCommandException("Invalid date format").toString());
        } catch (DuplicateUserEmailException e) {
            response.setUnsuccessfulResponse(e.toString());
        }
        return response;
    }

    public GenericResponseModel addToWatchList(AddUserWatchListRequestModel addUserWatchListRequestModel) {
        GenericResponseModel response = new GenericResponseModel();
        try {
            User user = userService.findUserByEmail(addUserWatchListRequestModel.getUserEmail());
            Movie movie = movieService.findMovieById(addUserWatchListRequestModel.getMovieId());
            userWatchListService.addToWatchList(user, movie);
            response.setSuccessfulResponse("movie added to watchlist successfully");
        } catch (IemdbException e) {
            response.setUnsuccessfulResponse(e.toString());
        }
        return response;
    }

    public GenericResponseModel removeFromWatchList(RemoveUserWatchListRequestModel removeUserWatchListRequestModel) {
        GenericResponseModel response = new GenericResponseModel();
        try {
            User user = userService.findUserByEmail(removeUserWatchListRequestModel.getUserEmail());
            userWatchListService.removeFromWatchList(user, removeUserWatchListRequestModel.getMovieId());
            response.setSuccessfulResponse("movie removed from watchlist successfully");
        } catch (IemdbException e) {
            response.setUnsuccessfulResponse(e.toString());
        }
        return response;
    }

    public GenericResponseModel getUserRecommendedMovies(GetUserRecommendedMoviesRequestModel requestModel) {
        GenericResponseModel response = new GenericResponseModel();
        try {
            User user = userService.findUserByEmail(requestModel.getUserEmail());
            List<Movie> movies = userWatchListService.getRecommendedMovie(user, requestModel.getCount()).toList();
            List<MovieResponseModel> movieResponseModels = movies.stream().map(movieHandler::getMovie).toList();
            response.setSuccessfulResponse(movieResponseModels);
        } catch (UserNotFoundException e) {
            response.setUnsuccessfulResponse(e.toString());
        }
        return response;
    }

    public GenericResponseModel getUserWatchList(GetUserWatchListRequestModel getUserWatchListRequestModel) {
        GenericResponseModel response = new GenericResponseModel();
        try {
            User user = userService.findUserByEmail(getUserWatchListRequestModel.getUserEmail());
            Stream<UserWatchList> watchList = userWatchListService.getUserWatchList(user);
            GetUserWatchListResponseModel getUserWatchListResponseModel = new GetUserWatchListResponseModel();
            List<MovieResponseModel> movieResponseModels =
                    watchList.map(UserWatchList::getMovieId).map(mvi -> {
                        try {
                            return movieService.findMovieById(mvi);
                        } catch (MovieNotFoundException e) {
                            return null;
                        }
                    }).map(
                            movieHandler::getMovie).toList();
            getUserWatchListResponseModel.setWatchList(movieResponseModels);
            getUserWatchListResponseModel.setUserEmail(user.getEmail());
            getUserWatchListResponseModel.setUserName(user.getName());
            getUserWatchListResponseModel.setUserNickname(user.getNickname());
            response.setSuccessfulResponse(getUserWatchListResponseModel);
        } catch (IemdbException e) {
            response.setUnsuccessfulResponse(e.toString());
        }
        return response;
    }
}
