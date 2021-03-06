package application.models.response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import domain.actor.models.Actor;
import domain.movie.models.Movie;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResponseModel {
    private Integer movieId;
    private String name;
    private String director;
    private List<String> genres;
    private Double rating;
    @JsonIgnore
    private String summary;
    @JsonIgnore
    private List<String> cast;
    @JsonIgnore
    private Date releaseDate;
    @JsonIgnore
    private String releaseDateString;
    @JsonIgnore
    private List<String> writers;
    @JsonIgnore
    private Double imdbRate;
    @JsonIgnore
    private Integer duration;
    @JsonIgnore
    private Integer ageLimit;

    public MovieResponseModel(Movie movie, Double rating, List<Actor> cast) {
        this.movieId = movie.getId();
        this.name = movie.getName();
        this.summary = movie.getSummary();
        this.director = movie.getDirector();
        this.genres = movie.getGenres();
        this.rating = Objects.requireNonNullElse(rating, 0.0);
        this.releaseDate = movie.getReleaseDate();
        this.releaseDateString = new SimpleDateFormat("yyyy-MM-dd").format(this.releaseDate);
        this.cast = cast.stream().map(Actor::getName).toList();
        this.writers = movie.getWriters();
        this.imdbRate = movie.getImdbRate();
        this.duration = movie.getDuration();
        this.ageLimit = movie.getAgeLimit();
    }
}
