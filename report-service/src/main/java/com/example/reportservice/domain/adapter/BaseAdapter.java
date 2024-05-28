package com.example.reportservice.domain.adapter;

import java.util.Optional;

public interface BaseAdapter<T, ID> {
    Optional<T> findById(ID id);
    T save(T entity);
    void deleteById(ID id);
}
