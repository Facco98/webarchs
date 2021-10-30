<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>FreeChat</title>
</head>
<body>
<h1>Welcome to FreeChat</h1>
<h3>Please login to proceed</h3>
<br/>

<jsp:useBean id="error" class="java.lang.String" scope="request"/>
<c:if test="${not empty error}">
    <h4>Wrong username/password combination</h4>
</c:if>

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