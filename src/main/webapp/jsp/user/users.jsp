<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>Users</title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>All Users</h1>
<table>
    <tr>
        <th>#</th>
        <th>Id</th>
        <th>Email</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${users}" var="user" varStatus="counter">
        <tr>
            <td>${counter.count}</td>
            <td>${user.id}</td>
            <td><a href="controller?command=user&id=${user.id}"><c:out value="${user.email}"/></a></td>
            <td>
                <form>
                    <input type="hidden" name="command" value="edit_user_form">
                    <input type="hidden" name="id" value="${user.id}">
                    <input type="submit" value="Edit">
                </form>
        </tr>
    </c:forEach>

</table>
</body>
</html>
