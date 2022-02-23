package domain.movie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MovieComment {
    private Integer movieId;
    private Integer commentId;
}
