package com.airtribe.meditrack.util;

import java.util.List;
import java.util.Optional;

public interface DataStore<T> {
    void save(T t);
    Optional<T> findById(String id);
    List<T> findAll();
    void deleteById(String id);
}