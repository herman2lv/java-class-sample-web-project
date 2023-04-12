package com.hrm.bookstore.controller;

import com.hrm.bookstore.AppListener;
import com.hrm.bookstore.controller.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@WebServlet(urlPatterns = "/controller", loadOnStartup = 1)
@MultipartConfig(maxFileSize = Controller.MB * 10, maxRequestSize = Controller.MB * 100)
@Log4j2
public class Controller extends HttpServlet {
    public static final int MB = 1024 * 1024;
    public static final String REDIRECT = "redirect:";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        Command commandInstance = AppListener.getContext().getBean(command, Command.class);
        String page = commandInstance.execute(req);
        if (page.startsWith(REDIRECT)) {
            resp.sendRedirect(req.getContextPath() + "/" + page.substring(REDIRECT.length()));
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }

}
