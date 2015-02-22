package org.googlemap;

import org.junit.*;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by Azunai on 22.02.2015.
 */
public class TestDistanceMatrix {
    @org.junit.Test
    public void testGetDistanceMatrix() throws Exception {
        int nOfTowns = 2;
        double[][] coordinates = {{59.923710640259905,55.923710640259905}, {30.3057861328125, 30.3057861328125}};
        int[][] indexMatrix = {{0, 1, 2}};
        int storeIndex = 0;
        DistanceMatrix distanceMatrix = new DistanceMatrix();
        double[][] res = distanceMatrix.getDistanceMatrix(coordinates,"duration");
        double[][] expArray = {{34849.0, 24849.0},{24689.0, 34849.0}};
        for(int i=0; i<res.length; i++)
            assertArrayEquals(expArray[i],res[i],0.000001);

    }
}
