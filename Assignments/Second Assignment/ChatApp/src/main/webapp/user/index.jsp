<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>FreeChat</title>
    </head>
    <body>
        <jsp:include page="banner.jsp"/>
        <jsp:useBean id="rooms" scope="request" type="it.unitn.disi.webarch.facchinetti.chatapp.bean.RoomListBean"/>
        <h3><c:out value="${rooms.message}"/></h3>


        <b>Available rooms: ${rooms.roomList.size()}</b>
        <ul>
            <c:forEach items="${rooms.roomList}" var="room">
                <li>
                    <a href='<c:url value="${room.url}"/>'>
                        <i>
                        <c:out value="${room.name}"/>
                        </i>
                    </a>
                </li>
            </c:forEach>
        </ul>

        <h3>Create a new room</h3>
        <form method="POST" action=".">
            <label for="inputName"></label>
            <input type="text" name="roomName" id="inputName" placeholder="Insert the room name here">
            <input type="submit" value="Create room">
        </form>
        <c:out value="${rooms.resultMessage}"/>
    </body>
</html>
