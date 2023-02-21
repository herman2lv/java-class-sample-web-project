package com.hrm.bookstore.data.repository;

import com.hrm.bookstore.data.entity.User;

public interface UserRepository extends CrudRepository<Long, User> {
    User findByEmail(String email);
}
