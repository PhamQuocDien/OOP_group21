
package dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class InMemoryDAO<T> {
    private final Map<String, T> data = new LinkedHashMap<>();
    private final Function<T, String> idExtractor;

    public InMemoryDAO(Function<T, String> idExtractor) {
        this.idExtractor = Objects.requireNonNull(idExtractor);
    }

    public List<T> getAll() {
        return new ArrayList<>(data.values());
    }

    public T findById(String id) {
        return data.get(id);
    }

    public void add(T item) {
        Objects.requireNonNull(item, "Du lieu khong duoc null");
        String id = idExtractor.apply(item);

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Ma dinh danh khong hop le");
        }
        if (data.containsKey(id)) {
            throw new IllegalArgumentException("Ma dinh danh da ton tai: " + id);
        }

        data.put(id, item);
    }

    public boolean removeById(String id) {
        return data.remove(id) != null;
    }
}
