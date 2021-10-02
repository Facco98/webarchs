<%--
  Created by IntelliJ IDEA.
  User: claudiofacchinetti
  Date: 02/10/21
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="roomInfo" type="it.unitn.disi.webarch.facchinetti.chat.ChatApp.bean.RoomBean" scope="request" />
<html>
<meta http-equiv="refresh" content="5">
<head>
    <title>${roomInfo.name} - FreeChat</title>
</head>
<body>
<jsp:include page="../banner.jsp"/>
<b>Welcome in the room: ${roomInfo.name}</b>
<form method="POST">
    <label for="newMessageContent">Your message: </label>
    <input type="text" name="messageContent" id="newMessageContent">
    <input type="submit" value="Send">
</form>
<c:if test="${roomInfo.messages.size() == 0}">
    <p>There are no messages yet: write the first one!</p>
</c:if>
<c:forEach items="${roomInfo.messages}" var="message">
    <c:out value="${message.dateTime}"/> &nbsp;
    <b><c:out value="${message.author}"/></b>: &nbsp;
    <c:out value="${message.content}"/>
    <br>
</c:forEach>
</body>
</html>
