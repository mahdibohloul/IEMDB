package domain.movie.models;

import infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRate extends BaseEntity {
    public Integer movieId;
    public Double avgRate;
}
