package framework.router.commandline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.core.JsonProcessingException;

import application.controllers.ActorController;
import application.controllers.CommentController;
import application.controllers.MovieController;
import application.controllers.UserController;
import application.models.request.*;
import application.models.response.GenericResponseModel;
import application.models.response.MoviesResponseModel;
import framework.router.commandline.exceptions.InvalidCommandException;
import infrastructure.json.services.JsonUtil;
import lombok.SneakyThrows;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class CommandLineRouterTest {
    @InjectMocks
    private CommandLineRouter commandLineRouter;

    @Mock
    private JsonUtil jsonUtil;

    @Mock
    private UserController userController;

    @Mock
    private MovieController movieController;

    @Mock
    private ActorController actorController;

    @Mock
    private CommentController commentController;

    private final PodamFactory podamFactory = new PodamFactoryImpl();


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
        Mockito.when(jsonUtil.toJson(responseModel)).thenReturn(responseModel.toString());
        assert commandLineRouter.route(command).equals(responseModel.toString());
    }

    @Test
    @DisplayName("should add actor with success")
    public void should_add_actor_with_success() throws JsonProcessingException {
        AddActorRequestModel requestModel = new AddActorRequestModel(
                "Marlon Brando",
                "1924-04-03",
                "American",
                1
        );
        GenericResponseModel response = new GenericResponseModel();
        response.setSuccessfulResponse("actor added successfully");
        String commandData =
                "{\"id\": 1, \"name\": \"Marlon Brando\", \"birthDate\": \"1924-04-03\", \"nationality\": \"American\"}";
        Mockito.when(jsonUtil.fromJson(commandData, AddActorRequestModel.class)).thenReturn(requestModel);
        Mockito.when(actorController.addActor(requestModel)).thenReturn(response);
        String responseData = "{\"success\": true,\"data\": \"actor added successfully\"}";
        Mockito.when(jsonUtil.toJson(response)).thenReturn(responseData);
        assert commandLineRouter.route("addActor " + commandData).equals(responseData);
    }

    @Test
    @DisplayName("should add movie with success")
    public void should_add_movie_with_success() throws JsonProcessingException {
        AddMovieRequestModel addMovieRequestModel = new AddMovieRequestModel();
        String commandData =
                "{\"id\": 1, \"name\": \"The Godfather\", \"summary\": \"The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.\", \"releaseDate\": \"1972-03-14\", \"director\": \"Francis Ford Coppola\", \"writers\": [\"Mario Puzo\", \"Francis Ford Coppola\"], \"genres\": [\"Crime\", \"Drama\"], \"cast\": [1, 2, 3], \"imdbRate\": 9.2, \"duration\": 175, \"ageLimit\": 14}";
        GenericResponseModel responseModel = new GenericResponseModel();
        responseModel.setSuccessfulResponse("movie added successfully");
        String responseData = "{\"success\": true, \"data\": \"movie added successfully\"}";
        Mockito.when(jsonUtil.fromJson(commandData, AddMovieRequestModel.class)).thenReturn(addMovieRequestModel);
        Mockito.when(movieController.addMovie(addMovieRequestModel)).thenReturn(responseModel);
        Mockito.when(jsonUtil.toJson(responseModel))
                .thenReturn(responseData);
        assert commandLineRouter.route("addMovie " + commandData).equals(responseData);
    }

    @Test
    @DisplayName("should add user with success")
    public void should_add_user_with_success() throws JsonProcessingException {
        AddUserRequestModel request = new AddUserRequestModel();
        String commandData =
                "{\"email\": \"sara@ut.ac.ir\", \"password\": \"sara1234\", \"name\": \"Sara\", \"nickname\": \"sara\", \"birthDate\": \"1998-03-11\"}";
        GenericResponseModel responseModel = new GenericResponseModel();
        responseModel.setSuccessfulResponse("user added successfully");
        String responseData = "{\"success\": true, \"data\": \"user added successfully\"}";
        Mockito.when(jsonUtil.fromJson(commandData, AddUserRequestModel.class)).thenReturn(request);
        Mockito.when(userController.addUser(request)).thenReturn(responseModel);
        Mockito.when(jsonUtil.toJson(responseModel))
                .thenReturn(responseData);
        assert commandLineRouter.route("addUser " + commandData).equals(responseData);
    }

    @Test
    @DisplayName("should add comment with success")
    public void should_add_comment_with_success() throws JsonProcessingException {
        AddCommentRequestModel request = new AddCommentRequestModel();
        String commandData =
                "{\"userEmail\": \"saman@ut.ac.ir\", \"movieId\": 1, \"text\": \"Interesting Movie\"}";
        GenericResponseModel responseModel = new GenericResponseModel();
        responseModel.setSuccessfulResponse("comment with id 1 added successfully");
        String responseData = "{\"success\": true, \"data\": \"comment with id 1 added successfully\"}";
        Mockito.when(jsonUtil.fromJson(commandData, AddCommentRequestModel.class)).thenReturn(request);
        Mockito.when(movieController.addComment(request)).thenReturn(responseModel);
        Mockito.when(jsonUtil.toJson(responseModel))
                .thenReturn(responseData);
        assert commandLineRouter.route("addComment " + commandData).equals(responseData);
    }

    @Test
    @DisplayName("should rate movie with success")
    public void should_rate_movie_with_success() throws JsonProcessingException {
        RateMovieRequestModel request = new RateMovieRequestModel();
        String commandData =
                "{\"userEmail\": \"saman@ut.ac.ir\", \"movieId\": 1, \"score\": 10}";
        GenericResponseModel responseModel = new GenericResponseModel();
        responseModel.setSuccessfulResponse("movie rated successfully");
        String responseData = "{\"success\": true, \"data\": \"movie rated successfully\"}";
        Mockito.when(jsonUtil.fromJson(commandData, RateMovieRequestModel.class)).thenReturn(request);
        Mockito.when(movieController.rateMovie(request)).thenReturn(responseModel);
        Mockito.when(jsonUtil.toJson(responseModel))
                .thenReturn(responseData);
        assert commandLineRouter.route("rateMovie " + commandData).equals(responseData);
    }

    @Test
    @DisplayName("should vote comment with success")
    public void should_vote_comment_with_success() throws JsonProcessingException {
        VoteCommentRequestModel request = new VoteCommentRequestModel();
        String commandData =
                "{\"userEmail\": \"sara@ut.ac.ir\", \"commentId\": 1, \"vote\": 1}";
        GenericResponseModel responseModel = new GenericResponseModel();
        responseModel.setSuccessfulResponse("comment voted successfully");
        String responseData = "{\"success\": true, \"data\": \"comment voted successfully\"}";
        Mockito.when(jsonUtil.fromJson(commandData, VoteCommentRequestModel.class)).thenReturn(request);
        Mockito.when(commentController.voteComment(request)).thenReturn(responseModel);
        Mockito.when(jsonUtil.toJson(responseModel))
                .thenReturn(responseData);
        assert commandLineRouter.route("voteComment " + commandData).equals(responseData);
    }

    @Test
    @DisplayName("should add to watch list with success")
    public void should_add_to_watch_list_with_success() throws JsonProcessingException {
        AddUserWatchListRequestModel request = new AddUserWatchListRequestModel();
        String commandData =
                "{\"userEmail\": \"saman@ut.ac.ir\", \"movieId\": 2}";
        GenericResponseModel responseModel = new GenericResponseModel();
        responseModel.setSuccessfulResponse("movie added to watchlist successfully");
        String responseData = "{\"success\": true, \"data\": \"movie added to watchlist successfully\"}";
        Mockito.when(jsonUtil.fromJson(commandData, AddUserWatchListRequestModel.class)).thenReturn(request);
        Mockito.when(userController.addToWatchList(request)).thenReturn(responseModel);
        Mockito.when(jsonUtil.toJson(responseModel))
                .thenReturn(responseData);
        assert commandLineRouter.route("addToWatchList " + commandData).equals(responseData);
    }

    @Test
    @DisplayName("should remove from watch list with success")
    public void should_remove_from_watch_list_with_success() throws JsonProcessingException {
        RemoveUserWatchListRequestModel request = new RemoveUserWatchListRequestModel();
        String commandData =
                "{\"userEmail\": \"sajjad@ut.ac.ir\", \"movieId\": 2}";
        GenericResponseModel responseModel = new GenericResponseModel();
        responseModel.setSuccessfulResponse("movie removed from watchlist successfully");
        String responseData = "{\"success\": true, \"data\": \"movie removed from watchlist successfully\"}";
        Mockito.when(jsonUtil.fromJson(commandData, RemoveUserWatchListRequestModel.class)).thenReturn(request);
        Mockito.when(userController.removeFromWatchList(request)).thenReturn(responseModel);
        Mockito.when(jsonUtil.toJson(responseModel))
                .thenReturn(responseData);
        assert commandLineRouter.route("removeFromWatchList " + commandData).equals(responseData);
    }

    @Test
    @DisplayName("should get movies list with success")
    public void should_get_movies_list_with_success() throws JsonProcessingException {
        MoviesResponseModel moviesResponseModel = podamFactory.manufacturePojo(MoviesResponseModel.class);
        GenericResponseModel responseModel = new GenericResponseModel();
        responseModel.setSuccessfulResponse(moviesResponseModel);
        String responseData =
                "{\"MoviesList\": [{\"movieId\": 1, \"name\": \"Fight Club\", \"director\": \"David Fincher\", \"genres\": [\"Drama\"], \"rating\": null}, {\"movieId\": 2, \"name\": \"The Prestige\", \"director\": \"Christopher Nolan\", \"genres\": [\"Drama\", \"Mystery\", \"Thriller\"], \"rating\": null}]}";
        Mockito.when(movieController.getMoviesList()).thenReturn(moviesResponseModel);
        Mockito.when(jsonUtil.toJson(responseModel))
                .thenReturn(responseData);
        assert commandLineRouter.route("getMoviesList").equals(responseData);
    }

    @Test
    @DisplayName("should get movie by id with success")
    public void should_get_movie_by_id_with_success() throws JsonProcessingException {
        GetMovieByIdRequestModel request = new GetMovieByIdRequestModel();
        String requestData = "{\"MovieId\": 8}";
        String responseData =
                "{\"movieId\": 2, \"name\": \"The Pianist\", \"summary\": \"A Polish Jewish musician struggles to survive the destruction of the Warsaw ghetto of World War II.\", \"releaseDate\": \"2002-05-24\", \"director\": \"Roman Polanski\", \"writers\": [\"Ronald Harwood\", \"Wladyslaw Szpilman\"], \"genres\": [\"Biography\", \"Drama\", \"Music\"], \"cast\": [{\"actorId\": 4, \"name\": \"Adrien Brody\"}, {\"actorId\": 5, \"name\": \"Thomas Kretschmann\"}, {\"actorId\": 6, \"name\": \"Frank Finlay\"}], \"rating\": null, \"duration\": 150, \"ageLimit\": 12, \"comments\": [{\"commentId\": 2, \"userEmail\": \"sara@ut.ac.ir\", \"text\": \"I like it\", \"like\": 1, \"dislike\": 1}]}";
        GenericResponseModel responseModel = new GenericResponseModel();
        responseModel.setSuccessfulResponse(responseData);
        Mockito.when(jsonUtil.fromJson(requestData, GetMovieByIdRequestModel.class)).thenReturn(request);
        Mockito.when(movieController.getMovieById(request)).thenReturn(responseModel);
        Mockito.when(jsonUtil.toJson(responseModel))
                .thenReturn(responseData);
        assert commandLineRouter.route("getMovieById " + requestData).equals(responseData);
    }

    @Test
    @DisplayName("should get movies by genre with success")
    public void should_get_movies_by_genre_with_success() throws JsonProcessingException {
        GetMoviesByGenreRequestModel request = new GetMoviesByGenreRequestModel();
        String requestData = "{\"genre\": \"Mystery\"}";
        String responseData =
                "{\"MoviesListByGenre\": [{\"movieId\": 2, \"name\": \"The Prestige\", \"director\": \"Christopher Nolan\", \"genres\": [\"Drama\", \"Mystery\", \"Thriller\"], \"rating\": null}, {\"movieId\": 3, \"name\": \"Seven\", \"director\": \"David Fincher\", \"genres\": [\"Crime\", \"Drama\", \"Mystery\"], \"rating\": null}]}";
        GenericResponseModel responseModel = new GenericResponseModel();
        MoviesResponseModel moviesResponseModel = podamFactory.manufacturePojo(MoviesResponseModel.class);
        responseModel.setSuccessfulResponse(moviesResponseModel);
        Mockito.when(jsonUtil.fromJson(requestData, GetMoviesByGenreRequestModel.class)).thenReturn(request);
        Mockito.when(movieController.getMoviesByGenre(request)).thenReturn(moviesResponseModel);
        Mockito.when(jsonUtil.toJson(responseModel))
                .thenReturn(responseData);
        assert commandLineRouter.route("getMoviesByGenre " + requestData).equals(responseData);
    }
    
    @Test
    @DisplayName("should get watch list with success")
    public void should_get_watch_list_with_success() throws JsonProcessingException {
        GetUserWatchListRequestModel request = new GetUserWatchListRequestModel();
        String requestData = "{\"userEmail\": \"sajjad@ut.ac.ir\"}";
        String responseData =
                "{\"WatchList\": [{\"movieId\": 2, \"name\": \"The Pianist\", \"director\": \"Roman Polanski\", \"genres\": [\"Biography\", \"Drama\", \"Music\"], rating: null}]}";
        GenericResponseModel responseModel = new GenericResponseModel();
        responseModel.setSuccessfulResponse(responseData);
        Mockito.when(jsonUtil.fromJson(requestData, GetUserWatchListRequestModel.class)).thenReturn(request);
        Mockito.when(userController.getUserWatchList(request)).thenReturn(responseModel);
        Mockito.when(jsonUtil.toJson(responseModel))
                .thenReturn(responseData);
        assert commandLineRouter.route("getWatchList " + requestData).equals(responseData);
    }
}
