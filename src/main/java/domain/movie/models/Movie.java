package domain.movie.models;

import infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Movie extends BaseEntity {
    public Movie() {
        this.comments = new java.util.ArrayList<>();
    }

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
    private List<MovieComment> comments;

    public void addComment(MovieComment comment) {
        this.comments.add(comment);
    }
}
