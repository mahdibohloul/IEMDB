package application.models.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MoviesResponseModel {
    private List<MovieResponseModel> moviesList;
}
