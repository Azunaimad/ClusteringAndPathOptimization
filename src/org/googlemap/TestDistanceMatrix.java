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
        double[][] expArray = {{10384.0, 86.0, 249.0},{245.0, 10384.0, 178.0}, {384.0, 164.0, 10384.0}};
        for(int i=0; i<res.length; i++)
            assertArrayEquals(expArray[i],res[i],0.000001);
    }

}
