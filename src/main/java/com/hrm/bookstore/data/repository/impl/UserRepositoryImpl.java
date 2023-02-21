package com.hrm.bookstore.data.repository.impl;

import com.hrm.bookstore.data.dao.UserDao;
import com.hrm.bookstore.data.dto.UserDto;
import com.hrm.bookstore.data.entity.User;
import com.hrm.bookstore.data.mapper.DataMapper;
import com.hrm.bookstore.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;
    private final DataMapper dataMapper;

    @Override
    public User findById(Long id) {
        return dataMapper.toEntity(userDao.findById(id));
    }

    @Override
    public User findByEmail(String email) {
        return dataMapper.toEntity(userDao.findByEmail(email));
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll()
                .stream()
                .map(dataMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public User save(User entity) {
        UserDto dto = dataMapper.toDto(entity);
        UserDto saved = userDao.save(dto);
        return dataMapper.toEntity(saved);
    }

    @Override
    public User update(User entity) {
        UserDto dto = dataMapper.toDto(entity);
        UserDto updated = userDao.update(dto);
        return dataMapper.toEntity(updated);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }
}
