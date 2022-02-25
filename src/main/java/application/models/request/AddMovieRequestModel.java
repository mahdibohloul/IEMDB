package application.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import domain.movie.models.Movie;
import infrastructure.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddMovieRequestModel extends BaseModel {
    private String name;
    private String summary;
    private String releaseDate;
    private String director;
    private List<String> writers;
    private List<String> genres;
    private List<Integer> cast;
    private Double imdbRate;
    private String duration;
    private Integer ageLimit;

    @JsonCreator
    public AddMovieRequestModel(@JsonProperty(value = "id", required = true) Integer id,
            @JsonProperty(required = true, value = "name") String name,
            @JsonProperty(required = true, value = "summary") String summary,
            @JsonProperty(required = true, value = "releaseDate") String releaseDate,
            @JsonProperty(required = true, value = "director") String director,
            @JsonProperty(required = true, value = "writers") List<String> writers,
            @JsonProperty(required = true, value = "genres") List<String> genres,
            @JsonProperty(required = true, value = "cast") List<Integer> cast,
            @JsonProperty(required = true, value = "imdbRate") Double imdbRate,
            @JsonProperty(required = true, value = "duration") String duration,
            @JsonProperty(required = true, value = "ageLimit") Integer ageLimit) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.director = director;
        this.writers = writers;
        this.genres = genres;
        this.cast = cast;
        this.imdbRate = imdbRate;
        this.duration = duration;
        this.ageLimit = ageLimit;
    }

    public Movie toMovie() throws ParseException {
        Movie movie = new Movie();
        movie.setId(this.id);
        movie.setName(this.name);
        movie.setAgeLimit(this.ageLimit);
        movie.setCast(this.cast);
        movie.setDuration(Integer.valueOf(this.duration));
        movie.setGenres(this.genres);
        movie.setImdbRate(this.imdbRate);
        Date releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(this.releaseDate);
        movie.setReleaseDate(releaseDate);
        movie.setSummary(this.summary);
        movie.setWriters(this.writers);
        movie.setDirector(this.director);
        return movie;
    }
}
