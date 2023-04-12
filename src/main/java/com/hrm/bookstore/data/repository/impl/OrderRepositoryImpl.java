package com.hrm.bookstore.data.repository.impl;

import com.hrm.bookstore.data.dao.BookDao;
import com.hrm.bookstore.data.dao.OrderDao;
import com.hrm.bookstore.data.dao.OrderInfoDao;
import com.hrm.bookstore.data.dao.UserDao;
import com.hrm.bookstore.data.dto.OrderDto;
import com.hrm.bookstore.data.dto.OrderInfoDto;
import com.hrm.bookstore.data.entity.Book;
import com.hrm.bookstore.data.entity.Order;
import com.hrm.bookstore.data.entity.OrderInfo;
import com.hrm.bookstore.data.entity.User;
import com.hrm.bookstore.data.mapper.DataMapper;
import com.hrm.bookstore.data.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderDao orderDao;
    private final OrderInfoDao orderInfoDao;
    private final UserDao userDao;
    private final BookDao bookDao;
    private final DataMapper dataMapper;

    @Override
    public Order findById(Long id) {
        OrderDto orderDto = orderDao.findById(id);
        return combineOrder(orderDto);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll()
                .stream()
                .map(this::combineOrder)
                .collect(Collectors.toList());
    }

    @Override
    public Order save(Order entity) {
        OrderDto orderDto = dataMapper.toDto(entity);
        OrderDto savedDto = orderDao.save(orderDto);
        entity.getDetails().forEach(orderInfo -> {
            OrderInfoDto orderInfoDto = dataMapper.toDto(orderInfo);
            orderInfoDao.save(orderInfoDto);
        });
        return combineOrder(savedDto);
    }

    @Override
    public Order update(Order entity) {
        OrderDto orderDto = dataMapper.toDto(entity);
        OrderDto updatedDto = orderDao.update(orderDto);
        orderInfoDao.findByOrderId(entity.getId())
                .forEach(oldOrderInfo -> orderInfoDao.delete(oldOrderInfo.getId()));
        entity.getDetails().forEach(orderInfo -> {
            OrderInfoDto orderInfoDto = dataMapper.toDto(orderInfo);
            orderInfoDao.save(orderInfoDto);
        });
        return combineOrder(updatedDto);
    }

    @Override
    public boolean delete(Long id) {
        if (!orderDao.delete(id)) {
            return false;
        }
        orderInfoDao.findByOrderId(id)
                .forEach(orderInfoDto -> orderInfoDao.delete(orderInfoDto.getId()));
        return true;
    }

    private Order combineOrder(OrderDto orderDto) {
        Order order = dataMapper.toEntity(orderDto);
        User user = dataMapper.toEntity(userDao.findById(orderDto.getUserId()));
        order.setUser(user);
        List<OrderInfoDto> detailsDto = orderInfoDao.findByOrderId(orderDto.getId());
        List<OrderInfo> details = new ArrayList<>();
        detailsDto.forEach(dto -> {
            OrderInfo entity = dataMapper.toEntity(dto);
            Book book = dataMapper.toEntity(bookDao.findById(dto.getBookId()));
            entity.setBook(book);
        });
        order.setDetails(details);
        return order;
    }
}
