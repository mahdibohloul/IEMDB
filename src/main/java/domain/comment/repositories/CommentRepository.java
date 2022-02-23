package domain.comment.repositories;

import domain.comment.models.Comment;
import infrastructure.store.InMemoryRepository;

import java.util.List;
import java.util.stream.Stream;

public interface CommentRepository extends InMemoryRepository<Comment, Integer> {
    Stream<Comment> searchComments(List<Integer> ids);
}
