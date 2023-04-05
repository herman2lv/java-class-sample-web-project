package com.hrm.bookstore.data.mapper;

import com.hrm.bookstore.data.dto.BookDto;
import com.hrm.bookstore.data.dto.UserDto;
import com.hrm.bookstore.data.entity.Book;
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

}
