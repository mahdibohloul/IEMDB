package domain.movie.services;

import domain.comment.models.Comment;
import domain.comment.services.CommentService;
import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.movie.models.MovieComment;
import domain.movie.repositories.MovieRepository;
import domain.user.models.User;
import infrastructure.time.services.TimeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final CommentService commentService;
    private final TimeService timeService;

    public MovieServiceImpl(MovieRepository movieRepository,
            CommentService commentService,
            TimeService timeService) {
        this.movieRepository = movieRepository;
        this.commentService = commentService;
        this.timeService = timeService;
    }

    @Override
    public Movie insertMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie findMovieById(Integer id) throws MovieNotFoundException {
        Movie movie = movieRepository.findById(id);
        if (movie == null) throw new MovieNotFoundException(id);
        return movie;
    }

    @Override
    public void addComment(Movie movie, String text, User user) throws MovieNotFoundException {
        Comment comment = new Comment(user.getEmail(), text, timeService.getCurrentTimestamp());
        comment.setUserEmail(user.getEmail());
        comment.setText(text);
        comment = commentService.insertComment(comment);
        movie.addComment(new MovieComment(movie.getId(), comment.getId()));
        updateMovie(movie);
    }

    @Override
    public Stream<Movie> searchMovies(List<Integer> ids,
            List<String> names, List<String> directors, List<String> writers,
            List<String> genres, Double imdbRateGt, Double imdbRateLt,
            Integer ageLimitGt, Integer ageLimitLt) {
        return movieRepository.searchMovies(ids, names, directors, writers, genres, imdbRateGt, imdbRateLt, ageLimitGt,
                ageLimitLt);
    }

    private Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}
