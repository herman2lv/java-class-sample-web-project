package com.hrm.bookstore.controller.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class PagingUtil {

    public Paging getPaging(HttpServletRequest req) {
        String limitStr = req.getParameter("limit");
        int limit;
        if (limitStr == null) {
            limit = 10;
        } else {
            limit = Integer.parseInt(limitStr);
        }
        String offsetStr = req.getParameter("page");
        long page;
        if (offsetStr == null) {
            page = 1;
        } else {
            page = Long.parseLong(offsetStr);
        }
        long offset = (page - 1) * limit;
        return new Paging(limit, offset, page);
    }

    public long getTotalPages(long totalEntities, int limit) {
        long totalPages = totalEntities / limit;
        int additionalPage = (totalEntities - (totalPages * limit) > 0) ? 1 : 0;
        return totalPages + additionalPage;
    }

    @Data
    public static class Paging {
        private final int limit;
        private final long offset;
        private final long page;
    }
}
