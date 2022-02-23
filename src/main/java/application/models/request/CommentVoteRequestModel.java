package application.models.request;

import infrastructure.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentVoteRequestModel extends BaseModel {
    private String userEmail;
    private Integer commentId;
    private Integer vote;
}
