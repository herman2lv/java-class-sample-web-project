package com.hrm.bookstore.controller;

import com.hrm.bookstore.data.connection.impl.ConnectionManagerImpl;
import com.hrm.bookstore.data.dao.impl.UserDaoImpl;
import com.hrm.bookstore.data.entity.User;
import com.hrm.bookstore.data.mapper.DataMapperImpl;
import com.hrm.bookstore.data.repository.impl.UserRepositoryImpl;
import com.hrm.bookstore.service.UserService;
import com.hrm.bookstore.service.impl.DigestServiceImpl;
import com.hrm.bookstore.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/user")
public class UserController extends HttpServlet {
    private static final String URL = "jdbc:postgresql://localhost:5432/sample_web_project";
    private static final String PSW = "root";
    private static final String USR = "postgres";
    private static final String DRV = "org.postgresql.Driver";
    private UserService userService = new UserServiceImpl(
            new UserRepositoryImpl(
                    new UserDaoImpl(
                            new ConnectionManagerImpl(URL, PSW, USR, DRV)), new DataMapperImpl()), new DigestServiceImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idStr = req.getParameter("id");
            Long id = Long.parseLong(idStr);
            User user = userService.getById(id);
            req.setAttribute("user", user);
            req.setAttribute("date", LocalDateTime.now().toString());
            req.getRequestDispatcher("jsp/user.jsp").forward(req, resp);
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
        }

    }
}
