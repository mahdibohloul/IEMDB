package application.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RateMovieRequestModel {
    private String userEmail;
    private Integer movieId;
    private Integer score;

    @JsonCreator
    public RateMovieRequestModel(
            @JsonProperty(required = true, value = "userEmail") String userEmail,
            @JsonProperty(required = true, value = "movieId") Integer movieId,
            @JsonProperty(required = true, value = "score") Integer score
    ) {
        this.userEmail = userEmail;
        this.movieId = movieId;
        this.score = score;
    }

}
