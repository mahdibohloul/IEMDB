package application.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;

import application.handlers.MovieHandler;
import application.models.request.AddUserWatchListRequestModel;
import application.models.request.GetUserWatchListRequestModel;
import application.models.request.RemoveUserWatchListRequestModel;
import application.models.request.UserRequestModel;
import application.models.response.GenericResponseModel;
import application.models.response.GetUserWatchListResponseModel;
import application.models.response.MovieResponseModel;
import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.movie.services.MovieService;
import domain.user.exceptions.DuplicateUserEmailException;
import domain.user.models.User;
import domain.user.models.UserWatchList;
import domain.user.services.UserService;
import domain.user.services.UserWatchListService;
import framework.router.commandline.exceptions.InvalidCommandException;
import infrastructure.exceptions.IemdbException;

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

    public GenericResponseModel addUser(UserRequestModel userRequestModel) {
        GenericResponseModel response = new GenericResponseModel();
        try {
            User user = userRequestModel.toUser();
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
            response.setSuccessfulResponse(getUserWatchListResponseModel);
        } catch (IemdbException e) {
            response.setUnsuccessfulResponse(e.toString());
        }
        return response;
    }
}
