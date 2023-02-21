<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="navbar">
    <li><a href="controller?command=home">Home</a></li>
    <c:if test="${sessionScope.user != null}">
        <li><a href="controller?command=logout">Logout</a></li>
    </c:if>
    <c:if test="${sessionScope.user == null}">
        <li><a href="controller?command=create_user_form">Sign up</a></li>
        <li><a href="controller?command=login_form">Sign in</a></li>
    </c:if>
    <li><a href="controller?command=cart">Cart</a></li>
    <li><a href="controller?command=books">All Books</a></li>
    <li><a href="controller?command=users">All Users</a></li>
    <li><a href="controller?command=orders">All Orders</a></li>
</ul>
