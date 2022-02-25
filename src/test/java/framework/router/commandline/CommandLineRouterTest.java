package framework.router.commandline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.controllers.ActorController;
import application.controllers.CommentController;
import application.controllers.MovieController;
import application.controllers.UserController;
import application.models.response.GenericResponseModel;
import framework.router.commandline.exceptions.InvalidCommandException;
import lombok.SneakyThrows;

public class CommandLineRouterTest {
    @InjectMocks
    private CommandLineRouter commandLineRouter;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UserController userController;

    @Mock
    private MovieController movieController;

    @Mock
    private ActorController actorController;

    @Mock
    private CommentController commentController;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @SneakyThrows
    @Test
    @DisplayName("should throw exception if command handler not found")
    public void should_throw_exception_if_command_handler_not_found() {
        String command = "ADDACTOR {}";
        GenericResponseModel responseModel = new GenericResponseModel();
        responseModel.setUnsuccessfulResponse(new InvalidCommandException("Command not found").toString());
        Mockito.when(objectMapper.writeValueAsString(responseModel)).thenReturn(responseModel.toString());
        assert commandLineRouter.route(command).equals(responseModel.toString());
    }
}
