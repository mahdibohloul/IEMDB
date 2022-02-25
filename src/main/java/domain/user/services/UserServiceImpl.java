package domain.user.services;

import org.springframework.stereotype.Service;

import domain.user.exceptions.DuplicateUserEmailException;
import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;
import domain.user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User insertUser(User user) throws DuplicateUserEmailException {
        try {
            findUserByEmail(user.getEmail());
            throw new DuplicateUserEmailException(user.getEmail());
        } catch (UserNotFoundException e) {
            return userRepository.save(user);
        }
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UserNotFoundException(email);
        return user;
    }
}
