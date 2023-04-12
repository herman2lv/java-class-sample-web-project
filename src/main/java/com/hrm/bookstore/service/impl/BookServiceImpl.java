package com.hrm.bookstore.service.impl;

import com.hrm.bookstore.data.entity.Book;
import com.hrm.bookstore.data.repository.BookRepository;
import com.hrm.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public List<Book> getAll(int limit, long offset) {
        return bookRepository.findAll(limit, offset);
    }

    @Override
    public Book getById(Long id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new RuntimeException("No book with id: " + id);
        }
        return book;
    }

    @Override
    public Book create(Book entity) {
        return bookRepository.save(entity);
    }

    @Override
    public Book update(Book entity) {
        return bookRepository.update(entity);
    }

    @Override
    public void delete(Long id) {
        boolean success = bookRepository.delete(id);
        if (!success) {
            throw new RuntimeException("Couldn't delete book (id=" + id + ")");
        }
    }

}
