<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Accommodation booker</title>
</head>
<body>
<a href="<c:url value="/index.jsp"/>"> Back to home</a>
<br>
<h1>Your reservations</h1>
<br>

<jsp:useBean id="reservations" scope="session" class="it.unitn.disi.webarchs.facchinetti.booker.externalweb.pojos.ReservationList" />
<c:forEach items="${reservations.reservations}" var="reservation" varStatus="loop">
    <b>${reservation.accommodationDTO.name}</b> from ${reservation.startDate.toLocaleString()} to ${reservation.endDate.toLocaleString()}
    <br>
    <b>Price</b>: ${reservation.price}
    <hr>
</c:forEach>

<c:if test="${reservations.reservations.size() == 0}">
    <h4>No reservations found!</h4>
</c:if>


</body>
</html>
