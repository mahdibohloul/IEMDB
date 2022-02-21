package domain.movie.models;

import infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieScore extends BaseEntity {
    public Integer movieId;
    public Integer score;
    public String userEmail;
}
