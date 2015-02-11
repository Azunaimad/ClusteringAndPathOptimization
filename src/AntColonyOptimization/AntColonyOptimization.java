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

    protected void countNewPheromones(){
        double [][] simpleAntPher = new double[numberOfTowns][numberOfTowns];
        double [][] eliteAntPher = new double[numberOfTowns][numberOfTowns];

        //Count simple ants pheromones
        for (int i=0; i<numberOfAnts; i++)
            for(int j=0; j<numberOfTowns; j++){
                simpleAntPher[ants[i].antRoute[j]][ants[i].antRoute[j+1]] =
                        qParameter / ants[i].getRouteLength(distanceMatrix);
            }

        //Count elite ants pheromones
        countBestLength();
        for(int i=0; i<numberOfTowns; i++)
            eliteAntPher[ants[bestAntRouteIndex].antRoute[i]]
                    [ants[bestAntRouteIndex].antRoute[i+1]] = qParameter / bestLength;


        //Update pheromones Matrix
        for(int i=0; i<numberOfTowns; i++)
            for(int j=0; j<numberOfTowns; j++)
                pheromonesMatrix[i][j] = (1-pheromonesEvaporation)*simpleAntPher[i][j] +
                        numberOfEliteAnts*eliteAntPher[i][j];
    }

    public double getBestLength(){
        return bestLength;
    }

    public int[] getBestRoute(){
        return ants[bestAntRouteIndex].antRoute;
    }

    public void findOptimalPath(){
        for(int i=0; i<numberOfIter; i++){
            for(int j=0; j<numberOfAnts; j++)
                ants[j].makeNewAntRoute(pheromonesMatrix, visibilityMatrix,
                        pheromonesPower, visibilityPower);
            countNewPheromones();
        }
    }


}
