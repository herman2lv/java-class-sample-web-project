package com.hrm.bookstore.controller.command.impl.order;

import com.hrm.bookstore.controller.command.Command;
import com.hrm.bookstore.data.entity.Order;
import com.hrm.bookstore.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("order")
@RequiredArgsConstructor
public class OrderCommand implements Command {
    private final OrderService service;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        Order order = service.getById(id);
        req.setAttribute("order", order);
        return "jsp/order/order.jsp";

    }

}
