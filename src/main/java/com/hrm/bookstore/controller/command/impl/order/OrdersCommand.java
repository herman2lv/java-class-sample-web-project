package com.hrm.bookstore.controller.command.impl.order;

import com.hrm.bookstore.controller.command.Command;
import com.hrm.bookstore.data.entity.Order;
import com.hrm.bookstore.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller("orders")
@RequiredArgsConstructor
public class OrdersCommand implements Command {
    private final OrderService service;

    @Override
    public String execute(HttpServletRequest req) {
        List<Order> orders = service.getAll();
        req.setAttribute("orders", orders);
        return "jsp/order/orders.jsp";
    }

}
