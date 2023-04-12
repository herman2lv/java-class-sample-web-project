package com.hrm.bookstore.data.dao.impl;

import com.hrm.bookstore.data.connection.ConnectionManager;
import com.hrm.bookstore.data.dao.OrderDao;
import com.hrm.bookstore.data.dto.OrderDto;
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
public class OrderDaoImpl implements OrderDao {
    private static final String FIND_BY_ID = "SELECT o.id, o.user_id, o.total_cost, o.status FROM orders o WHERE o.id = ?";
    private static final String FIND_ALL = "SELECT o.id, o.user_id, o.total_cost, o.status FROM orders o";
    private static final String INSERT = "INSERT INTO orders (user_id, total_cost, status) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE orders SET user_id = ?, total_cost = ?, status = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM orders WHERE id = ?";
    private final ConnectionManager connectionManager;

    @Override
    public OrderDto findById(Long id) {
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
    public List<OrderDto> findAll() {
        List<OrderDto> list = new ArrayList<>();
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
    public OrderDto save(OrderDto dto) {
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
    public OrderDto update(OrderDto dto) {
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

    private OrderDto process(ResultSet resultSet) throws SQLException {
        OrderDto dto = new OrderDto();
        dto.setId(resultSet.getLong("id"));
        dto.setUserId(resultSet.getLong("user_id"));
        dto.setTotalCost(resultSet.getBigDecimal("total_cost"));
        int statusOrdinal = resultSet.getInt("status");
        dto.setStatus(OrderDto.Status.values()[statusOrdinal]);
        return dto;
    }

    private void prepareForInsert(PreparedStatement statement, OrderDto dto) throws SQLException {
        statement.setLong(1, dto.getUserId());
        statement.setBigDecimal(2, dto.getTotalCost());
        statement.setInt(3, dto.getStatus().ordinal());
    }

    private void prepareForUpdate(PreparedStatement statement, OrderDto dto) throws SQLException {
        prepareForInsert(statement, dto);
        statement.setLong(4, dto.getId());
    }

}
