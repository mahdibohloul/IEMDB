package domain.comment.repositories;

import domain.comment.models.CommentVote;
import infrastructure.store.InMemoryRepository;

import java.util.stream.Stream;

public interface CommentVoteRepository extends InMemoryRepository<CommentVote, Integer> {
    Stream<CommentVote> findAllByCommentId(Integer commentId);
}
