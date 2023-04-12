package com.hrm.bookstore.controller.command.impl.order;

import com.hrm.bookstore.controller.command.Command;
import com.hrm.bookstore.data.entity.Order;
import com.hrm.bookstore.data.entity.User;
import com.hrm.bookstore.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class CreateOrderCommand implements Command {
    private final OrderService orderService;

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "jsp/loginForm.jsp";
        }
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        Order processed = orderService.processCart(cart, user);
        Order created = orderService.create(processed);
        session.removeAttribute("cart");
        req.setAttribute("order", created);
        req.setAttribute("message", "Order created successfully");
        return "jsp/order/order.jsp";

    }
}
