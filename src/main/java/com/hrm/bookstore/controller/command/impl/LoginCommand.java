package com.hrm.bookstore.controller.command.impl;

import com.hrm.bookstore.controller.command.Command;
import com.hrm.bookstore.data.entity.User;
import com.hrm.bookstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("login")
@RequiredArgsConstructor
public class LoginCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = userService.login(email, password);
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        return "index.jsp";
    }
}
