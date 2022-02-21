package domain.comment.exceptions;

import infrastructure.exceptions.IemdbException;

public class InvalidVoteValueException extends IemdbException {
    public InvalidVoteValueException(int vote) {
        super("Invalid vote value: " + vote);
    }
}
