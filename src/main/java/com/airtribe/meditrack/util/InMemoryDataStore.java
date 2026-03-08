package com.airtribe.meditrack.util;

import java.lang.reflect.Method;
import java.util.*;

public class InMemoryDataStore<T> implements DataStore<T> {
    private final Map<String, T> map = new HashMap<>();
    private final Method getIdMethod;

    public InMemoryDataStore(Class<T> type) {
        try { this.getIdMethod = type.getMethod("getId"); }
        catch (Exception e) { throw new IllegalArgumentException("Type must have getId(): " + type, e); }
    }

    @Override public void save(T t) {
        try {
            String id = (String) getIdMethod.invoke(t);
            map.put(id, t);
        } catch (Exception e) { throw new RuntimeException("Failed to save", e); }
    }
    @Override public Optional<T> findById(String id) { return Optional.ofNullable(map.get(id)); }
    @Override public List<T> findAll() { return new ArrayList<>(map.values()); }
    @Override public void deleteById(String id) { map.remove(id); }
}