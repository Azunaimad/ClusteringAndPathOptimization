package web.googlemap;

import java.util.Arrays;

/**
 * Created by Azunai on 17.02.2015.
 */
public class Test {

    public static void main(String[] args) {
        String coordinatesStr = "_(59.923710640259905, 30.3057861328125)_(60.923710640259905, 35.3057861328125)_(57.923710640259905, 31.3057861328125)_";
        String volumesStr = "_0_100_150_";
        String storeStr = "_1_0_0_";

        String[] tempVolumes = volumesStr.split("_");
        String[] store = storeStr.split("_");
        String[] tempCoordinates = coordinatesStr.split("_");

        tempCoordinates = Arrays.copyOfRange(tempCoordinates,1,tempCoordinates.length);
        Double[][] coordinates = new Double[2][tempCoordinates.length];
        for(int i=0; i<tempCoordinates.length; i++){
            String[] temp;
            temp = (tempCoordinates[i].replaceAll("[^0-9. ]","")).split(" ");
            coordinates[0][i] = Double.parseDouble(temp[0]);
            coordinates[1][i] = Double.parseDouble(temp[1]);
        }

        tempVolumes = Arrays.copyOfRange(tempVolumes,1,tempVolumes.length);
        double[] volumes = new double[tempVolumes.length];
        for (int i=0; i<tempVolumes.length; i++){
            volumes[i] = Double.parseDouble(tempVolumes[i]);
        }

    }
}
