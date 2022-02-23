package application.handlers;

import application.models.response.*;
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
import infrastructure.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MovieHandler {
    private final MovieService movieService;
    private final MovieRateService movieRateService;
    private final ActorService actorService;
    private final CommentService commentService;
    private final CommentVoteService commentVoteService;

    public MovieHandler(MovieService movieService, MovieRateService movieRateService,
            ActorService actorService, CommentService commentService,
            CommentVoteService commentVoteService) {
        this.movieService = movieService;
        this.movieRateService = movieRateService;
        this.actorService = actorService;
        this.commentService = commentService;
        this.commentVoteService = commentVoteService;
    }

    public MovieResponseModel getMovie(Movie movie) {
        Double movieRate = movieRateService.getMovieRate(movie);
        return new MovieResponseModel(movie, movieRate);
    }

    public MoviesResponseModel getMovieList(List<Integer> ids, List<String> names, List<String> directors,
            List<String> writers, List<String> genres,
            Double imdbRateGt, Double imdbRateLt, Integer ageLimitGt, Integer ageLimitLt) {
        Stream<Movie> movieStream =
                movieService.searchMovies(ids, names, directors, writers, genres, imdbRateGt, imdbRateLt, ageLimitGt,
                        ageLimitLt);
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
        Stream<Comment> comments =
                commentService.searchComments(movie.getComments().stream().map(MovieComment::getCommentId).toList());
        Map<Integer, List<CommentVote>> commentVotes = comments.collect(Collectors.toMap(BaseEntity::getId,
                comment -> commentVoteService.findAllByCommentId(comment.getId()).toList()));
        Stream<CommentResponseModel> commentResponseModels =
                comments.map(comment -> new CommentResponseModel(comment, commentVotes.get(comment.getId())));
        Double movieRate = movieRateService.getMovieRate(movie);
        return new MovieDetailResponseModel(movie, commentResponseModels.toList(), cast.toList(), movieRate);
    }
}
