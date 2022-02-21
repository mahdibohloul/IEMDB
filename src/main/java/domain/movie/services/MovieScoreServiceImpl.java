package domain.movie.services;

import domain.movie.exceptions.InvalidRateScoreException;
import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.MovieScore;
import domain.movie.pubsub.events.MovieScoredEvent;
import domain.movie.repositories.MovieScoreRepository;
import domain.user.exceptions.UserNotFoundException;
import domain.user.services.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class MovieScoreServiceImpl implements MovieScoreService {
    private final MovieScoreRepository movieScoreRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserService userService;
    private final MovieService movieService;
    private static Integer SCORE_UPPER_LIMIT = 10;
    private static Integer SCORE_LOWER_LIMIT = 1;

    public MovieScoreServiceImpl(MovieScoreRepository movieScoreRepository,
                                 ApplicationEventPublisher applicationEventPublisher,
                                 UserService userService,
                                 MovieService movieService) {
        this.movieScoreRepository = movieScoreRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userService = userService;
        this.movieService = movieService;
    }

    @Override
    public void scoreMovie(Integer movieId, String userEmail, Integer score) throws InvalidRateScoreException, MovieNotFoundException, UserNotFoundException {
        validateScore(score);
        validateMovie(movieId);
        validateUser(userEmail);
        if (alreadyScored(movieId, userEmail)) {
            MovieScore movieScore = getMovieScore(movieId, userEmail);
            movieScore.setScore(score);
            saveMoveScore(movieScore);
        } else {
            MovieScore movieScore = new MovieScore();
            movieScore.setMovieId(movieId);
            movieScore.setUserEmail(userEmail);
            movieScore.setScore(score);
            saveMoveScore(movieScore);
        }
        applicationEventPublisher.publishEvent(new MovieScoredEvent(movieId));
    }


    @Override
    public MovieScore getMovieScore(Integer movieId, String userEmail) {
        return movieScoreRepository.findByUserEmailAndMovieId(userEmail, movieId);
    }

    @Override
    public Stream<MovieScore> findAllByMovieId(Integer movieId) {
        return movieScoreRepository.findAllByMovieId(movieId);
    }

    private MovieScore saveMoveScore(MovieScore movieScore) {
        return movieScoreRepository.save(movieScore);
    }

    private void validateScore(Integer score) throws InvalidRateScoreException {
        if (score > SCORE_UPPER_LIMIT || score < SCORE_LOWER_LIMIT) {
            throw new InvalidRateScoreException();
        }
    }

    private void validateMovie(Integer movieId) throws MovieNotFoundException {
        if (movieId == null) {
            throw new IllegalArgumentException("Movie id cannot be null");
        }
        movieService.findMovieById(movieId);
    }

    private void validateUser(String userEmail) throws UserNotFoundException {
        if (userEmail == null) {
            throw new IllegalArgumentException("User email cannot be null");
        }
        userService.findUserByEmail(userEmail);
    }

    private boolean alreadyScored(Integer movieId, String userEmail) {
        return movieScoreRepository.findByUserEmailAndMovieId(userEmail, movieId) != null;
    }

}
