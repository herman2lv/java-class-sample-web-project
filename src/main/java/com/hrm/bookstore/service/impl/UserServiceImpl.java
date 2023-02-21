package com.hrm.bookstore.service.impl;

import com.hrm.bookstore.data.entity.User;
import com.hrm.bookstore.data.repository.UserRepository;
import com.hrm.bookstore.service.DigestService;
import com.hrm.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DigestService digestService;

    @Override
    public User getById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new RuntimeException("No user with id=" + id);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User entity) {
        validate(entity);
        String hashedPassword = digestService.hash(entity.getPassword());
        entity.setPassword(hashedPassword);
        return userRepository.save(entity);
    }

    private static void validate(User entity) {
        if (entity.getPassword().length() < 4) {
            throw new RuntimeException("Password is too short");
        }
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("No user with email: " + email);
        }
        String hashedPassword = digestService.hash(password);
        if (!user.getPassword().equals(hashedPassword)) {
            throw new RuntimeException("Wrong password for user: " + email);
        }
        return user;
    }

    @Override
    public User update(User entity) {
        return userRepository.update(entity);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.delete(id)) {
            throw new RuntimeException("Couldn't delete user (id=" + id + ")");
        }
    }

}
