<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>Order</title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>All Orders</h1>
<table>
    <tr>
        <th>#</th>
        <th>Id</th>
        <th>User</th>
        <th>Items</th>
        <th>Status</th>
    </tr>
    <c:forEach items="${requestScope.orders}" var="order" varStatus="counter">
        <tr>
            <td>${counter.count}</td>
            <td><a href="controller?command=order&id=${order.id}">${order.id}</a></td>
            <td><a href="controller?command=user&id=${order.user.id}">${order.user.email}</a></td>
            <td>
                <ul>
                    <c:forEach items="${order.details}" var="info">
                        <li>${info.bookQuantity} x <a
                                href="controller?command=book&id=${info.book.id}">${info.book.title}</a></li>
                    </c:forEach>
                </ul>
                TOTAL PRICE: ${order.totalCost} USD
            </td>
            <td>${order.status.toString()}</td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
