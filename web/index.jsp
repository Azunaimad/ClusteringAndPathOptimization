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
    <form method="get" name = 'frm'>
      <div id="dynamicInput">
        <table id="input-table" cellspacing="0" cellpadding="4">
        <tr>
          <td class="address">Адреса</td>
          <td class="volume">Объемы</td>
          <td class="start-point">Склад</td>
          <td class="start-point">Загрузка грузовика</td>
          <td><input type="text" class="volume" name="maxWeight" id="maxWeight"></td>
        </tr>
        </table>
      </div>



      <input type="hidden" name="hiddenFieldCoords" id="hiddenFieldCoords" value="_">
      <input type="hidden" name="hiddenFieldVolume" id="hiddenFieldVolume" value="_">
      <input type="hidden" name="hiddenFieldStore" id="hiddenFieldStore" value="_">
      <input type="hidden" name="hiddenFieldMaxWeight" id="hiddenFieldMaxWeight" value="">

      <input id = "submit" type="submit" value="Рассчитать оптимальный маршрут" onclick="fillHiddenFields()">
      <input id = "clear" type="button" value="Очистить" onclick="removeAll('input-table')">
    </form>

  <%
    String coordinates = request.getParameter("hiddenFieldCoords");
    String volume = request.getParameter("hiddenFieldVolume");
    String store = request.getParameter("hiddenFieldStore");
    String weight = request.getParameter("hiddenFieldMaxWeight");

    HttpSession httpSession = request.getSession();
    httpSession.setAttribute("coordinates", coordinates);
    httpSession.setAttribute("volume",volume);
    httpSession.setAttribute("store", store);
    httpSession.setAttribute("weight", weight);
    if(request.getParameter("hiddenFieldCoords")!=null)
      response.sendRedirect("/response.jsp");
  %>


  </body>
</html>
