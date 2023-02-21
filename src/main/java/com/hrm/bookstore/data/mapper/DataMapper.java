package com.hrm.bookstore.data.mapper;

import com.hrm.bookstore.data.dto.BookDto;
import com.hrm.bookstore.data.dto.OrderDto;
import com.hrm.bookstore.data.dto.OrderInfoDto;
import com.hrm.bookstore.data.dto.UserDto;
import com.hrm.bookstore.data.entity.Book;
import com.hrm.bookstore.data.entity.Order;
import com.hrm.bookstore.data.entity.OrderInfo;
import com.hrm.bookstore.data.entity.User;

public interface DataMapper {
    BookDto toDto(Book entity);

    UserDto toDto(User entity);

    OrderDto toDto(Order entity);

    OrderInfoDto toDto(OrderInfo entity);

    Book toEntity(BookDto dto);

    User toEntity(UserDto dto);

    Order toEntity(OrderDto dto);

    OrderInfo toEntity(OrderInfoDto dto);

}
