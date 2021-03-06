package application.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddUserWatchListRequestModel {
    private String userEmail;
    private Integer movieId;

    @JsonCreator
    public AddUserWatchListRequestModel(
            @JsonProperty(required = true, value = "userEmail") String userEmail,
            @JsonProperty(required = true, value = "movieId") Integer movieId) {
        this.userEmail = userEmail;
        this.movieId = movieId;
    }

}
