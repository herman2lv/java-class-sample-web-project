package com.hrm.bookstore.service;

import com.hrm.bookstore.data.entity.Order;
import com.hrm.bookstore.data.entity.User;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order getById(Long id);

    List<Order> getAll();

    Order create(Order entity);

    Order update(Order entity);

    void delete(Long id);

    Order processCart(Map<Long, Integer> cart, User user);

}
