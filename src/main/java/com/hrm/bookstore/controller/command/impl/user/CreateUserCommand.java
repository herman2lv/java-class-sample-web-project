package com.hrm.bookstore.controller.command.impl.user;

import com.hrm.bookstore.controller.command.Command;
import com.hrm.bookstore.data.entity.User;
import com.hrm.bookstore.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateUserCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        try {
            Part part = req.getPart("avatar");
            if (part != null) {
                String fileName = UUID.randomUUID() + "_" + part.getSubmittedFileName();
                part.write("C:\\server_storage\\" + fileName);
            }
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }


        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(User.Role.USER);
        User created = userService.create(user);
        req.setAttribute("user", created);
        req.setAttribute("message", "New user was created");
        return "jsp/user/user.jsp";
    }

}
