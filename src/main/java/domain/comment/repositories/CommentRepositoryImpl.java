package domain.comment.repositories;

import domain.comment.models.Comment;
import infrastructure.store.InMemoryStore;

public class CommentRepositoryImpl implements CommentRepository {
    private final InMemoryStore<Comment, Integer> store = new InMemoryStore<>();

    @Override
    public Comment insert(Comment entity) {
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Comment update(Comment entity) {
        return insert(entity);
    }

    @Override
    public Comment findById(Integer integer) {
        return store.get(integer);
    }

    @Override
    public void delete(Comment entity) {
        store.remove(entity.getId());
    }

    @Override
    public Integer generateId() {
        return store.getLastId(Integer::compare) + 1;
    }
}
