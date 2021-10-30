<%--
  Created by IntelliJ IDEA.
  User: claudiofacchinetti
  Date: 02/10/21
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="USER" scope="session" type="it.unitn.disi.webarch.facchinetti.chatapp.bean.UserSessionBean"/>
<span>
    <p style="text-align: left;">
        Welcome ${USER.username}
        <c:if test="${USER.admin}">
            - <a href="<c:url value="/user/admin/"/>" target="_blank">Admin page</a>
        </c:if>
        - <a href="<c:url value="/user/logout"/>">Logout</a></p>
</span>

