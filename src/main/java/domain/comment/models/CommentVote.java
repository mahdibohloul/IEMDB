package domain.comment.models;

import domain.comment.valueobjects.VoteType;
import infrastructure.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentVote extends BaseEntity {
    private String userEmail;
    private Integer commentId;
    private VoteType type;
}
