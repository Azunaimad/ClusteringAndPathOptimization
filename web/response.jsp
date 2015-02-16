<%@ page import="java.util.Enumeration" %>
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
  Enumeration coordinates =  httpSession.getAttributeNames();
  String test = (String) coordinates.nextElement();
  String test2= (String) httpSession.getAttribute(test);
%>

<%=test%>
<br>
<%=test2%>
</body>
</html>
