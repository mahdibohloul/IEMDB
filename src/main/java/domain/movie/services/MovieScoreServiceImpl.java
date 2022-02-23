package domain.movie.services;

import java.util.stream.Stream;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import domain.movie.exceptions.InvalidRateScoreException;
import domain.movie.models.Movie;
import domain.movie.models.MovieScore;
import domain.movie.pubsub.events.MovieScoredEvent;
import domain.movie.repositories.MovieScoreRepository;
import domain.user.models.User;

@Service
public class MovieScoreServiceImpl implements MovieScoreService {
    private final MovieScoreRepository movieScoreRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    private static final Integer SCORE_UPPER_LIMIT = 10;
    private static final Integer SCORE_LOWER_LIMIT = 1;

    public MovieScoreServiceImpl(MovieScoreRepository movieScoreRepository,
            ApplicationEventPublisher applicationEventPublisher) {
        this.movieScoreRepository = movieScoreRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void scoreMovie(Movie movie, User user, Integer score)
            throws InvalidRateScoreException {
        validateScore(score);
        if (alreadyScored(movie, user)) {
            MovieScore movieScore = getMovieScore(movie.getId(), user.getEmail());
            movieScore.setScore(score);
            saveMoveScore(movieScore);
        } else {
            MovieScore movieScore = new MovieScore();
            movieScore.setMovieId(movie.getId());
            movieScore.setUserEmail(user.getEmail());
            movieScore.setScore(score);
            saveMoveScore(movieScore);
        }
        applicationEventPublisher.publishEvent(new MovieScoredEvent(movie.getId()));
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

    private boolean alreadyScored(Movie movie, User user) {
        return movieScoreRepository.findByUserEmailAndMovieId(user.getEmail(), movie.getId()) != null;
    }

}
