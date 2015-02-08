package AntColonyOptimization;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AntTest{
    public AntTest(){

    }

    @Test
    public void testSwapTowns(){
        int numberOfTowns = 3;
        int[] testRoute = {3, 1, 2, 3};
        Ant ant = new Ant(numberOfTowns);
        ant.antRoute = testRoute;
        ant.swapTowns(1, 2);
        int expVal = 2;
        assertEquals(expVal,ant.antRoute[1]);
        expVal = 1;
        assertEquals(expVal,ant.antRoute[2]);
    }


    @Test
    public void testCountNewTownIndex(){
        int numberOfTowns = 6;
        Ant ant = new Ant(numberOfTowns);
        double[] probabilityDistribution = {0.1, 0.2, 0.3, 0.4};
        double happenedProbability = 0.35;
        int expVal = 4;
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
        int numberOfTowns = 3;

        Ant ant = new Ant(numberOfTowns);

        double[] result = ant.countProbabilityDistribution(pheromonesMatrix,
                visibilityMatrix,currentTown,nextTowns,
                pheromonesPower,visibilityPower);

        double[] expDistr = {0.00009/0.00034, 0.00025/0.00034};
        assertArrayEquals(expDistr,result,0.00000001);
    }

    @Test
    public void testMakeInitialAntRoute(){
        int numberOfTowns = 10;
        Ant ant = new Ant(numberOfTowns);
        ant.makeInitialAntRoute();

        int expVal=11;
        assertEquals(expVal,ant.antRoute.length);

        //Test that route's end equals start
        assertEquals(ant.antRoute[0],ant.antRoute[numberOfTowns]);

        //Test that ant.Route contains all Towns
        for(int i=0; i<numberOfTowns; i++) {
            boolean test = ArrayUtils.contains(ant.antRoute,i);
            assertEquals(true,test);
        }
    }
}
