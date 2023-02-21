package com.hrm.bookstore.data.repository.impl;

import com.hrm.bookstore.data.dao.BookDao;
import com.hrm.bookstore.data.dto.BookDto;
import com.hrm.bookstore.data.entity.Book;
import com.hrm.bookstore.data.mapper.DataMapper;
import com.hrm.bookstore.data.repository.BookRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final BookDao bookDao;
    private final DataMapper dataMapper;

    @Override
    public Book findById(Long id) {
        return dataMapper.toEntity(bookDao.findById(id));
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll()
                .stream()
                .map(dataMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll(int limit, long offset) {
        return bookDao.findAll(limit, offset)
                .stream()
                .map(dataMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Book save(Book entity) {
        BookDto dto = dataMapper.toDto(entity);
        BookDto saved = bookDao.save(dto);
        return dataMapper.toEntity(saved);
    }

    @Override
    public Book update(Book entity) {
        BookDto dto = dataMapper.toDto(entity);
        BookDto updated = bookDao.update(dto);
        return dataMapper.toEntity(updated);
    }

    @Override
    public boolean delete(Long id) {
        return bookDao.delete(id);
    }

    @Override
    public long count() {
        return bookDao.count();
    }
}
