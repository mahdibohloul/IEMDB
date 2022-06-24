package application.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ActorMovieModel {
    private Integer id;
    private String name;
    private Double imdbRate;
    private Double rating;
}
