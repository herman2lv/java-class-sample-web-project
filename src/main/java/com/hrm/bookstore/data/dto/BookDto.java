package com.hrm.bookstore.data.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDto {
    private Long id;
    private String title;
    private BigDecimal price;

}
