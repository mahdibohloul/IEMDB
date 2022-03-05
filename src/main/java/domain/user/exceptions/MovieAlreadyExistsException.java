package domain.user.exceptions;

import infrastructure.exceptions.IemdbException;

public class MovieAlreadyExistsException extends IemdbException {
    public MovieAlreadyExistsException(String userEmail, Integer movieId) {
        super("Movie with id " + movieId + " already exists in user " + userEmail + " watchlist");
    }

    @Override
    public String toString() {
        return "MovieAlreadyExists";
    }
}
