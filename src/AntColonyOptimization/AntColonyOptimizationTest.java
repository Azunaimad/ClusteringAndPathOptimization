package AntColonyOptimization;


import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AntColonyOptimizationTest {
    private double[][] distanceMatrix = {{10000, 17, 36, 14, 34, 17},
                                        {17, 10000, 32, 44, 43, 31},
                                        {10, 34, 10000, 10, 46, 49},
                                        {25, 31, 33, 10000, 44, 36},
                                        {48, 36, 33, 43, 10000, 34},
                                        {39, 37, 26, 14, 18, 10000}};

    private double[][] pheromonesMatrix = {{0.0, 0.1, 0.1, 0.1, 0.1, 0.1},
                                           {0.1, 0.0, 0.1, 0.1, 0.1, 0.1},
                                           {0.1, 0.1, 0.0, 0.1, 0.1, 0.1},
                                           {0.1, 0.1, 0.1, 0.0, 0.1, 0.1},
                                           {0.1, 0.1, 0.1, 0.1, 0.0, 0.1},
                                           {0.1, 0.1, 0.1, 0.1, 0.1, 0.0}};

    private int numberOfTowns = 6;
    private int numberOfAnts = 3;
    private int numberOfEliteAnts = 1;
    private int numberOfIter = 100;
    private double pheromonesPower = 1.0;
    private double visibilityPower = 2.0;
    private double pheromonesEvaporation = 0.5;
    private double qParameter = 60.0;


    @Test
    public void testCountBestLength(){

        AntColonyOptimization aco = new AntColonyOptimization(numberOfAnts,numberOfEliteAnts,
                numberOfIter,pheromonesPower, visibilityPower, pheromonesEvaporation,
                distanceMatrix, pheromonesMatrix, qParameter);

        aco.ants[0].antRoute = new int[]{0, 1, 2, 3, 4, 5, 0}; //len = 176
        aco.ants[1].antRoute = new int[]{1, 2, 3, 5, 4, 0, 1}; //len = 161
        aco.ants[2].antRoute = new int[]{2, 3, 1, 0, 5, 4, 2}; //len = 126

        aco.countBestLength();
        double result = aco.getBestLength();
        double expVal = 126.0;
        assertEquals(expVal,result,0.0);
    }

    @Test
    public void testCountNewPheromones(){

        AntColonyOptimization aco = new AntColonyOptimization(numberOfAnts,numberOfEliteAnts,
                numberOfIter,pheromonesPower, visibilityPower, pheromonesEvaporation,
                distanceMatrix, pheromonesMatrix, qParameter);

        aco.ants[0].antRoute = new int[]{0, 1, 2, 3, 4, 5, 0}; //len = 176
        aco.ants[1].antRoute = new int[]{1, 2, 3, 5, 4, 0, 1}; //len = 161
        aco.ants[2].antRoute = new int[]{2, 3, 1, 0, 5, 4, 2}; //len = 126

        aco.countNewPheromones();
        double[][] result = aco.getPheromonesMatrix();

        double[][] expArray = {{0.0, 0.7635799, 0.05, 0.05, 0.05, 1.002381},
                               {1.002381, 0.0, 0.7635799, 0.05, 0.05, 0.05},
                               {0.05, 0.05, 0.0, 1.7159609, 0.05, 0.05},
                               {0.05, 1.002381, 0.05, 0.0, 0.3909091, 0.4226708},
                               {0.4226708, 0.05, 1.002381, 0.05, 0.0, 0.3909091},
                               {0.3909091, 0.05, 0.05, 0.05, 1.3750518,0.0}};

        for(int i=0; i<result.length; i++)
            assertArrayEquals(expArray[i],result[i],0.000001);


    }

    @Test
    public void testFindOptimalPath(){
        AntColonyOptimization aco = new AntColonyOptimization(numberOfAnts,numberOfEliteAnts,
                numberOfIter,pheromonesPower, visibilityPower, pheromonesEvaporation,
                distanceMatrix, pheromonesMatrix, qParameter);

        double result = 200.0;
        double expVal = 126.0;
        for(int i=0; i<10; i++){
            aco.findOptimalPath();
            double temp = aco.getBestLength();
            if (temp<result) result = temp;
        }

        assertEquals(expVal, result, 8.0);

        int[] route = aco.getBestRoute();
        for(int i=0; i<numberOfTowns; i++) {
            boolean test = ArrayUtils.contains(route, i);
            assertEquals(true,test);
        }
    }
}
