package infrastructure.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InMemoryStoreTest {
    private InMemoryStore store;

    @Test
    @DisplayName("should return null when key is not found")
    void should_store_and_retrieve_a_value() {
        store = new InMemoryStore<String, String>();
        store.put("key", "value");
        assert store.get("key").equals("value");
    }

    @Test
    @DisplayName("should return null when key is not found")
    void should_return_null_when_key_is_not_found() {
        store = new InMemoryStore<String, String>();
        assert store.get("key") == null;
    }

    @Test
    @DisplayName("should do nothing when key is not found for removing")
    void should_do_nothing_when_key_is_not_found_for_removing() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "key");
        store = new InMemoryStore<>(map);
        store.remove(2);
        assert store.get(1) != null;
        assert store.get(1).equals("key");
    }

    @Test
    @DisplayName("should get all stored values")
    void should_get_all_stored_values() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "key");
        map.put(2, "key");
        store = new InMemoryStore<>(map);
        assert store.getAll().size() == 2;
    }

    @Test
    @DisplayName("should get last id in store")
    void should_get_last_id_in_store() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "key");
        map.put(2, "key");
        InMemoryStore<String, Integer> sec_store = new InMemoryStore<>(map);
        assert Objects.equals(sec_store.getLastId(Integer::compare), 2);
    }
}
