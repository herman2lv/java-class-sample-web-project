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
<h1>Cart</h1>
<c:if test="${requestScope.cart == null}">
    <p>You haven't added anything in your cart</p>
</c:if>
<c:if test="${requestScope.cart != null}">
    <table>
        <tr>
            <th>Items</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>
        <c:forEach items="${requestScope.cart.details}" var="item">
            <tr>
                <td><a href="controller?command=book&id=${item.book.id}">${item.book.title}</a></td>
                <td>${item.book.price}</td>
                <td>x ${item.bookQuantity}</td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3">TOTAL COST: ${requestScope.cart.totalCost}</td>
        </tr>
    </table>
    <a href="controller?command=create_order">Place order</a>
</c:if>
</body>
</html>
