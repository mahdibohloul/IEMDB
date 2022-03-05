package application.handlers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import application.models.response.*;
import domain.actor.models.Actor;
import domain.actor.services.ActorService;
import domain.comment.models.Comment;
import domain.comment.models.CommentVote;
import domain.comment.services.CommentService;
import domain.comment.services.CommentVoteService;
import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.movie.models.MovieComment;
import domain.movie.services.MovieRateService;
import domain.movie.services.MovieService;
import domain.user.exceptions.UserNotFoundException;
import domain.user.models.User;
import domain.user.services.UserService;
import infrastructure.BaseEntity;

@Component
public class MovieHandler {
    private final MovieService movieService;
    private final MovieRateService movieRateService;
    private final ActorService actorService;
    private final CommentService commentService;
    private final CommentVoteService commentVoteService;
    private final UserService userService;

    public MovieHandler(MovieService movieService, MovieRateService movieRateService,
            ActorService actorService, CommentService commentService,
            CommentVoteService commentVoteService, UserService userService) {
        this.movieService = movieService;
        this.movieRateService = movieRateService;
        this.actorService = actorService;
        this.commentService = commentService;
        this.commentVoteService = commentVoteService;
        this.userService = userService;
    }

    public MovieResponseModel getMovie(Movie movie) {
        Double movieRate = movieRateService.getMovieRate(movie);
        Stream<Actor> actorStream = actorService.searchActors(movie.getCast());
        return new MovieResponseModel(movie, movieRate, actorStream.toList());
    }

    public MoviesResponseModel getMovieList(
            List<Integer> ids,
            List<String> names,
            List<String> directors,
            List<Integer> cast,
            List<String> writers, List<String> genres,
            Double imdbRateGt, Double imdbRateLt, Integer ageLimitGt, Integer ageLimitLt, Integer yearGt,
            Integer yearLt) {
        Stream<Movie> movieStream =
                movieService.searchMovies(ids, names, directors, writers, genres, cast, imdbRateGt, imdbRateLt,
                        ageLimitGt,
                        ageLimitLt, yearGt, yearLt);
        MoviesResponseModel movieList = new MoviesResponseModel();
        List<MovieResponseModel> movieResponseModels =
                movieStream.map(this::getMovie).toList();
        movieList.setMoviesList(movieResponseModels);
        return movieList;
    }

    public MovieDetailResponseModel findMovieById(Integer id) throws MovieNotFoundException {
        Movie movie = movieService.findMovieById(id);
        Stream<ActorResponseModel> cast =
                actorService.searchActors(movie.getCast()).map(ActorResponseModel::new);
        List<Comment> comments =
                commentService.searchComments(movie.getComments().stream().map(MovieComment::getCommentId).toList())
                        .toList();

        Map<Integer, List<CommentVote>> commentVotes = comments.stream().collect(Collectors.toMap(BaseEntity::getId,
                comment -> commentVoteService.findAllByCommentId(comment.getId()).toList()));
        Map<Integer, User> userComments = comments.stream().collect(Collectors.toMap(BaseEntity::getId,
                comment -> {
                    try {
                        return userService.findUserByEmail(comment.getUserEmail());
                    } catch (UserNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }));
        Stream<CommentResponseModel> commentResponseModels =
                comments.stream().map(comment -> new CommentResponseModel(comment, commentVotes.get(comment.getId()),
                        userComments.get(comment.getId())));
        Double movieRate = movieRateService.getMovieRate(movie);
        return new MovieDetailResponseModel(movie, commentResponseModels.toList(), cast.toList(), movieRate);
    }
}
