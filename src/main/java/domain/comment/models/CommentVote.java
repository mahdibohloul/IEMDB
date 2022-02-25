package domain.comment.models;

import domain.comment.valueobjects.VoteType;
import infrastructure.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = {"userEmail", "commentId", "type"}, callSuper = true)
public class CommentVote extends BaseEntity {
    private String userEmail;
    private Integer commentId;
    private VoteType type;
}
