package application.models.response;

import java.text.SimpleDateFormat;
import java.util.List;

import domain.movie.models.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MovieDetailResponseModel {
    private Integer movieId;
    private String name;
    private String summary;
    private String releaseDate;
    private String director;
    private List<String> writers;
    private List<String> genres;
    private List<ActorResponseModel> cast;
    private Double rating;
    private Integer duration;
    private Integer ageLimit;
    private Double imdbRate;
    private List<CommentResponseModel> comments;

    public MovieDetailResponseModel(Movie movie, List<CommentResponseModel> comments, List<ActorResponseModel> cast,
            Double rating) {
        this.movieId = movie.getId();
        this.name = movie.getName();
        this.summary = movie.getSummary();
        this.releaseDate = new SimpleDateFormat("yyyy-MM-dd").format(movie.getReleaseDate());
        this.director = movie.getDirector();
        this.writers = movie.getWriters();
        this.genres = movie.getGenres();
        this.cast = cast;
        this.rating = rating;
        this.duration = movie.getDuration();
        this.ageLimit = movie.getAgeLimit();
        this.comments = comments;
        this.imdbRate = movie.getImdbRate();
    }
}
