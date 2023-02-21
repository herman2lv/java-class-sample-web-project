<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>BookStore</title>
</head>
<body>
<jsp:include page="jsp/navbar.jsp"/>
<h1>Welcome to Bookstore, Dear ${sessionScope.user != null ? sessionScope.user.email : 'Guest'}!</h1>
<img src="images/bookstore.png" alt="bookstore"/>
</body>
</html>
