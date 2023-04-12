package com.hrm.bookstore.data.dao.impl;

import com.hrm.bookstore.data.connection.ConnectionManager;
import com.hrm.bookstore.data.dao.BookDao;
import com.hrm.bookstore.data.dto.BookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {
    private static final String FIND_BY_ID = "SELECT b.id, b.title, b.price FROM books b WHERE b.id = ?";
    private static final String FIND_ALL = "SELECT b.id, b.title, b.price FROM books b";
    private static final String FIND_ALL_PAGED = "SELECT b.id, b.title, b.price FROM books b LIMIT ? OFFSET ?";
    private static final String INSERT = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM books WHERE id = ?";
    public static final String COUNT_ALL = "SELECT COUNT(*) AS total FROM books";
    private final ConnectionManager connectionManager;

    @Override
    public BookDto findById(Long id) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return process(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<BookDto> findAll() {
        List<BookDto> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                list.add(process(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<BookDto> findAll(int limit, long offset) {
        List<BookDto> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_PAGED);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(process(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public BookDto save(BookDto dto) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            prepareForInsert(statement, dto);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                long id = keys.getLong("id");
                return findById(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Couldn't create new dto: " + dto);
    }

    @Override
    public BookDto update(BookDto dto) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            prepareForUpdate(statement, dto);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 1) {
                return findById(dto.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Couldn't update dto: " + dto);
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try (Connection connection = connectionManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_ALL);
            if (resultSet.next()) {
                return resultSet.getLong("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Couldn't count books");
    }

    private BookDto process(ResultSet resultSet) throws SQLException {
        BookDto book = new BookDto();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }

    private void prepareForInsert(PreparedStatement statement, BookDto dto) throws SQLException {
        statement.setString(1, dto.getTitle());
        statement.setBigDecimal(2, dto.getPrice());
    }

    private void prepareForUpdate(PreparedStatement statement, BookDto dto) throws SQLException {
        prepareForInsert(statement, dto);
        statement.setLong(3, dto.getId());
    }

}
