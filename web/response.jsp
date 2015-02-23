<%@ page import="java.util.Arrays" %>
<%@ page import="SweepAlgorithm.Cluster" %>
<%@ page import="AntColonyOptimization.AntColonyOptimization" %>
<%@ page import="org.googlemap.DistanceMatrix" %>
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

  // Get Coordinates
  String coordinatesStr = (String) httpSession.getAttribute("coordinates");
  String[] tempCoordinates = coordinatesStr.split("_");
  tempCoordinates = Arrays.copyOfRange(tempCoordinates, 1, tempCoordinates.length);
  double[][] coordinates = new double[2][tempCoordinates.length];
  for(int i=0; i<tempCoordinates.length; i++){
    String[] temp;
    temp = (tempCoordinates[i].replaceAll("[^0-9. ]","")).split(" ");
    coordinates[0][i] = Double.parseDouble(temp[0]);
    coordinates[1][i] = Double.parseDouble(temp[1]);
  }

  // Get Volumes
  String volumesStr = (String) httpSession.getAttribute("volume");
  String[] tempVolumes = volumesStr.split("_");
  tempVolumes = Arrays.copyOfRange(tempVolumes,1,tempVolumes.length);
  double[] volumes = new double[tempVolumes.length];
  for (int i=0; i<tempVolumes.length; i++){
    volumes[i] = Double.parseDouble(tempVolumes[i]);
  }

  // Get Store index
  String storeStr = (String) httpSession.getAttribute("store");
  String[] tempStore = storeStr.split("_");
  tempStore = Arrays.copyOfRange(tempStore,1,tempStore.length);
  int[] store = new int[tempStore.length];
  int storeIndex = -1;
  for(int i=0; i<tempStore.length; i++) {
    store[i] = Integer.parseInt(tempStore[i]);
    if(store[i] == 1 && storeIndex == -1)
      storeIndex = i;
  }
  // Get Max Weight

  String maxWeightStr = (String) httpSession.getAttribute("weight");
  int maxWeight = Integer.parseInt(maxWeightStr);

  // Swap 0 volumes and coordinates with storeIndex
  if(storeIndex != 0){
    double temp = volumes[storeIndex];
    volumes[storeIndex] = volumes[0];
    volumes[0] = temp;

    double tempX = coordinates[0][storeIndex];
    double tempY = coordinates[1][storeIndex];
    coordinates[0][storeIndex] = coordinates[0][0];
    coordinates[1][storeIndex] = coordinates[1][0];
    coordinates[0][0] = tempX;
    coordinates[1][0] = tempY;
  }

  Cluster cluster = new Cluster(coordinates, volumes, maxWeight);
  double[][] afterClusterization = cluster.doClusterization();

  //Cast to int
  int[][] indexMatrix = new int[afterClusterization.length][afterClusterization[0].length];
  for (int i=0; i<afterClusterization.length; i++)
    for (int j=0; j<afterClusterization[0].length; j++)
      indexMatrix[i][j] = (int) afterClusterization[i][j];


    for(int i=0; i<afterClusterization.length; i++)
      for(int j=0; j<afterClusterization[0].length; j++){
        if(afterClusterization[i][j] == 0)
          afterClusterization[i][j] = storeIndex;
        else if(afterClusterization[i][j] == storeIndex)
          afterClusterization[i][j] = 0;
      }
    double temp = volumes[storeIndex];
    volumes[storeIndex] = volumes[0];
    volumes[0] = temp;

    double tempX = coordinates[0][storeIndex];
    double tempY = coordinates[1][storeIndex];
    coordinates[0][storeIndex] = coordinates[0][0];
    coordinates[1][storeIndex] = coordinates[1][0];
    coordinates[0][0] = tempX;
    coordinates[1][0] = tempY;

  for(int i=0; i<indexMatrix.length; i++)
    for (int j=0; j<indexMatrix[0].length; j++)
      if(indexMatrix[i][j] == 0) indexMatrix[i][j] = -1;


  for(int k=0; k<indexMatrix.length; k++){
    %>
<br>
<%
    for (int l=0; l<indexMatrix[0].length; l++){
  %>
<%=indexMatrix[k][l]%>
<%
    }
  }
  %>
<br>
<br>
<%
  /*
  0.0 7.0 2.0
  5.0 1.0 6.0
  4.0 3.0 -1.0
   */

  //TODO: Проверить, а работает ли вообще все это
  int[] townsInClusters = new int[indexMatrix.length];
  for(int i=0; i<indexMatrix.length; i++) {
    for (int j = 0; j < indexMatrix[0].length; j++){
      if(indexMatrix[i][j] != -1) townsInClusters[i]+=1;
    }
    townsInClusters[i]+=1;
  }

  String[] bestRoutes = new String[indexMatrix.length];
  String type = "duration";
  double totalLength = 0.0;
  DistanceMatrix dM = new DistanceMatrix();


  for(int i=0; i<indexMatrix.length; i++) {
    int nOfTowns = townsInClusters[i];

    double[][] subCoordinates = new double[2][nOfTowns];
    for (int j = 0; j < indexMatrix[i].length; j++) {
      int minusOneN = 0;
      if (indexMatrix[i][j] != -1) {
        subCoordinates[0][j - minusOneN] = coordinates[0][indexMatrix[i][j]];
        subCoordinates[1][j - minusOneN] = coordinates[1][indexMatrix[i][j]];
      } else {
        minusOneN++;
      }
    }
    subCoordinates[0][nOfTowns - 1] = coordinates[0][storeIndex];
    subCoordinates[1][nOfTowns - 1] = coordinates[1][storeIndex];
    double[][] distanceMatrix;
    distanceMatrix = dM.getDistanceMatrix(subCoordinates, type);
    AntColonyOptimization aco = new AntColonyOptimization(distanceMatrix);
    int[] bestRoute = aco.getBestRoute();
    totalLength += aco.getBestLength();
    bestRoutes[i] = Arrays.toString(bestRoute);
  }
    for(int k=0; k<bestRoutes.length; k++){

    %>
<%=bestRoutes[k]%>
<%
  }
%>

</body>
</html>