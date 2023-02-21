package com.hrm.bookstore.data.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Book {
    private Long id;
    private String title;
    private BigDecimal price;

}
