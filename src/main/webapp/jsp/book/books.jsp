<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>Books</title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>All Books</h1>
<table>
    <jsp:include page="../pagination.jsp"/>
    <tr>
        <th>#</th>
        <th>Id</th>
        <th>Title</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${requestScope.books}" var="book" varStatus="counter">
        <tr>
            <td>${counter.count}</td>
            <td>${book.id}</td>
            <td><a href="controller?command=book&id=${book.id}">${book.title}</a></td>
            <td>
                <form method="post" action="controller">
                    <input type="hidden" name="command" value="add_to_cart">
                    <input type="hidden" name="bookId" value="${book.id}">
                    <input type="submit" value="Add to Card">
                </form>
            </td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
