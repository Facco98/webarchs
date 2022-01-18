<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <title>Accommodation booker</title>
</head>
<body>
<h1>Book your accommodation</h1>
<br/>

<h3>Choose the period</h3>
<form method="post" action="<c:url value="/book"/>">
    <label for="startDate">Insert the arrival date</label>
    <br>
    <input type="date" name="startDate" id="startDate">
    <br>
    <br>
    <label for="endDate">Insert the last day</label>
    <br>
    <input type="date" name="leavingDate" id="endDate">
    <br>
    <br>
    <label for="accommodationType">Choose the accommodation type</label>
    <select name="accommodationType" id="accommodationType">
        <option value="all">All accommodations</option>
        <option value="hotels">Only hotels</option>
        <option value="apartments">Only apartments</option>
    </select>
    <br>
    <br>
    <label for="numberPeople">Insert the number of people</label>
    <br>
    <input type="number" value="0" name="numberPeople" id="numberPeople">
    <br>
    <br>
    <input type="submit" value="Check available reservations">
</form>

<hr>

<h3>View your reservations</h3>
<form method="get" action="<c:url value="/reservations"/>">
    <label>Name</label>
    <input type="text" name="name">
    <br>
    <label>Surname</label>
    <input type="text" name="surname">
    <br>
    <input type="submit" value="View reservations">
</form>

</body>
</html>
