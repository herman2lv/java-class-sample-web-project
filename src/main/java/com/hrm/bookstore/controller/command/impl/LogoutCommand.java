package com.hrm.bookstore.controller.command.impl;

import com.hrm.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("logout")
public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
        return "index.jsp";
    }
}
