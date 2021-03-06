package domain.movie.services;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import domain.actor.exceptions.ActorNotFoundException;
import domain.actor.services.ActorService;
import domain.comment.models.Comment;
import domain.comment.services.CommentService;
import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.movie.models.MovieComment;
import domain.movie.repositories.MovieRepository;
import domain.movie.valueobjects.MovieSearchModel;
import domain.user.models.User;
import infrastructure.time.services.TimeService;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final CommentService commentService;
    private final TimeService timeService;
    private final ActorService actorService;

    public MovieServiceImpl(MovieRepository movieRepository,
            CommentService commentService,
            TimeService timeService, ActorService actorService) {
        this.movieRepository = movieRepository;
        this.commentService = commentService;
        this.timeService = timeService;
        this.actorService = actorService;
    }

    @Override
    public Movie insertMovie(Movie movie) throws ActorNotFoundException {
        validateCast(movie.getCast());
        return movieRepository.save(movie);
    }

    @Override
    public Movie findMovieById(Integer id) throws MovieNotFoundException {
        Movie movie = movieRepository.findById(id);
        if (movie == null) throw new MovieNotFoundException(id);
        return movie;
    }

    @Override
    public Integer addComment(Movie movie, String text, User user) {
        Comment comment = new Comment(user.getEmail(), text, timeService.getCurrentTimestamp());
        comment = commentService.insertComment(comment);
        movie.addComment(new MovieComment(movie.getId(), comment.getId()));
        updateMovie(movie);
        return comment.getId();
    }

    @Override
    public Stream<Movie> searchMovies(MovieSearchModel searchModel) {
        return movieRepository.searchMovies(searchModel);
    }

    private Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    private void validateCast(List<Integer> cast) throws ActorNotFoundException {
        if (cast != null) {
            for (Integer id : cast) {
                if (!actorService.existsActorById(id)) {
                    throw new ActorNotFoundException(id);
                }
            }
        }
    }
}
