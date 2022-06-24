package domain.user.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import domain.movie.exceptions.MovieNotFoundException;
import domain.movie.models.Movie;
import domain.movie.services.MovieRateService;
import domain.movie.services.MovieService;
import domain.movie.valueobjects.MovieSearchModel;
import domain.user.exceptions.AgeLimitErrorException;
import domain.user.exceptions.MovieAlreadyExistsException;
import domain.user.models.User;
import domain.user.models.UserWatchList;
import domain.user.repositories.UserWatchListRepository;

@Service
public class UserWatchListServiceImpl implements UserWatchListService {
    private final UserWatchListRepository userWatchListRepository;
    private final MovieService movieService;
    private final MovieRateService movieRateService;

    public UserWatchListServiceImpl(UserWatchListRepository userWatchListRepository,
            MovieService movieService, MovieRateService movieRateService) {
        this.userWatchListRepository = userWatchListRepository;
        this.movieService = movieService;
        this.movieRateService = movieRateService;
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

    @Override
    public Stream<Movie> getRecommendedMovie(User user, Integer count) {
        List<Integer> userWatchListMovieId = getUserWatchList(user).map(UserWatchList::getMovieId).toList();
        List<Movie> movies = movieService.searchMovies(new MovieSearchModel()).toList();
        List<Movie> userWatchListMovie =
                movies.stream().filter(movie -> userWatchListMovieId.contains(movie.getId())).toList();
        List<Movie> notUserWatchListMovie =
                movies.stream().filter(movie -> !userWatchListMovieId.contains(movie.getId())).toList();
        return notUserWatchListMovie.stream()
                .sorted(Comparator.comparing(movie -> calculateMovieScore(userWatchListMovie, (Movie) movie))
                        .reversed()).limit(count);
    }

    public void validateMovieAgeLimit(User user, Movie movie) throws AgeLimitErrorException {
        LocalDate userBirthDate = user.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate curDate = LocalDate.now();
        Integer year = Period.between(userBirthDate, curDate).getYears();
        if (year < movie.getAgeLimit())
            throw new AgeLimitErrorException(year, movie.getAgeLimit());
    }

    private Double calculateMovieScore(List<Movie> watchListMovies, Movie movie) {
        double genreSimilarity = 0.0;
        for (Movie watchListMovie : watchListMovies) {
            genreSimilarity += watchListMovie.getGenres().stream().filter(movie.getGenres()::contains).count();
        }
        return 3 * genreSimilarity + movie.getImdbRate() +
               Objects.requireNonNullElse(movieRateService.getMovieRate(movie), 0.0);
    }
}
