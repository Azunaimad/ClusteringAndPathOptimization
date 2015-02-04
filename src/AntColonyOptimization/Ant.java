package AntColonyOptimization;

public class Ant {

    protected int[] antRoute;
    protected int numberOfTowns;


    public Ant(){
        /* TODO */
    }

    protected void makeInitialAntRoute(){
        /* TODO */
    }

    protected void makeNewAntRoute(double[][] pheromonesMatrix, double[][] visibilityMatrix){
        /* TODO */
    }

    private int countNewTownIndex(double[] probabilityDistribution, double happenedProbability){
        int newTownIndex = 0;
        /* TODO */
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

    private void swapTowns(int oldTownIndex, int newTownIndex){
        /* TODO */
    }

}
