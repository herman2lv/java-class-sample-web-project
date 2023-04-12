package com.hrm.bookstore.controller.command.impl.user;

import com.hrm.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class CreateUserFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/user/createUserForm.jsp";
    }

}
