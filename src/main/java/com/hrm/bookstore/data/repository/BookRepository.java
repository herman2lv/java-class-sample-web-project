package com.hrm.bookstore.data.repository;

import com.hrm.bookstore.data.entity.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Long, Book> {
    List<Book> findAll(int limit, long offset);

    long count();

}
