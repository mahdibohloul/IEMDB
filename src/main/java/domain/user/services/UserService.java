package domain.user.services;

import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;

public interface UserService {
    User insertUser(User user);
    User findUserByEmail(String email) throws UserNotFoundException;
}
