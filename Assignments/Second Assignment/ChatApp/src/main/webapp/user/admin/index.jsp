<%--
  Created by IntelliJ IDEA.
  User: claudiofacchinetti
  Date: 08/10/21
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>FreeChat - Admin page</title>
</head>
<body>
<jsp:include page="../banner.jsp"/>
<jsp:useBean id="resultPresent" scope="request" type="java.lang.Boolean"/>
<c:if test="${resultPresent}">
    <jsp:useBean id="result" scope="request" type="it.unitn.disi.webarch.facchinetti.chatapp.bean.UserInsertionResultBean"/>
    <c:if test="${result.success == true}">
        <h4>User created successfully</h4>
    </c:if>
    <c:if test="${result.success == false}">
        <h4>Error while creating the user: ${result.errorMessage}</h4>
    </c:if>
</c:if>

<h3>Add a new user</h3>
<form method="POST" action=".">
    <label for="inputUsername">Username: </label>
    <input type="text" name="username" id="inputUsername" placeholder="Insert the username here">
    <br>
    <label for="inputPassword">Password: </label>
    <input type="password" name="password" id="inputPassword" placeholder="Insert the password here">
    <br>
    <input type="submit" value="Create user">
</form>
</body>
</html>
