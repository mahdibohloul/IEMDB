package domain.movie.valueobjects;

import infrastructure.SortOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieSearchModel {
    List<Integer> ids = null;
    List<String> names = null;
    List<String> directors = null;
    List<String> writers = null;
    List<String> genres = null;
    List<Integer> cast = null;
    Double imdbRateGt = null;
    Double imdbRateLt = null;
    Integer ageLimitGt = null;
    Integer ageLimitLt = null;
    Integer yearGt = null;
    Integer yearLt = null;
    MovieSortOption sortOption = MovieSortOption.IMDB;
    SortOrder sortOrder = SortOrder.DESCENDING;
}
