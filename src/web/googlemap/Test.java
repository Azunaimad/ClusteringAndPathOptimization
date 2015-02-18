package web.googlemap;

import SweepAlgorithm.Cluster;

import java.util.Arrays;

/**
 * Created by Azunai on 17.02.2015.
 */
public class Test {

    public static void main(String[] args) {
        double[][] coordinates = {{1, 4, 7, 2, 9, 12}, {1, 4, 3, 12, 15, 5}};
        double[] need = {0, 1, 2, 1, 2, 1};
        double weight = 3;
        Cluster cluster = new Cluster(coordinates,need, weight);
        double[][] res = cluster.doClusterization();
        for(int i=0; i<res.length; i++) {
            System.out.println("");
            for (int j = 0; j < res[0].length; j++)
                System.out.print(res[i][j]+" ");
        }

        String test = Arrays.toString(need);
        System.out.println(test);

    }
}
