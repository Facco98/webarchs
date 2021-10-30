<%--
  Created by IntelliJ IDEA.
  User: claudiofacchinetti
  Date: 22/10/21
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="SESSION_USER_BEAN" scope="session" type="it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.UserBean"/>
<html>
<head>
    <title>Memory</title>
</head>
<body>
<h1>Welcome, ${SESSION_USER_BEAN.username}</h1>
<hr>
<h2>Ranking: </h2>
<div id="ranking-list">
    Loading the ranking...
</div>
<hr>
<form method="POST" action=".">
    <input type="submit" value="Play game!">
</form>


<script src="../js/ranking.js"></script>
</body>
</html>
