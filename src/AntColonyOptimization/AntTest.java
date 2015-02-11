package AntColonyOptimization;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AntTest{
    private double[][] distanceMatrix = {{1000, 30, 40, 50},
                                         {24, 1000, 75, 64},
                                         {42, 45, 1000, 13},
                                         {48, 98, 32, 1000}};


    private int numberOfTowns = 4;
    public AntTest(){

    }

    @Test
    public void testSwapTowns(){
        int[] testRoute = {2, 0, 3, 1, 2};
        Ant ant = new Ant(numberOfTowns, distanceMatrix);
        ant.antRoute = testRoute;
        ant.swapTowns(1, 2);
        int expVal = 3;
        assertEquals(expVal,ant.antRoute[1]);
        expVal = 0;
        assertEquals(expVal,ant.antRoute[2]);
    }


    @Test
    public void testCountNewTownIndex(){
        Ant ant = new Ant(numberOfTowns, distanceMatrix);
        double[] probabilityDistribution = {0.1, 0.2, 0.3};
        double happenedProbability = 0.25;
        int expVal = 2;
        int result = ant.countNewTownIndex(probabilityDistribution, happenedProbability);
        assertEquals(expVal,result);
    }

    @Test
    public void testCountProbabilityDitribution(){
        double[][] pheromonesMatrix = { {0.0, 0.1, 0.1},
                                        {0.1, 0.0, 0.1},
                                        {0.1, 0.1, 0.0} };
        double[][] visibilityMatrix = { {0.0 , 0.05, 0.03},
                                        {0.04, 0.0 , 0.02},
                                        {0.06, 0.03 , 0.0}};
        int currentTown = 0;
        int[] nextTowns = {2, 1};
        double pheromonesPower = 1.0;
        double visibilityPower = 2.0;

        Ant ant = new Ant(numberOfTowns, distanceMatrix);

        double[] result = ant.countProbabilityDistribution(pheromonesMatrix,
                visibilityMatrix,currentTown,nextTowns,
                pheromonesPower,visibilityPower);

        double[] expDistr = {0.00009/0.00034, 0.00025/0.00034};
        assertArrayEquals(expDistr,result,0.00000001);
    }

    @Test
    public void testMakeInitialAntRoute(){
        Ant ant = new Ant(numberOfTowns,distanceMatrix);
        ant.makeInitialAntRoute();

        int expVal=5;
        assertEquals(expVal,ant.antRoute.length);

        //Test that route's end equals start
        assertEquals(ant.antRoute[0],ant.antRoute[numberOfTowns]);

        //Test that ant.Route contains all Towns
        for(int i=0; i<numberOfTowns; i++) {
            boolean test = ArrayUtils.contains(ant.antRoute,i);
            assertEquals(true,test);
        }
    }

    @Test
    public void testGetRouteLength(){
        Ant ant = new Ant(numberOfTowns, distanceMatrix);
        ant.antRoute = new int[]{2, 0, 3, 1, 2};
        double result = ant.getRouteLength(distanceMatrix);
        double expVal = 265.0;
        assertEquals(expVal,result,0.0);

    }
}
