<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<a href="controller?command=books&page=1">First</a>
<a href="controller?command=books&page=${requestScope.currentPage - 1}">Prev</a>
Page ${requestScope.currentPage} out of ${requestScope.totalPages}
<a href="controller?command=books&page=${requestScope.currentPage + 1}">Next</a>
<a href="controller?command=books&page=${requestScope.totalPages}">Last</a>
