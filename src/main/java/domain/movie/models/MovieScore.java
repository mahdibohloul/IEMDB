package domain.movie.models;

import infrastructure.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = {"movieId", "score", "userEmail"}, callSuper = true)
public class MovieScore extends BaseEntity {
    public Integer movieId;
    public Integer score;
    public String userEmail;
}
