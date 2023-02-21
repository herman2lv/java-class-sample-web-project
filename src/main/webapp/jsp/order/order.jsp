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
<h1>Order Detailed Info</h1>
<table>
    <tr>
        <th>Id</th>
        <th>User</th>
        <th>Items</th>
        <th>Status</th>
    </tr>
    <tr>
        <td><a href="controller?command=order&id=${requestScope.order.id}">${requestScope.order.id}</a></td>
        <td><a href="controller?command=user&id=${requestScope.order.user.id}">${requestScope.order.user.email}</a></td>
        <td>
            <table>
                <c:forEach items="${requestScope.order.details}" var="info">
                    <tr>
                        <td><a href="controller?command=book&id=${info.book.id}">${info.book.title}</a></td>
                        <td>$${info.bookPrice} x ${info.bookQuantity}</td>
                    </tr>
                </c:forEach>
            </table>
            TOTAL PRICE: ${requestScope.order.totalCost} USD
        </td>
        <td>${requestScope.order.status}</td>
    </tr>

</table>
</body>
</html>
