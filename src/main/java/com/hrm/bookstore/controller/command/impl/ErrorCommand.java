package com.hrm.bookstore.controller.command.impl;

import com.hrm.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("error")
public class ErrorCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/error.jsp";
    }

}
