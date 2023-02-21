package com.hrm.bookstore.controller.command.impl;

import com.hrm.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/error.jsp";
    }

}
