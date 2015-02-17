<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.google.appengine.repackaged.com.google.api.services.datastore.client.DatastoreException" %>
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
  Enumeration data =  httpSession.getAttributeNames();

  // Get Coordinates
  String element = (String) data.nextElement();
  String coordinatesStr= (String) httpSession.getAttribute(element);
  String[] tempCoordinates = coordinatesStr.split("_");
  tempCoordinates = Arrays.copyOfRange(tempCoordinates, 1, tempCoordinates.length);
  Double[][] coordinates = new Double[2][tempCoordinates.length];
  for(int i=0; i<tempCoordinates.length; i++){
    String[] temp;
    temp = (tempCoordinates[i].replaceAll("[^0-9. ]","")).split(" ");
    coordinates[0][i] = Double.parseDouble(temp[0]);
    coordinates[1][i] = Double.parseDouble(temp[1]);
  }

  // Get Volumes
  element = (String) data.nextElement();
  String volumesStr = (String) httpSession.getAttribute(element);
  String[] tempVolumes = volumesStr.split("_");
  tempVolumes = Arrays.copyOfRange(tempVolumes,1,tempVolumes.length);
  double[] volumes = new double[tempVolumes.length];
  for (int i=0; i<tempVolumes.length; i++){
    volumes[i] = Double.parseDouble(tempVolumes[i]);
  }

  // Get Store index
  element = (String) data.nextElement();
  String storeStr = (String) httpSession.getAttribute(element);
  String[] tempStore = storeStr.split("_");
  tempStore = Arrays.copyOfRange(tempStore,1,tempStore.length);
  int[] store = new int[tempStore.length];
  int storeIndex = -1;
  for(int i=0; i<tempStore.length; i++) {
    store[i] = Integer.parseInt(tempStore[i]);
    if(store[i] == 1 && storeIndex == -1)
      storeIndex = i;
    else
      throw new Exception();
  }



%>



</body>
</html>
