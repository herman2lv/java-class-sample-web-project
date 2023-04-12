package com.hrm.bookstore.controller.command.impl.user;

import com.hrm.bookstore.controller.command.Command;
import com.hrm.bookstore.data.entity.User;
import com.hrm.bookstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("edit_user")
@RequiredArgsConstructor
public class EditUserCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        User entity = userService.getById(Long.parseLong(req.getParameter("id")));
        entity.setEmail(req.getParameter("email"));
        entity.setRole(User.Role.valueOf(req.getParameter("role")));
        User updated = userService.update(entity);
        req.setAttribute("user", updated);
        req.setAttribute("message", "User data was saved");
        return "jsp/user/user.jsp";
    }

}
