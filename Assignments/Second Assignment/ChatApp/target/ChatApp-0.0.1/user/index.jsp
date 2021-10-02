<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>FreeChat</title>
</head>
<body>
<jsp:include page="banner.jsp"/>
<h3>Available rooms</h3>
<jsp:useBean id="rooms" scope="request" type="it.unitn.disi.webarch.facchinetti.chat.ChatApp.bean.RoomListBean"/>
<c:if test="${rooms.size() == 0}">
    <p>There a no rooms yet, create the first one!</p>
</c:if>
<c:if test="${rooms.size() > 0}">
    <p>Join a room or create a new one</p>
</c:if>
<c:forEach items="${rooms}" var="room">
    <a href='<c:url value="${room.url}"/>'>
        <i>
        <c:out value="${room.name}"/>
        </i>
    </a>
</c:forEach>
</body>
</html>
