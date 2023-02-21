package com.hrm.bookstore.data.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderInfo {
    private Long id;
    private Long orderId;
    private Book book;
    private Integer bookQuantity;
    private BigDecimal bookPrice;
}
