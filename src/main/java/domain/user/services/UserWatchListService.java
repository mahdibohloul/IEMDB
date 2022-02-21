package domain.user.services;

import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.user.exceptions.AgeLimitErrorException;
import domain.user.exceptions.MovieAlreadyExistsException;
import domain.user.models.User;
import domain.user.models.UserWatchList;

import java.util.stream.Stream;

public interface UserWatchListService {
    UserWatchList addToWatchList(User user, Movie movie) throws MovieAlreadyExistsException, AgeLimitErrorException;

    void removeFromWatchList(User user, Movie movie) throws MovieNotFoundException;

    Stream<UserWatchList> getUserWatchList(User user);
}