package application.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserWatchListRequestModel {
    private String userEmail;

    @JsonCreator
    public GetUserWatchListRequestModel(
            @JsonProperty(value = "userEmail", required = true) String userEmail) {
        this.userEmail = userEmail;
    }

}
