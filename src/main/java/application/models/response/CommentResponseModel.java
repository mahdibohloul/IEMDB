package application.models.response;

import domain.comment.models.Comment;
import domain.comment.models.CommentVote;
import domain.comment.valueobjects.VoteType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseModel {
    private Integer commentId;
    private String userEmail;
    private String text;
    private Long like;
    private Long dislike;

    public CommentResponseModel(Comment comment, List<CommentVote> commentVotes) {
        this.commentId = comment.getId();
        this.userEmail = comment.getUserEmail();
        this.text = comment.getText();
        this.like = commentVotes.stream().filter(commentVote -> commentVote.getType() == VoteType.LIKE).count();
        this.dislike = commentVotes.stream().filter(commentVote -> commentVote.getType() == VoteType.DISLIKE).count();
    }
}
