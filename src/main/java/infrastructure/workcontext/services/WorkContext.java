package infrastructure.workcontext.services;

import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;

public interface WorkContext {
    User getCurrentUser();
    User setCurrentUser(String userEmail) throws UserNotFoundException;
    void clearCurrentUser();
}
