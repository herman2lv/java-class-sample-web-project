package com.hrm.bookstore.controller.command.impl.book;

import com.hrm.bookstore.controller.command.Command;
import com.hrm.bookstore.controller.util.PagingUtil;
import com.hrm.bookstore.controller.util.PagingUtil.Paging;
import com.hrm.bookstore.data.entity.Book;
import com.hrm.bookstore.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller("books")
@RequiredArgsConstructor
public class BooksCommand implements Command {
    private final BookService service;
    private final PagingUtil pagingUtil;

    @Override
    public String execute(HttpServletRequest req) {
        Paging paging = pagingUtil.getPaging(req);
        List<Book> books = service.getAll(paging.getLimit(), paging.getOffset());
        long totalEntities = service.count();
        long totalPages = pagingUtil.getTotalPages(totalEntities, paging.getLimit());
        req.setAttribute("currentPage", paging.getPage());
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("books", books);
        return "jsp/book/books.jsp";
    }

}
