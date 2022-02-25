package application.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCommentRequestModel {
    private String userEmail;
    private Integer movieId;
    private String text;

    @JsonCreator
    public AddCommentRequestModel(
            @JsonProperty(required = true, value = "userEmail") String userEmail,
            @JsonProperty(required = true, value = "movieId") Integer movieId,
            @JsonProperty(required = true, value = "text") String text
    ) {
        this.userEmail = userEmail;
        this.movieId = movieId;
        this.text = text;
    }

}
