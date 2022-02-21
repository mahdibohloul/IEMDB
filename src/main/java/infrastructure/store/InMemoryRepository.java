package infrastructure.store;

import infrastructure.BaseEntity;

public interface InMemoryRepository<T extends BaseEntity, ID> {
    default T save(T entity) {
        if (entity.getId() != null && findById((ID) entity.getId()) != null) {
            update(entity);
            return entity;
        }
        if (entity.getId() == null) {
            entity.setId((Integer) generateId());
        }
        return insert(entity);
    }

    T insert(T entity);

    T update(T entity);

    T findById(ID id);

    void delete(T entity);

    ID generateId();
}
