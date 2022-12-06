package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base stored = memory.computeIfPresent(model.getId(), (k, v) -> {
                    if (v.getVersion() != model.getVersion()) {
                        throw new OptimisticException("Versions are not equal");
                    }
                    return new Base(k, v.getVersion() + 1);
                }
        );
        return stored != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Base get(int id) {
        return memory.get(id);
    }
}