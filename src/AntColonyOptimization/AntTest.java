package AntColonyOptimization;

import org.junit.Test;

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
}
