<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>Book</title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Book Detailed Info</h1>
<table>
    <tr>
        <th>Field</th>
        <th>Value</th>
    </tr>
    <tr>
        <td>ID</td>
        <td>${requestScope.book.id}</td>
    </tr>
    <tr>
        <td>Title</td>
        <td>${requestScope.book.title}</td>
    </tr>
    <tr>
        <td>Price</td>
        <td>${requestScope.book.price} USD</td>
    </tr>
</table>
</body>
</html>
