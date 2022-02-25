package domain.user.exceptions;

import infrastructure.exceptions.IemdbException;

public class DuplicateUserEmailException extends IemdbException {
    public DuplicateUserEmailException(String userEmail) {
        super("User with email " + userEmail + " already exists");
    }

    @Override
    public String toString() {
        return "DuplicateUserEmail";
    }
}
