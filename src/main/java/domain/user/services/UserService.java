package domain.user.services;

import domain.user.exceptions.DuplicateUserEmailException;
import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;

public interface UserService {
    User insertUser(User user) throws DuplicateUserEmailException;
    User findUserByEmail(String email) throws UserNotFoundException;
}
