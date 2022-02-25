package application.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetMovieByIdRequestModel {
    private Integer movieId;

    @JsonCreator
    public GetMovieByIdRequestModel(
            @JsonProperty(value = "movieId", required = true) Integer movieId
    ) {
        this.movieId = movieId;
    }

}
