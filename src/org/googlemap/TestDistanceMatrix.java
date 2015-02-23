package org.googlemap;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;


public class TestDistanceMatrix {
    @Test
    public void testGetDistanceMatrix() throws Exception {
        double[][] coordinates = {{59.92160287366319, 59.91942506602241, 59.924759096737795},
                                  {30.3592050075531, 30.353808403015137, 30.35644769668579}};
        DistanceMatrix distanceMatrix = new DistanceMatrix();
        double[][] res = distanceMatrix.getDistanceMatrix(coordinates,"duration");
        double[][] expArray = {{34849.0, 24849.0},{24689.0, 34849.0}};
        System.out.println(res.length);
        System.out.println(res[0].length);
        System.out.println(distanceMatrix.getUrl());
        for(int i=0; i<res.length; i++){
            System.out.println();
            for (int j=0; j<res[0].length; j++)
                System.out.print(res[i][j]+" ");
        }
        /*for(int i=0; i<res.length; i++)
            assertArrayEquals(expArray[i],res[i],0.000001);*/
    }
}
