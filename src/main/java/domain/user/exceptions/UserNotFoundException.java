package domain.user.exceptions;

import infrastructure.exceptions.IemdbException;

public class UserNotFoundException extends IemdbException {
    public UserNotFoundException(String userEmail) {
        super("User with email " + userEmail + " not found");
    }

    public UserNotFoundException(Integer userId) {
        super("User with id " + userId + " not found");
    }

    @Override
    public String toString() {
        return "UserNotFound";
    }
}
