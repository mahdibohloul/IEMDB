package application.models.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import domain.comment.models.Comment;
import domain.comment.models.CommentVote;
import domain.comment.valueobjects.VoteType;
import domain.user.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseModel {
    private Integer commentId;
    private String userEmail;
    private String text;
    private Long like;
    private Long dislike;
    @JsonIgnore
    private String userNickname;

    public CommentResponseModel(Comment comment, List<CommentVote> commentVotes, User user) {
        this.commentId = comment.getId();
        this.userEmail = comment.getUserEmail();
        this.text = comment.getText();
        this.like = commentVotes.stream().filter(commentVote -> commentVote.getType() == VoteType.LIKE).count();
        this.dislike = commentVotes.stream().filter(commentVote -> commentVote.getType() == VoteType.DISLIKE).count();
        this.userNickname = user.getNickname();
    }
}
