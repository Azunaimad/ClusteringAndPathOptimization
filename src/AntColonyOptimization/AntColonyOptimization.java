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

    protected Ant[] ants;

    private int bestAntRouteIndex;
    private double bestLength;


    /**
     * Constructor
     * @param distanceMatrix - Matrix (numberOfTowns x numberOfTowns)with distances from i-th town
     *                       to j-th town, i,j = 0,1,...,numberOfTowns
     * Other parameters is given
     */
    public AntColonyOptimization(double[][] distanceMatrix){
        this.distanceMatrix = distanceMatrix;
        numberOfAnts = distanceMatrix.length;
        numberOfEliteAnts = 0;
        numberOfTowns = distanceMatrix.length;

        numberOfIter = 100;
        pheromonesPower = 1;
        visibilityPower = 2;
        pheromonesEvaporation = 0.5;



        this.ants = new Ant[numberOfAnts];
        for (int i=0; i<numberOfAnts; i++){
            this.ants[i] = new Ant(numberOfTowns, distanceMatrix);
        }

        pheromonesMatrix = new double[distanceMatrix.length][distanceMatrix[0].length];
        this.visibilityMatrix = new double[distanceMatrix.length][distanceMatrix[0].length];
        for(int i=0; i<distanceMatrix.length;i++)
            for(int j=0; j<distanceMatrix[0].length;j++){
                this.visibilityMatrix[i][j] = 1.0/distanceMatrix[i][j];
                pheromonesMatrix[i][j] = 0.1;
            }

        qParameter = ants[0].getRouteLength(distanceMatrix);
        this.bestLength = 9999999.0;
    }


    /**
     * Constructor
     * @param numberOfAnts - number of ants
     * @param numberOfEliteAnts - number of elite ants, which leaves special - strong pheromones
     * @param numberOfIter - number of iterations. For example, if numberOfIterations = 100 than
     *                     100 times ants will leave pheromones and try to find better route using
     *                     that pheromones distribution
     * @param pheromonesPower - parameter, which sets pheromones trail weight
     * @param visibilityPower - parameter, which sets visibility weight
     * @param pheromonesEvaporation - evaporation of the old pheromones, which helps algorithm
     *                              to avoid the convergence to a locally optimal solution
     * @param distanceMatrix - Matrix (numberOfTowns x numberOfTowns)with distances from i-th town
     *                       to j-th town, i,j = 0,1,...,numberOfTowns
     * @param pheromonesMatrix - Matrix (numberOfTowns x numberOfTowns) with initial
     *                         distribution of pheromones
     * @param qParameter - parameter, which is the same order as the optimal route length
     */
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

    /**
     * Find current best length
     */
    protected void countBestLength(){
        for (int i=0; i<numberOfAnts; i++) {
            double antRouteLength = ants[i].getRouteLength(distanceMatrix);
            if (antRouteLength < bestLength){
                bestLength = antRouteLength;
                bestAntRouteIndex = i;
            }
        }
    }

    /**
     * Count new pheromonesMatrix after renewing ants routes
     */
    protected void countNewPheromones(){
        double [][] simpleAntPher = new double[numberOfTowns][numberOfTowns];
        double [][] eliteAntPher = new double[numberOfTowns][numberOfTowns];

        //Count simple ants pheromones
        for (int i=0; i<numberOfAnts; i++)
            for(int j=0; j<numberOfTowns; j++){
                simpleAntPher[ants[i].antRoute[j]][ants[i].antRoute[j+1]] +=
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
                pheromonesMatrix[i][j] = (1-pheromonesEvaporation)*pheromonesMatrix[i][j] +
                        simpleAntPher[i][j] + numberOfEliteAnts*eliteAntPher[i][j];
    }


    /**
     * Main method, which find optimal path
     */
    public void findOptimalPath(){
        for(int i=0; i<numberOfIter; i++){
            for(int j=0; j<numberOfAnts; j++)
                ants[j].makeNewAntRoute(pheromonesMatrix, visibilityMatrix,
                        pheromonesPower, visibilityPower);
            countNewPheromones();
        }
    }

    public double getBestLength(){
        return bestLength;
    }

    public int[] getBestRoute(){
        return ants[bestAntRouteIndex].antRoute;
    }

    public double[][] getPheromonesMatrix() {
        return pheromonesMatrix;
    }

    public void setPheromonesMatrix(double[][] pheromonesMatrix){
        this.pheromonesMatrix = pheromonesMatrix;
    }

    public void setQParameter(double qParameter) {
        this.qParameter = qParameter;
    }

    public void setPheromonesPower(double pheromonesPower) {
        this.pheromonesPower = pheromonesPower;
    }

    public void setVisibilityPower(double visibilityPower) {
        this.visibilityPower = visibilityPower;
    }

    public void setPheromonesEvaporation(double pheromonesEvaporation) {
        this.pheromonesEvaporation = pheromonesEvaporation;
    }

    public void setDistanceMatrix(double[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public void setNumberOfIter(int numberOfIter) {
        this.numberOfIter = numberOfIter;
    }

    public void setNumberOfAnts(int numberOfAnts) {
        this.numberOfAnts = numberOfAnts;
    }

    public void setNumberOfTowns(int numberOfTowns) {
        this.numberOfTowns = numberOfTowns;
    }

    public void setNumberOfEliteAnts(int numberOfEliteAnts) {
        this.numberOfEliteAnts = numberOfEliteAnts;
    }

}
