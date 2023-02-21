package com.hrm.bookstore.data.dao;

import com.hrm.bookstore.data.dto.UserDto;

public interface UserDao extends CrudDao<Long, UserDto> {
    UserDto findByEmail(String email);
}
