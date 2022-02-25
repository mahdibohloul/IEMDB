package domain.user.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.user.exceptions.AgeLimitErrorException;
import domain.user.exceptions.MovieAlreadyExistsException;
import domain.user.models.User;
import domain.user.models.UserWatchList;
import domain.user.repositories.UserWatchListRepository;

@Service
public class UserWatchListServiceImpl implements UserWatchListService {
    private final UserWatchListRepository userWatchListRepository;

    public UserWatchListServiceImpl(UserWatchListRepository userWatchListRepository) {
        this.userWatchListRepository = userWatchListRepository;
    }

    @Override
    public UserWatchList addToWatchList(User user, Movie movie)
            throws MovieAlreadyExistsException, AgeLimitErrorException {
        UserWatchList userWatchList = userWatchListRepository.findByUserEmailAndMovieId(user.getEmail(), movie.getId());
        if (userWatchList != null)
            throw new MovieAlreadyExistsException(user.getEmail(), movie.getId());
        validateMovieAgeLimit(user, movie);
        userWatchList = new UserWatchList();
        userWatchList.setUserEmail(user.getEmail());
        userWatchList.setMovieId(movie.getId());
        return userWatchListRepository.save(userWatchList);
    }

    @Override
    public void removeFromWatchList(User user, Integer movieId) throws MovieNotFoundException {
        UserWatchList userWatchList = userWatchListRepository.findByUserEmailAndMovieId(user.getEmail(), movieId);
        if (userWatchList == null)
            throw new MovieNotFoundException(movieId);

        userWatchListRepository.delete(userWatchList);
    }

    @Override
    public Stream<UserWatchList> getUserWatchList(User user) {
        return userWatchListRepository.findAllByUserEmail(user.getEmail());
    }

    public void validateMovieAgeLimit(User user, Movie movie) throws AgeLimitErrorException {
        LocalDate userBirthDate = user.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate curDate = LocalDate.now();
        Integer year = Period.between(userBirthDate, curDate).getYears();
        if (year < movie.getAgeLimit())
            throw new AgeLimitErrorException(year, movie.getAgeLimit());
    }
}
