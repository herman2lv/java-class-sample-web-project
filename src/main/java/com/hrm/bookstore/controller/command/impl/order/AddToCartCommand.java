package com.hrm.bookstore.controller.command.impl.order;

import com.hrm.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class AddToCartCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        Long bookId = Long.parseLong(req.getParameter("bookId"));
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        Integer quantity = cart.get(bookId);
        if (quantity == null) {
            cart.put(bookId, 1);
        } else {
            cart.put(bookId, quantity + 1);
        }
        //cart.merge(bookId, 1, Integer::sum); //the same as 6 lines above
        session.setAttribute("cart", cart);

        return "redirect:controller?command=books";
    }
}
