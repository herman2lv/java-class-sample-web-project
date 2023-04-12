package com.hrm.bookstore.service.impl;

import com.hrm.bookstore.data.entity.Book;
import com.hrm.bookstore.data.entity.Order;
import com.hrm.bookstore.data.entity.OrderInfo;
import com.hrm.bookstore.data.entity.User;
import com.hrm.bookstore.data.repository.BookRepository;
import com.hrm.bookstore.data.repository.OrderRepository;
import com.hrm.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    @Override
    public Order getById(Long id) {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new RuntimeException("No order with id: " + id);
        }
        return order;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order create(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public Order processCart(Map<Long, Integer> cart, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        List<OrderInfo> details = new ArrayList<>();
        cart.forEach((bookId, quantity) -> {
            OrderInfo orderInfo = new OrderInfo();
            Book book = bookRepository.findById(bookId);
            orderInfo.setBook(book);
            orderInfo.setBookPrice(book.getPrice());
            orderInfo.setBookQuantity(quantity);
            details.add(orderInfo);
        });
        order.setDetails(details);
        BigDecimal totalCost = calculatePrice(details);
        order.setTotalCost(totalCost);
        return order;
    }

    private BigDecimal calculatePrice(List<OrderInfo> details) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderInfo detail : details) {
            BigDecimal bookPrice = detail.getBookPrice();
            BigDecimal itemPrise = bookPrice.multiply(BigDecimal.valueOf(detail.getBookQuantity()));
            totalCost = totalCost.add(itemPrise);
        }
        return totalCost;
    }

    @Override
    public Order update(Order entity) {
        return orderRepository.update(entity);
    }

    @Override
    public void delete(Long id) {
        if (!orderRepository.delete(id)) {
            throw new RuntimeException("Couldn't delete order (id=" + id + ")");
        }
    }

}
