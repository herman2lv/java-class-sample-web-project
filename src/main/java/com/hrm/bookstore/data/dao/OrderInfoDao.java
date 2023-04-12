package com.hrm.bookstore.data.dao;

import com.hrm.bookstore.data.dto.OrderInfoDto;

import java.util.List;

public interface OrderInfoDao extends CrudDao<Long, OrderInfoDto> {
    List<OrderInfoDto> findByOrderId(Long orderId);

}
