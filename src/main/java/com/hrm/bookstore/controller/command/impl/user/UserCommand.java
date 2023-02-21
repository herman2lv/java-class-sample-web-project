package com.hrm.bookstore.controller.command.impl.user;

import com.hrm.bookstore.controller.command.Command;
import com.hrm.bookstore.data.entity.User;
import com.hrm.bookstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCommand implements Command {
    private final UserService service;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        User user = service.getById(id);
        req.setAttribute("user", user);
        return "jsp/user/user.jsp";
    }

}
