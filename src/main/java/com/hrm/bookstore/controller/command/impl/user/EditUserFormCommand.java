package com.hrm.bookstore.controller.command.impl.user;

import com.hrm.bookstore.controller.command.Command;
import com.hrm.bookstore.data.entity.User;
import com.hrm.bookstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditUserFormCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        User user = userService.getById(id);
        req.setAttribute("user", user);
        return "jsp/user/editUserForm.jsp";
    }

}
