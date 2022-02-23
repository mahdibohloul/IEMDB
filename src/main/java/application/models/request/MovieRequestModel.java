package application.models.request;

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
public class MovieRequestModel extends BaseModel {
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
