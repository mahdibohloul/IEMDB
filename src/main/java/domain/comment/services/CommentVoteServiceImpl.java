package domain.comment.services;

import domain.comment.exceptions.InvalidVoteValueException;
import domain.comment.models.Comment;
import domain.comment.models.CommentVote;
import domain.comment.repositories.CommentVoteRepository;
import domain.comment.valueobjects.VoteType;
import domain.user.models.User;
import org.springframework.stereotype.Service;

@Service
public class CommentVoteServiceImpl implements CommentVoteService {
    private final CommentVoteRepository commentVoteRepository;

    public CommentVoteServiceImpl(CommentVoteRepository commentVoteRepository) {
        this.commentVoteRepository = commentVoteRepository;
    }

    @Override
    public CommentVote voteComment(User user, Comment comment, int vote) throws InvalidVoteValueException {
        CommentVote commentVote = new CommentVote();
        commentVote.setCommentId(comment.getId());
        commentVote.setUserEmail(user.getEmail());
        commentVote.setType(VoteType.fromInt(vote));
        return commentVoteRepository.save(commentVote);
    }
}