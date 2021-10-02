<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>FreeChat</title>
</head>
<body>
<h1>Welcome to FreeChat</h1>
<h3>Please login to proceed</h3>
<br/>
<form method="POST" action="login">
    <label for="inputUsername">Username: </label>
    <input type="text" name="username" id="inputUsername" placeholder="Insert your username here">
    <br>
    <label for="inputPassword">Password: </label>
    <input type="password" name="password" id="inputPassword" placeholder="Insert your password here">
    <br>
    <input type="submit" value="Login">
</form>
</body>
</html>