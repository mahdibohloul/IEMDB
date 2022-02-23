package application.controllers;

import application.handlers.MovieHandler;
import application.models.request.GetUserWatchListRequestModel;
import application.models.request.UserRequestModel;
import application.models.request.AddUserWatchListRequestModel;
import application.models.response.GetUserWatchListResponseModel;
import application.models.response.MovieResponseModel;
import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.movie.services.MovieService;
import domain.user.exceptions.AgeLimitErrorException;
import domain.user.exceptions.MovieAlreadyExistsException;
import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;
import domain.user.models.UserWatchList;
import domain.user.services.UserService;
import domain.user.services.UserWatchListService;
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

    public void addUser(UserRequestModel userRequestModel) throws ParseException {
        User user = userRequestModel.toUser();
        userService.insertUser(user);
    }

    public void addToWatchList(AddUserWatchListRequestModel addUserWatchListRequestModel)
            throws UserNotFoundException, MovieNotFoundException, AgeLimitErrorException, MovieAlreadyExistsException {
        User user = userService.findUserByEmail(addUserWatchListRequestModel.getUserEmail());
        Movie movie = movieService.findMovieById(addUserWatchListRequestModel.getMovieId());
        userWatchListService.addToWatchList(user, movie);
    }

    public void removeFromWatchList(AddUserWatchListRequestModel addUserWatchListRequestModel)
            throws UserNotFoundException, MovieNotFoundException {
        User user = userService.findUserByEmail(addUserWatchListRequestModel.getUserEmail());
        userWatchListService.removeFromWatchList(user, addUserWatchListRequestModel.getMovieId());
    }

    public GetUserWatchListResponseModel getUserWatchList(GetUserWatchListRequestModel getUserWatchListRequestModel)
            throws UserNotFoundException {
        User user = userService.findUserByEmail(getUserWatchListRequestModel.getUserEmail());
        Stream<UserWatchList> watchList = userWatchListService.getUserWatchList(user);
        GetUserWatchListResponseModel response = new GetUserWatchListResponseModel();
        List<MovieResponseModel> movieResponseModels =
                watchList.map(UserWatchList::getMovieId).map(mvi -> {
                    try {
                        return movieService.findMovieById(mvi);
                    } catch (MovieNotFoundException e) {
                        return null;
                    }
                }).map(
                        movieHandler::getMovie).toList();
        response.setWatchList(movieResponseModels);
        return response;
    }
}
