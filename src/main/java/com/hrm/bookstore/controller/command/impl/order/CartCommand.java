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
public class CartCommand implements Command {
    public static final String PAGE = "jsp/order/cart.jsp";
    private final OrderService orderService;

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            req.setAttribute("message", "Cart is empty");
            return PAGE;
        }
        User user = (User) session.getAttribute("user");
        Order processed = orderService.processCart(cart, user);
        req.setAttribute("cart", processed);
        return PAGE;

    }

}
