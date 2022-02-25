package domain.user.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;
import domain.user.repositories.UserRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private final PodamFactory podamFactory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should insert user with success")
    void should_insert_user_with_success() {
        User user = podamFactory.manufacturePojo(User.class);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        assert userService.insertUser(user).equals(user);
    }

    @Test
    @DisplayName("should find user by email with success")
    void should_find_user_by_email_with_success() throws UserNotFoundException {
        User user = podamFactory.manufacturePojo(User.class);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        Assertions.assertDoesNotThrow(() -> userService.findUserByEmail(user.getEmail()));
        assert userService.findUserByEmail(user.getEmail()).equals(user);
    }

    @Test
    @DisplayName("should throw exception when user not found")
    public void should_throw_exception_when_find_user_by_email_with_fail() {
        User user = podamFactory.manufacturePojo(User.class);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail(user.getEmail()));
    }


}
