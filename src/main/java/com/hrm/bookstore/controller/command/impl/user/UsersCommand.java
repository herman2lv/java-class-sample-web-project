package com.hrm.bookstore.controller.command.impl.user;

import com.hrm.bookstore.controller.command.Command;
import com.hrm.bookstore.data.entity.User;
import com.hrm.bookstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller("users")
@RequiredArgsConstructor
public class UsersCommand implements Command {
    private final UserService service;

    @Override
    public String execute(HttpServletRequest req) {
        List<User> users = service.getAll();
        req.setAttribute("users", users);
        return "jsp/user/users.jsp";
    }

}
