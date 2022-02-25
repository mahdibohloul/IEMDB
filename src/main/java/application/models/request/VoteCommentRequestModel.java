package application.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoteCommentRequestModel {
    private String userEmail;
    private Integer commentId;
    private Integer vote;

    @JsonCreator
    public VoteCommentRequestModel(
            @JsonProperty(required = true, value = "userEmail") String userEmail,
            @JsonProperty(required = true, value = "commentId") Integer commentId,
            @JsonProperty(required = true, value = "vote") Integer vote
    ) {
        this.userEmail = userEmail;
        this.commentId = commentId;
        this.vote = vote;
    }
}
