package domain.comment.repositories;

import domain.comment.models.CommentVote;
import infrastructure.store.InMemoryStore;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public class CommentVoteRepositoryImpl implements CommentVoteRepository {
    private final InMemoryStore<CommentVote, Integer> store = new InMemoryStore<>();

    @Override
    public CommentVote insert(CommentVote entity) {
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public CommentVote update(CommentVote entity) {
        return insert(entity);
    }

    @Override
    public CommentVote findById(Integer integer) {
        return store.get(integer);
    }

    @Override
    public void delete(CommentVote entity) {
        store.remove(entity.getId());
    }

    @Override
    public Integer generateId() {
        return store.getLastId(Integer::compare) + 1;
    }

    @Override
    public Stream<CommentVote> findAllByCommentId(Integer commentId) {
        return store.getAll().stream().filter(commentVote -> commentVote.getCommentId().equals(commentId));
    }

    @Override
    public CommentVote findByCommentIdAndUserEmail(Integer commentId, String userEmail) {
        return store.getAll().stream().filter(commentVote -> commentVote.getCommentId().equals(commentId) &&
                                                             commentVote.getUserEmail().equals(userEmail)).findFirst()
                .orElse(null);
    }
}
