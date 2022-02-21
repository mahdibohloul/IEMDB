package infrastructure.store;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class InMemoryStore<T, ID> {
    Map<ID, T> store;

    public InMemoryStore() {
        store = new HashMap<>();
    }

    public void put(ID id, T value) {
        store.put(id, value);
    }

    public T get(ID id) {
        return store.get(id);
    }

    public void remove(ID id) {
        store.remove(id);
    }

    public Collection<T> getAll() {
        return store.values();
    }

    public ID getLastId(Comparator<ID> comparator) {
        return store.keySet().stream().max(comparator).orElse((ID) (Integer.valueOf(store.size() + 1)));
    }
}
