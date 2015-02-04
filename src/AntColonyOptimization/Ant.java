package AntColonyOptimization;

import java.util.Random;

public class Ant {

    protected int[] antRoute;
    protected int numberOfTowns;


    public Ant(){
        /* TODO */
    }

    private int countNewTownIndex(double[] probabilityDistribution, double happenedProbability){
        int newTownIndex = 0;
        int probSum = 0;
        for(int j=0; j<probabilityDistribution.length; j++){
            probSum+= probabilityDistribution[j];
            if (happenedProbability<probSum){
                // newTownIndex = i-1+j = j - (numberOfTowns - i) + numberOfTowns - 1 =
                // = j - nextTowns.length + numberOfTowns - 1;
                // nextTowns.length = probabilityDistribution.length
                newTownIndex = j - probabilityDistribution.length + numberOfTowns - 1;
                break;
            }
        }
        return newTownIndex;
    }

    private double [] countProbabilityDistribution(double[][] pheromonesMatrix,
                                                   double[][] visibilityMatrix,
                                                   int currentTown,
                                                   int[] nextTowns){
        double[] Probability = new double[nextTowns.length];
        /* TODO */
        return Probability;
    }

    protected void makeInitialAntRoute(){
        /* TODO */
    }

    protected void makeNewAntRoute(double[][] pheromonesMatrix, double[][] visibilityMatrix){
        for(int i=1; i<numberOfTowns-1; i++){
            int currentTown = antRoute[i-1];
            int[] nextTowns = new int[numberOfTowns - i];
            //Get ant next towns
            System.arraycopy(antRoute, i, nextTowns, i, numberOfTowns - i);
            double[] probabilityDistribution = countProbabilityDistribution(pheromonesMatrix,
                    visibilityMatrix, currentTown, nextTowns);
            double happenedProbability = (new Random()).nextDouble();
            int newTownIndex = countNewTownIndex(probabilityDistribution, happenedProbability);
            swapTowns(i, newTownIndex);
        }
    }

    private void swapTowns(int oldTownIndex, int newTownIndex){
        int temp = antRoute[newTownIndex];
        antRoute[newTownIndex] = antRoute[oldTownIndex];
        antRoute[oldTownIndex] = temp;
    }

}
