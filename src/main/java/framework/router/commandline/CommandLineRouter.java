package framework.router.commandline;

import application.controllers.ActorController;
import application.controllers.CommentController;
import application.controllers.MovieController;
import application.controllers.UserController;
import application.models.request.*;
import application.models.response.GenericResponseModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import framework.router.commandline.annotation.CommandType;
import framework.router.commandline.exceptions.InvalidCommandException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class CommandLineRouter {

    private final ObjectMapper objectMapper;
    private final UserController userController;
    private final MovieController movieController;
    private final ActorController actorController;
    private final CommentController commentController;

    public CommandLineRouter(ObjectMapper objectMapper, UserController userController,
            MovieController movieController, ActorController actorController,
            CommentController commentController) {
        this.objectMapper = objectMapper;
        this.movieController = movieController;
        this.userController = userController;
        this.actorController = actorController;
        this.commentController = commentController;
    }

    @SneakyThrows
    public String route(String command) {
        try {
            String[] commandParts = command.split(" ", 2);
            String commandName = commandParts[0];
            String commandData = commandParts.length > 1 ? commandParts[1] : "";
            Method handler = getHandlerFunction(commandName);
            return (String) handler.invoke(this, commandData);
        } catch (Exception e) {
            GenericResponseModel response = new GenericResponseModel();
            response.setUnsuccessfulResponse(e.toString());
            return objectMapper.writeValueAsString(response);
        }
    }


    @CommandType(command = "addActor")
    private String addActor(String commandData) throws JsonProcessingException {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            AddActorRequestModel request = objectMapper.readValue(commandData, AddActorRequestModel.class);
            responseModel = actorController.addActor(request);
        } catch (JsonProcessingException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException(commandData).toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    @CommandType(command = "addMovie")
    private String addMovie(String commandData) throws JsonProcessingException {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            AddMovieRequestModel request = objectMapper.readValue(commandData, AddMovieRequestModel.class);
            responseModel = movieController.addMove(request);
        } catch (JsonProcessingException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException(commandData).toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    @CommandType(command = "addUser")
    private String addUser(String commandData) throws JsonProcessingException {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            UserRequestModel request = objectMapper.readValue(commandData, UserRequestModel.class);
            responseModel = userController.addUser(request);
        } catch (JsonProcessingException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException(commandData).toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    @CommandType(command = "addComment")
    private String addComment(String commandData) throws JsonProcessingException {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            AddCommentRequestModel request = objectMapper.readValue(commandData, AddCommentRequestModel.class);
            responseModel = movieController.addComment(request);
        } catch (JsonProcessingException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException(commandData).toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    @CommandType(command = "rateMovie")
    private String rateMovie(String commandData) throws JsonProcessingException {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            RateMovieRequestModel request = objectMapper.readValue(commandData, RateMovieRequestModel.class);
            responseModel = movieController.rateMovie(request);
        } catch (JsonMappingException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException(commandData).toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    @CommandType(command = "voteComment")
    private String voteComment(String commandData) throws JsonProcessingException {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            VoteCommentRequestModel request = objectMapper.readValue(commandData, VoteCommentRequestModel.class);
            responseModel = responseModel = commentController.voteComment(request);
        } catch (JsonMappingException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException(commandData).toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    @CommandType(command = "addToWatchList")
    private String addToWatchList(String commandData) {
        return "";
    }

    @CommandType(command = "removeFromWatchList")
    private String removeFromWatchList(String commandData) {
        return "";
    }

    @CommandType(command = "getMoviesList")
    private String getMoviesList(String commandData) {
        return "";
    }

    @CommandType(command = "getMovieById")
    private String getMovieById(String commandData) {
        return "";
    }

    @CommandType(command = "getMoviesByGenre")
    private String getMoviesByGenre(String commandData) {
        return "";
    }

    private Method getHandlerFunction(String commandType) throws InvalidCommandException {
        Method[] methods = this.getClass().getDeclaredMethods();
        return Arrays.stream(methods).toList()
                .stream().filter(method -> method.isAnnotationPresent(CommandType.class))
                .filter(method -> method.getAnnotation(CommandType.class).command().equals(commandType))
                .findFirst().orElseThrow(() -> new InvalidCommandException(commandType));
    }

}
