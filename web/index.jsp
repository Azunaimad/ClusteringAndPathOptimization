<%--
  Created by IntelliJ IDEA.
  User: Azunai
  Date: 04.02.2015
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Оптимизация перевозок</title>

    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensors=false&libraries=places&language=ru"></script>
    <script type="text/javascript" src="googleMap.js"></script>


  </head>
  <link rel="stylesheet" type="text/css" href="map.css">
  <body>

    <input id="pac-input" class="controls" type="text" placeholder="Search Box">
    <div id="map-canvas" style="width: 100%; height: 500px"></div>
    <form method="get" name = 'frm' action="response.jsp">
      <div id="dynamicInput">
        <table id="input-table" cellspacing="0" cellpadding="4">
        <tr>
          <td class="address">Адреса</td>
          <td class="volume">Объемы перевозок</td>
          <td class="start-point">Склад</td>
        </tr>
        </table>
      </div>


      <input type="hidden" name="hiddenField" id="hiddenField" value="">
      <input id = "submit" type="submit" value="Рассчитать оптимальный маршрут">
      <input id = "clear" type="button" value="Очистить" onclick="removeAll('input-table')">
    </form>


  <%
    String coordinates = request.getParameter("hiddenField");
    String[] coords;
    HttpSession httpSession = request.getSession();

    if(coordinates != null && !coordinates.equals("")){
      coords = coordinates.split("_");
      httpSession.setAttribute("coordinates", coords);
    }
  %>

  </body>
</html>
