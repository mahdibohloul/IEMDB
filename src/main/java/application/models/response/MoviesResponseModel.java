package application.models.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoviesResponseModel {
    @JsonProperty(value = "MoviesList")
    private List<MovieResponseModel> moviesList;
}
