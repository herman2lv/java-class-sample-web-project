package com.hrm.bookstore.data.mapper;

import com.hrm.bookstore.data.dto.BookDto;
import com.hrm.bookstore.data.dto.UserDto;
import com.hrm.bookstore.data.entity.Book;
import com.hrm.bookstore.data.entity.User;

public interface DataMapper {
    BookDto toDto(Book entity);

    UserDto toDto(User entity);

    Book toEntity(BookDto dto);

    User toEntity(UserDto dto);

}
