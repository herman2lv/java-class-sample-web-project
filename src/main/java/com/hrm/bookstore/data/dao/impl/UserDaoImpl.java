package com.hrm.bookstore.data.dao.impl;

import com.hrm.bookstore.data.connection.ConnectionManager;
import com.hrm.bookstore.data.dao.UserDao;
import com.hrm.bookstore.data.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private static final String FIND_BY_ID = "SELECT u.id, u.email, u.password, u.role FROM users u WHERE u.id = ?";
    private static final String FIND_BY_EMAIL = "SELECT u.id, u.email, u.password, u.role FROM users u WHERE u.email = ?";
    private static final String FIND_ALL = "SELECT u.id, u.email, u.password, u.role FROM users u";
    private static final String INSERT = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE users SET email = ?, password = ?, role = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM users WHERE id = ?";
    private final ConnectionManager connectionManager;

    @Override
    public UserDto findById(Long id) {
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
    public UserDto findByEmail(String email) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL);
            statement.setString(1, email);
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
    public List<UserDto> findAll() {
        List<UserDto> list = new ArrayList<>();
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
    public UserDto save(UserDto dto) {
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
    public UserDto update(UserDto dto) {
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

    private UserDto process(ResultSet resultSet) throws SQLException {
        UserDto dto = new UserDto();
        dto.setId(resultSet.getLong("id"));
        dto.setEmail(resultSet.getString("email"));
        dto.setPassword(resultSet.getString("password"));
        dto.setRole(UserDto.Role.values()[resultSet.getInt("role")]);
        return dto;
    }

    private void prepareForInsert(PreparedStatement statement, UserDto dto) throws SQLException {
        statement.setString(1, dto.getEmail());
        statement.setString(2, dto.getPassword());
        statement.setInt(3, dto.getRole().ordinal());
    }

    private void prepareForUpdate(PreparedStatement statement, UserDto dto) throws SQLException {
        prepareForInsert(statement, dto);
        statement.setLong(4, dto.getId());
    }

}
