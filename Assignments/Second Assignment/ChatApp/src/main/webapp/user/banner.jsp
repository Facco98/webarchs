<%--
  Created by IntelliJ IDEA.
  User: claudiofacchinetti
  Date: 02/10/21
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<span>
    <p style="text-align: left;">Welcome ${sessionScope.get("USER")} - <a href="<c:url value="/user/logout"/>">Logout</a></p>
</span>

