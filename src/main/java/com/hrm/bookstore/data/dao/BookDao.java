package com.hrm.bookstore.data.dao;

import com.hrm.bookstore.data.dto.BookDto;

import java.util.List;

public interface BookDao extends CrudDao<Long, BookDto> {
    List<BookDto> findAll(int limit, long offset);

    long count();

}
