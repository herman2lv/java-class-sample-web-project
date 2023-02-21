package com.hrm.bookstore.service;

import com.hrm.bookstore.data.entity.User;

import java.util.List;

public interface UserService {
    User getById(Long id);

    List<User> getAll();

    User create(User entity);

    User update(User entity);

    void delete(Long id);

    User login(String email, String password);

}
