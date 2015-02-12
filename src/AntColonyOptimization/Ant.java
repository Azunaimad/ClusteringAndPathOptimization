package AntColonyOptimization;

import java.util.Random;

public class Ant {

    private int[] antRoute;
    private int numberOfTowns;
    private double routeLength;

    /**
     * Constructor for Ant. Create Ant, create random initial route and count its length
     * @param numberOfTowns - number of Towns, which Ant should to visit.
     *                      If numberOfTowns=4, then Ant should visit Towns={0,1,2,3}
     * @param distanceMatrix - matrix with distances from i to j town.
     */
    public Ant(int numberOfTowns, double[][] distanceMatrix){
        this.numberOfTowns = numberOfTowns;
        this.antRoute = new int[numberOfTowns+1];
        makeInitialAntRoute();
        routeLength = getRouteLength(distanceMatrix);
    }

    /**
     * Find index for new town, which we should swap with old town to minimize route length
     * @param probabilityDistribution - distribution, which we could get using countProbabilityDistribution
     * @param happenedProbability - realization of the random variable, which helps us to find
     *                            which route we should to choose
     * @return new town index
     */
    protected int countNewTownIndex(double[] probabilityDistribution, double happenedProbability){
        int newTownIndex = 0;
        double probSum = 0;
        for(int j=0; j<probabilityDistribution.length; j++){
            probSum += probabilityDistribution[j];
            if (happenedProbability < probSum){
                newTownIndex = j - probabilityDistribution.length + numberOfTowns;
                break;
            }
        }
        return newTownIndex;
    }

    /**
     * Count current probability distribution, which is needed to find next town for the Ant
     * @param pheromonesMatrix - Matrix (numberOfTowns x numberOfTowns) with current pheromones
     * @param visibilityMatrix - Matrix (numberOfTowns x numberOfTowns) with visibility
     * @param currentTown - town where the Ant is now
     * @param nextTowns - towns, which the Ant could visit
     * @param pheromonesPower - parameter
     * @param visibilityPower - parameter
     * @return current probability distribution, which is necessary to find next town for the Ant
     * using makeNewAntRoute method
     */
    protected double [] countProbabilityDistribution(double[][] pheromonesMatrix,
                                                   double[][] visibilityMatrix,
                                                   int currentTown,
                                                   int[] nextTowns,
                                                   double pheromonesPower,
                                                   double visibilityPower){
        double[] Probability = new double[nextTowns.length];
        double[] requiredPheromones = new double[nextTowns.length];
        double[] requiredVisibility = new double[nextTowns.length];
        double sum=0.0;
        double[] temp = new double[nextTowns.length];


        for(int i=0; i<nextTowns.length;i++){
            requiredPheromones[i] = Math.pow(pheromonesMatrix[currentTown][nextTowns[i]],
                                            pheromonesPower);
            requiredVisibility[i] = Math.pow(visibilityMatrix[currentTown][nextTowns[i]],
                                            visibilityPower);
            temp[i] = requiredPheromones[i]*requiredVisibility[i];
            sum += temp[i];
        }

        for(int i=0; i<nextTowns.length; i++)
            Probability[i] = temp[i] / sum;

        return Probability;
    }

    /**
     * Create initial random route, which contains all towns from 0 to numberOfTowns
     * Example: let numberOfTowns = 4, then antRoute = {1, 3, 2, 0, 1}
     */
    protected void makeInitialAntRoute(){
        Random random = new Random();
        for(int j=0; j<numberOfTowns; j++) antRoute[j] = j;

        for(int j=0; j<numberOfTowns; j++){
            int ran = j + random.nextInt (numberOfTowns-j);
            swapTowns(ran, j);
        }
        antRoute[numberOfTowns] = antRoute[0];
    }

    /**
     * Create new Ant's route using pheromones and visibility Matrix
     * @param pheromonesMatrix - Matrix (numberOfTowns x numberOfTowns) with current pheromones
     * @param visibilityMatrix - Matrix (numberOfTowns x numberOfTowns) with visibility
     * @param pheromonesPower - parameter
     * @param visibilityPower - parameter
     */
    protected void makeNewAntRoute(double[][] pheromonesMatrix, double[][] visibilityMatrix,
                                   double pheromonesPower, double visibilityPower){

        for(int i=1; i<numberOfTowns-1; i++){
            int currentTown = antRoute[i-1];
            int[] nextTowns = new int[numberOfTowns - i];
            //Get ant next towns
            for(int j = i; j<numberOfTowns; j++)
                nextTowns[j-i] = antRoute[j];

            double[] probabilityDistribution = countProbabilityDistribution(pheromonesMatrix,
                    visibilityMatrix, currentTown, nextTowns, pheromonesPower, visibilityPower);

            double happenedProbability = (new Random()).nextDouble();
            int newTownIndex = countNewTownIndex(probabilityDistribution, happenedProbability);
            swapTowns(i, newTownIndex);
        }
    }

    /**
     * Swap 2 towns in antRoute
     * @param oldTownIndex - antRoute's index
     * @param newTownIndex - antRoute's index
     */
    protected void swapTowns(int oldTownIndex, int newTownIndex){
        int temp = antRoute[newTownIndex];
        antRoute[newTownIndex] = antRoute[oldTownIndex];
        antRoute[oldTownIndex] = temp;
    }

    public double getRouteLength(double[][] distanceMatrix){
        routeLength = 0;
        for(int i=0; i<distanceMatrix[0].length; i++)
                routeLength += distanceMatrix[antRoute[i]][antRoute[i+1]];
        return routeLength;
    }

    public int[] getAntRoute() {
        return antRoute;
    }

    public void setAntRoute(int[] antRoute) {
        this.antRoute = antRoute;
    }

    public void setNumberOfTowns(int numberOfTowns) {
        this.numberOfTowns = numberOfTowns;
    }
}
