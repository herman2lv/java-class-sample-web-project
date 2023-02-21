package com.hrm.bookstore.data.dao;

import java.util.List;

public interface CrudDao<K, T> {
    T findById(K id);

    List<T> findAll();

    T save(T dto);

    T update(T dto);

    boolean delete(K id);
}
