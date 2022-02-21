package domain.comment.services;

import domain.comment.exceptions.InvalidVoteValueException;
import domain.comment.models.Comment;
import domain.comment.models.CommentVote;
import domain.user.models.User;

public interface CommentVoteService {
    CommentVote voteComment(User user, Comment comment, int vote) throws InvalidVoteValueException;
}
