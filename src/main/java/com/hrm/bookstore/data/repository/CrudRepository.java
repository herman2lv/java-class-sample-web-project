package com.hrm.bookstore.data.repository;

import java.util.List;

public interface CrudRepository<K, T> {
    T findById(K id);

    List<T> findAll();

    T save(T entity);

    T update(T entity);

    boolean delete(K id);
}
