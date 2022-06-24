package infrastructure.dataprovider.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MovieModel {
    private Integer id;
    private String name;
    private String summary;
    private Date releaseDate;
    private String director;
    private List<String> writers;
    private List<String> genres;
    private List<Integer> cast;
    private Double imdbRate;
    private Integer duration;
    private Integer ageLimit;

    @SneakyThrows
    @JsonCreator
    public MovieModel(
            @JsonProperty(value = "id", required = true) Integer id,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "summary", required = true) String summary,
            @JsonProperty(value = "releaseDate", required = true) String releaseDate,
            @JsonProperty(value = "director", required = true) String director,
            @JsonProperty(value = "writers", required = true) List<String> writers,
            @JsonProperty(value = "genres", required = true) List<String> genres,
            @JsonProperty(value = "cast", required = true) List<Integer> cast,
            @JsonProperty(value = "imdbRate", required = true) Double imdbRate,
            @JsonProperty(value = "duration", required = true) Integer duration,
            @JsonProperty(value = "ageLimit", required = true) Integer ageLimit
    ) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
        this.director = director;
        this.writers = writers;
        this.genres = genres;
        this.cast = cast;
        this.imdbRate = imdbRate;
        this.duration = duration;
        this.ageLimit = ageLimit;
    }
}
