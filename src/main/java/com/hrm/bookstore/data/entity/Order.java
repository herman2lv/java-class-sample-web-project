package com.hrm.bookstore.data.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Order {
    private Long id;
    private User user;
    private BigDecimal totalCost;
    private Status status;
    private List<OrderInfo> details;

    public enum Status {
        PENDING, PAID, DELIVERED, CANCELED
    }
}
