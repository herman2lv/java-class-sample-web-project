package com.hrm.bookstore.service;

import com.hrm.bookstore.data.entity.Book;

import java.util.List;

public interface BookService {

    Book getById(Long id);

    List<Book> getAll();

    Book create(Book entity);

    Book update(Book entity);

    void delete(Long id);

    List<Book> getAll(int limit, long offset);

    long count();
}
