package domain.movie.exceptions;

import infrastructure.exceptions.IemdbException;

public class MovieNotFoundException extends IemdbException {
    public MovieNotFoundException(Integer movieId) {
        super("Movie with id " + movieId + " not found");
    }

    @Override
    public String toString() {
        return "MovieNotFound";
    }
}
