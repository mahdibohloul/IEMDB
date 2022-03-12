package domain.comment.services;

import domain.comment.exceptions.InvalidVoteValueException;
import domain.comment.models.Comment;
import domain.comment.models.CommentVote;
import domain.comment.repositories.CommentVoteRepository;
import domain.comment.valueobjects.VoteType;
import domain.user.models.User;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class CommentVoteServiceImpl implements CommentVoteService {
    private final CommentVoteRepository commentVoteRepository;

    public CommentVoteServiceImpl(CommentVoteRepository commentVoteRepository) {
        this.commentVoteRepository = commentVoteRepository;
    }

    @Override
    public CommentVote voteComment(User user, Comment comment, int vote) throws InvalidVoteValueException {
        CommentVote existingVote = commentVoteRepository.findByCommentIdAndUserEmail(comment.getId(), user.getEmail());
        VoteType voteType = VoteType.fromInt(vote);
        if (existingVote != null) {
            if (existingVote.getType() != voteType) {
                existingVote.setType(voteType);
                return commentVoteRepository.save(existingVote);
            }
            return existingVote;
        } else {
            CommentVote commentVote = new CommentVote();
            commentVote.setCommentId(comment.getId());
            commentVote.setUserEmail(user.getEmail());
            commentVote.setType(voteType);
            return commentVoteRepository.save(commentVote);
        }
    }

    @Override
    public Stream<CommentVote> findAllByCommentId(Integer commentId) {
        return commentVoteRepository.findAllByCommentId(commentId);
    }
}
