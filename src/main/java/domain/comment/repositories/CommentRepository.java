package domain.comment.repositories;

import domain.comment.models.Comment;
import infrastructure.store.InMemoryRepository;

public interface CommentRepository extends InMemoryRepository<Comment, Integer> {
}
