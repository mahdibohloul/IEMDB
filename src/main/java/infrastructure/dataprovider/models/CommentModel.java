package infrastructure.dataprovider.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentModel {
    private String userEmail;
    private Integer movieId;
    private String text;

    @JsonCreator
    public CommentModel(
            @JsonProperty(value = "userEmail", required = true) String userEmail,
            @JsonProperty(value = "movieId", required = true) Integer movieId,
            @JsonProperty(value = "text", required = true) String text) {
        this.userEmail = userEmail;
        this.movieId = movieId;
        this.text = text;
    }

}
