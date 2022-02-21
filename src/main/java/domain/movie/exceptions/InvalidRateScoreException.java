package domain.movie.exceptions;

import infrastructure.exceptions.IemdbException;

public class InvalidRateScoreException extends IemdbException {
    public InvalidRateScoreException() {
        super("Invalid rate score");
    }

    @Override
    public String toString() {
        return "InvalidRateScore";
    }
}
