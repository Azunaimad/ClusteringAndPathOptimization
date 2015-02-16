<%--
  Created by IntelliJ IDEA.
  User: Azunai
  Date: 15.02.2015
  Time: 23:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
TEST
<%
HttpSession httpSession = request.getSession(false);
  Object coords = httpSession.getAttribute("coordinates");


%>
<%=coords%>
<%

%>
</body>
</html>
