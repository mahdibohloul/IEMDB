package domain.comment.repositories;

import java.util.stream.Stream;

import domain.comment.models.CommentVote;
import infrastructure.store.InMemoryRepository;

public interface CommentVoteRepository extends InMemoryRepository<CommentVote, Integer> {
    Stream<CommentVote> findAllByCommentId(Integer commentId);
    CommentVote findByCommentIdAndUserEmail(Integer commentId, String userEmail);
}
