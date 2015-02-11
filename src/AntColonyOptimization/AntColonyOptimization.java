package AntColonyOptimization;


public class AntColonyOptimization {

    private int numberOfTowns;
    private int numberOfAnts;


    private int numberOfIter;
    private int numberOfEliteAnts;
    private double pheromonesPower;
    private double visibilityPower;
    private double pheromonesEvaporation;
    private double[][] distanceMatrix;
    private double[][] pheromonesMatrix;
    private double[][] visibilityMatrix;
    private double qParameter;

    private Ant[] ants;

    private int[] bestRoute;
    private int bestAntRouteIndex;
    private double bestLength;

    public AntColonyOptimization(int numberOfAnts, int numberOfEliteAnts, int numberOfIter,
                                 double pheromonesPower, double visibilityPower,
                                 double pheromonesEvaporation, double[][] distanceMatrix,
                                 double[][] pheromonesMatrix, double qParameter) {
        this.numberOfAnts = numberOfAnts;
        this.numberOfEliteAnts = numberOfEliteAnts;
        this.numberOfIter = numberOfIter;
        this.pheromonesPower = pheromonesPower;
        this.visibilityPower = visibilityPower;
        this.pheromonesEvaporation = pheromonesEvaporation;
        this.distanceMatrix = distanceMatrix;
        this.pheromonesMatrix = pheromonesMatrix;
        this.qParameter = qParameter;
        this.numberOfTowns = distanceMatrix[0].length;

        this.ants = new Ant[numberOfAnts];
        for (int i=0; i<numberOfAnts; i++){
            this.ants[i] = new Ant(numberOfTowns, distanceMatrix);
        }

        this.visibilityMatrix = new double[distanceMatrix.length][distanceMatrix[0].length];
        for(int i=0; i<distanceMatrix.length;i++)
            for(int j=0; j<distanceMatrix[0].length;j++)
                this.visibilityMatrix[i][j] = 1.0/distanceMatrix[i][j];

        this.bestLength = 9999999.0;
        this.bestRoute = new int[numberOfTowns+1];
    }

    protected void countBestLength(){
        for (int i=0; i<numberOfAnts; i++) {
            double antRouteLength = ants[i].getRouteLength(distanceMatrix);
            if (antRouteLength < bestLength){
                bestLength = antRouteLength;
                bestAntRouteIndex = i;
            }
        }
    }
    
    public double getBestLength(){
        return bestLength;
    }

    public int[] getBestRoute(){
        bestRoute = ants[bestAntRouteIndex].antRoute;
        return bestRoute;
    }



}
