package domain.comment.repositories;

import domain.comment.models.CommentVote;
import infrastructure.store.InMemoryRepository;

public interface CommentVoteRepository extends InMemoryRepository<CommentVote, Integer> {
}
