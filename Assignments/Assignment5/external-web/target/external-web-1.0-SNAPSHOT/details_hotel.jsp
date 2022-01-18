<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="accommodation" scope="request" class="it.unitn.disi.webarchs.facchinetti.booker.dto.HotelDTO" />
<!DOCTYPE html>
<html>
<head>
    <title>Accommodation booker</title>
</head>
<body>

<a href="<c:url value="/index.jsp"/>"> Back to home</a>
<jsp:useBean id="accommodationPrice" scope="request" class="it.unitn.disi.webarchs.facchinetti.booker.dto.HotelPriceDTO" />
<h1>Details for the accommodation: ${accommodation.name}</h1>
<br/>

<b>Price: ${accommodationPrice.finalPrice}</b>
<br>
<br>
<b>Extra for half board: </b> ${accommodationPrice.pricePerDayOfHalfBoard}
<br>
<b>Final price with half board: </b> ${accommodationPrice.finalPriceWithHalfBoard}

<h3>Book the spot!</h3>


<form method="post" action="<c:url value="/reservations"/>">
    <input type="hidden" name="id" value="${accommodation.id}">
    <label>Name</label>
    <input type="text" name="name">
    <br>
    <label>Surname</label>
    <input type="text" name="surname">
    <br>
    <label>Credit card</label>
    <input type="text" name="creditCard">
    <br>
    <label>With half board?</label>
    <input type="checkbox" name="halfBoard">
    <br>
    <input type="submit" value="book"/>
</form>



</body>
</html>
