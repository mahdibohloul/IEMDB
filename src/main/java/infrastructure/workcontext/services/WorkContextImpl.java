package infrastructure.workcontext.services;

import org.springframework.stereotype.Service;

import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;
import domain.user.services.UserService;

@Service
public class WorkContextImpl implements WorkContext {
    private User cachedUser;

    private final UserService userService;

    public WorkContextImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getCurrentUser() {
        return cachedUser;
    }

    @Override
    public User setCurrentUser(String userEmail) throws UserNotFoundException {
        cachedUser = userService.findUserByEmail(userEmail);
        return cachedUser;
    }

    @Override
    public void clearCurrentUser() {
        cachedUser = null;
    }
}
