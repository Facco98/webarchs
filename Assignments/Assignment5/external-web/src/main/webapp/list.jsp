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
<h1>Available accommodations for the selected period</h1>
<br/>

<jsp:useBean id="accommodations" scope="session" class="it.unitn.disi.webarchs.facchinetti.booker.externalweb.pojos.AccommodationsList" />
<c:forEach items="${accommodations.accommodations}" var="accommodation" varStatus="loop">
    <p>
        <b>${accommodation.type}</b>:
        <a href="<c:url value="/details?id=${loop.index}"/>">${accommodation.name}</a>
    </p>
</c:forEach>

<c:if test="${accommodations.accommodations.size() == 0}">
    <h4>No accommodation found matching the selected criteria</h4>
</c:if>


</body>
</html>
