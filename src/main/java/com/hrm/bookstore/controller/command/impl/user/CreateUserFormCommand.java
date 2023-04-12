package com.hrm.bookstore.controller.command.impl.user;

import com.hrm.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("create_user_form")
public class CreateUserFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/user/createUserForm.jsp";
    }

}
