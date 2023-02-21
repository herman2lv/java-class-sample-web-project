package com.hrm.bookstore.data.mapper;

import com.hrm.bookstore.data.dto.BookDto;
import com.hrm.bookstore.data.dto.OrderDto;
import com.hrm.bookstore.data.dto.OrderInfoDto;
import com.hrm.bookstore.data.dto.UserDto;
import com.hrm.bookstore.data.entity.Book;
import com.hrm.bookstore.data.entity.Order;
import com.hrm.bookstore.data.entity.OrderInfo;
import com.hrm.bookstore.data.entity.User;

public class DataMapperImpl implements DataMapper {
    @Override
    public BookDto toDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    @Override
    public UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        User.Role role = entity.getRole();
        if (role != null) {
            dto.setRole(UserDto.Role.valueOf(role.toString()));
        }
        return dto;
    }

    @Override
    public OrderDto toDto(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        User user = entity.getUser();
        if (user != null) {
            dto.setUserId(user.getId());
        }
        dto.setTotalCost(entity.getTotalCost());
        Order.Status status = entity.getStatus();
        if (status != null) {
            dto.setStatus(OrderDto.Status.valueOf(status.toString()));
        }
        return dto;
    }

    @Override
    public OrderInfoDto toDto(OrderInfo entity) {
        OrderInfoDto dto = new OrderInfoDto();
        dto.setId(entity.getId());
        Book book = entity.getBook();
        if (book != null) {
            dto.setBookId(book.getId());
        }
        dto.setBookPrice(entity.getBookPrice());
        dto.setBookQuantity(entity.getBookQuantity());
        dto.setOrderId(entity.getOrderId());
        return dto;
    }

    @Override
    public Book toEntity(BookDto dto) {
        Book entity = new Book();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    @Override
    public User toEntity(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        UserDto.Role role = dto.getRole();
        if (role != null) {
            entity.setRole(User.Role.valueOf(role.toString()));
        }
        return entity;
    }

    @Override
    public Order toEntity(OrderDto dto) {//without user & details
        Order entity = new Order();
        entity.setId(dto.getUserId());
        entity.setTotalCost(dto.getTotalCost());
        OrderDto.Status status = dto.getStatus();
        if (status != null) {
            entity.setStatus(Order.Status.valueOf(status.toString()));
        }
        return entity;
    }

    @Override
    public OrderInfo toEntity(OrderInfoDto dto) {//without book
        OrderInfo entity = new OrderInfo();
        entity.setId(dto.getId());
        entity.setBookPrice(dto.getBookPrice());
        entity.setBookQuantity(dto.getBookQuantity());
        entity.setOrderId(dto.getOrderId());
        return entity;
    }
}
