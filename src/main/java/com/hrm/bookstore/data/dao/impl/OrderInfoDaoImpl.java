package com.hrm.bookstore.data.dao.impl;

import com.hrm.bookstore.data.connection.ConnectionManager;
import com.hrm.bookstore.data.dao.OrderInfoDao;
import com.hrm.bookstore.data.dto.OrderInfoDto;
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
public class OrderInfoDaoImpl implements OrderInfoDao {
    private static final String FIND_BY_ID = "SELECT i.id, i.order_id, i.book_id, i.book_quantity, i.book_price FROM order_infos i WHERE i.id = ?";
    private static final String FIND_ALL = "SELECT i.id, i.order_id, i.book_id, i.book_quantity, i.book_price FROM order_infos i";
    private static final String FIND_BY_ORDER_ID = "SELECT i.id, i.order_id, i.book_id, i.book_quantity, i.book_price FROM order_infos i WHERE i.order_id = ?";
    private static final String INSERT = "INSERT INTO order_infos (order_id, book_id, book_quantity, book_price) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE order_infos SET order_id = ?, book_id = ?, book_quantity = ?, book_price = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM order_infos WHERE id = ?";
    private final ConnectionManager connectionManager;

    @Override
    public OrderInfoDto findById(Long id) {
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
    public List<OrderInfoDto> findByOrderId(Long orderId) {
        List<OrderInfoDto> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ORDER_ID);
            statement.setLong(1, orderId);
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
    public List<OrderInfoDto> findAll() {
        List<OrderInfoDto> list = new ArrayList<>();
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
    public OrderInfoDto save(OrderInfoDto dto) {
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
    public OrderInfoDto update(OrderInfoDto dto) {
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

    private OrderInfoDto process(ResultSet resultSet) throws SQLException {
        OrderInfoDto dto = new OrderInfoDto();
        dto.setId(resultSet.getLong("id"));
        dto.setOrderId(resultSet.getLong("order_id"));
        dto.setBookId(resultSet.getLong("book_id"));
        dto.setBookPrice(resultSet.getBigDecimal("book_price"));
        dto.setBookQuantity(resultSet.getInt("book_quantity"));
        return dto;
    }

    private void prepareForInsert(PreparedStatement statement, OrderInfoDto dto) throws SQLException {
        statement.setLong(1, dto.getOrderId());
        statement.setLong(2, dto.getBookId());
        statement.setInt(3, dto.getBookQuantity());
        statement.setBigDecimal(4, dto.getBookPrice());
    }

    private void prepareForUpdate(PreparedStatement statement, OrderInfoDto dto) throws SQLException {
        prepareForInsert(statement, dto);
        statement.setLong(5, dto.getId());
    }

}
