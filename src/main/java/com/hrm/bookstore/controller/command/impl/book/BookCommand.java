package com.hrm.bookstore.controller.command.impl.book;

import com.hrm.bookstore.controller.command.Command;
import com.hrm.bookstore.data.entity.Book;
import com.hrm.bookstore.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookCommand implements Command {
    private final BookService service;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        Book book = service.getById(id);
        req.setAttribute("book", book);
        return "jsp/book/book.jsp";

    }

}
