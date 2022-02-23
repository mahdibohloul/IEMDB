package application.models.response;

import domain.movie.models.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieResponseModel {
    private Integer movieId;
    private String name;
    private String director;
    private List<String> genres;
    private Double rating;

    public MovieResponseModel(Movie movie, Double rating) {
        this.movieId = movie.getId();
        this.name = movie.getName();
        this.director = movie.getDirector();
        this.genres = movie.getGenres();
        this.rating = rating;
    }
}
