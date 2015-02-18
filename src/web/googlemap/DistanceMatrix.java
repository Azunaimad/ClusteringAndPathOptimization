package web.googlemap;

import org.json.JSONException;

import java.io.IOException;

public class DistanceMatrix extends Direction {

    int nOfTowns;
    double[][] coordinates;
    int[][] indexMatrix;
    int storeIndex;
    String type;

    public DistanceMatrix(int nOfTowns, double[][] coordinates, int[][] indexMatrix, int storeIndex, String type) {
        this.nOfTowns = nOfTowns;
        this.coordinates = coordinates;
        this.indexMatrix = indexMatrix;
        this.storeIndex = storeIndex;
        this.type = type;
    }

    public double[][] getDistanceMatrix(int currentIndexMatrixLine) throws JSONException, IOException {
        double[][] distanceMatrix = new double[nOfTowns][nOfTowns];

        Geodecoder geodecoder = new Geodecoder();

        for (int j=0; j < indexMatrix[0].length; j++){
            int index = indexMatrix[currentIndexMatrixLine][j];
            if(currentIndexMatrixLine == j) distanceMatrix[currentIndexMatrixLine][j] = 10000000.0;
            if(index != -1 && currentIndexMatrixLine != j){
                String storeCoords = coordinates[0][storeIndex] + "," + coordinates[1][storeIndex];
                String destination = coordinates[0][index] + "," + coordinates[1][index];
                distanceMatrix[currentIndexMatrixLine][j] = getDirectionLength(geodecoder.geodecode(storeCoords),
                        geodecoder.geodecode(destination),type);
                distanceMatrix[j][currentIndexMatrixLine] = getDirectionLength(geodecoder.geodecode(destination),
                        geodecoder.geodecode(storeCoords),type);

                for(int k=j+1; k< indexMatrix[0].length; k++) {
                    int nextIndex = indexMatrix[currentIndexMatrixLine][k];
                    if(nextIndex != -1) {
                        String origin = coordinates[0][nextIndex] + "," + coordinates[1][nextIndex];
                        distanceMatrix[currentIndexMatrixLine][k] = getDirectionLength(geodecoder.geodecode(destination),
                                geodecoder.geodecode(origin),type);
                        distanceMatrix[k][currentIndexMatrixLine] = getDirectionLength(geodecoder.geodecode(origin),
                                geodecoder.geodecode(destination),type);
                    }
                }
            }
        }
        return distanceMatrix;
    }
}
