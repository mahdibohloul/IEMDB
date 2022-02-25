package framework.router.commandline;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.controllers.ActorController;
import application.controllers.CommentController;
import application.controllers.MovieController;
import application.controllers.UserController;
import application.models.request.*;
import application.models.response.GenericResponseModel;
import application.models.response.MoviesResponseModel;
import framework.router.commandline.annotation.CommandType;
import framework.router.commandline.exceptions.InvalidCommandException;
import lombok.SneakyThrows;

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
            responseModel = commentController.voteComment(request);
        } catch (JsonMappingException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException(commandData).toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    @CommandType(command = "addToWatchList")
    private String addToWatchList(String commandData) throws JsonProcessingException {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            AddUserWatchListRequestModel request =
                    objectMapper.readValue(commandData, AddUserWatchListRequestModel.class);
            responseModel = userController.addToWatchList(request);
        } catch (JsonMappingException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException(commandData).toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    @CommandType(command = "removeFromWatchList")
    private String removeFromWatchList(String commandData) throws JsonProcessingException {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            RemoveUserWatchListRequestModel request =
                    objectMapper.readValue(commandData, RemoveUserWatchListRequestModel.class);
            responseModel = userController.removeFromWatchList(request);
        } catch (JsonMappingException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException(commandData).toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    @CommandType(command = "getMoviesList")
    private String getMoviesList(String commandData) throws JsonProcessingException {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            if (commandData != null && !commandData.isEmpty()) throw new InvalidCommandException(commandData);
            MoviesResponseModel moviesResponseModel = movieController.getMoviesList();

            responseModel.setSuccessfulResponse(moviesResponseModel);
        } catch (InvalidCommandException e) {
            responseModel.setUnsuccessfulResponse(e.toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    @CommandType(command = "getMovieById")
    private String getMovieById(String commandData) throws JsonProcessingException {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            GetMovieByIdRequestModel request = objectMapper.readValue(commandData, GetMovieByIdRequestModel.class);
            responseModel = movieController.getMovieById(request);
        } catch (JsonMappingException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException(commandData).toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    @CommandType(command = "getMoviesByGenre")
    private String getMoviesByGenre(String commandData) throws JsonProcessingException {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            GetMoviesByGenreRequestModel request =
                    objectMapper.readValue(commandData, GetMoviesByGenreRequestModel.class);
            MoviesResponseModel moviesResponseModel = movieController.getMoviesByGenre(request);
            responseModel.setSuccessfulResponse(moviesResponseModel);
        } catch (JsonMappingException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException(commandData).toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    @CommandType(command = "getWatchList")
    private String getWatchList(String commandData) throws JsonProcessingException {
        GenericResponseModel responseModel = new GenericResponseModel();
        try {
            GetUserWatchListRequestModel request =
                    objectMapper.readValue(commandData, GetUserWatchListRequestModel.class);
            responseModel = userController.getUserWatchList(request);
        } catch (JsonMappingException e) {
            responseModel.setUnsuccessfulResponse(new InvalidCommandException(commandData).toString());
        }
        return objectMapper.writeValueAsString(responseModel);
    }

    private Method getHandlerFunction(String commandType) throws InvalidCommandException {
        Method[] methods = this.getClass().getDeclaredMethods();
        return Arrays.stream(methods).toList()
                .stream().filter(method -> method.isAnnotationPresent(CommandType.class))
                .filter(method -> method.getAnnotation(CommandType.class).command().equals(commandType))
                .findFirst().orElseThrow(() -> new InvalidCommandException(commandType));
    }

}
