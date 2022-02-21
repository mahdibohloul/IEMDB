package domain.comment.repositories;

import domain.comment.models.CommentVote;
import infrastructure.store.InMemoryStore;
import org.springframework.stereotype.Repository;

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
}
