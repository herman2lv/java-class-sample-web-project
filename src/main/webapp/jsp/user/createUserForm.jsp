<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>Register new user</title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Register new user</h1>
<form method="post" action="controller" enctype="multipart/form-data">
    <input type="file" name="avatar" accept="image/*">
    <br/>
    <input name="command" type="hidden" value="create_user"/>
    <label for="email-input">Email: </label>
    <input id="email-input" name="email" type="email"/>
    <br/>
    <label for="password-input">Password: </label>
    <input id="password-input" name="password" type="password" minlength="4"/>
    <br/>
    <input type="submit" value="REGISTER">
</form>
</body>
</html>
