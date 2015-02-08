package AntColonyOptimization;

import java.util.Random;

public class Ant {

    protected int[] antRoute;
    protected int numberOfTowns;


    public Ant(int numberOfTowns){
        this.numberOfTowns = numberOfTowns;
    }

    protected int countNewTownIndex(double[] probabilityDistribution, double happenedProbability){
        int newTownIndex = 0;
        double probSum = 0;
        for(int j=0; j<probabilityDistribution.length; j++){
            probSum+= probabilityDistribution[j];
            if (happenedProbability<probSum){
                // newTownIndex = i-1+j = j - (numberOfTowns - i) + numberOfTowns - 1 =
                // = j - nextTowns.length + numberOfTowns - 1;
                // nextTowns.length = probabilityDistribution.length
                newTownIndex = j - probabilityDistribution.length + numberOfTowns;
                System.out.println(newTownIndex);
                break;
            }
        }
        return newTownIndex;
    }

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

    protected void makeInitialAntRoute(){
        Random random = new Random();
        for(int j=0; j<numberOfTowns; j++) antRoute[j] = j;

        for(int j=0; j<numberOfTowns; j++){
            int ran = j + random.nextInt (numberOfTowns-j);
            swapTowns(ran, j);
        }
    }

    protected void makeNewAntRoute(double[][] pheromonesMatrix, double[][] visibilityMatrix,
                                   double pheromonesPower, double visibilityPower){
        for(int i=1; i<numberOfTowns-2; i++){
            int currentTown = antRoute[i-1];
            int[] nextTowns = new int[numberOfTowns - i];
            //Get ant next towns
            for(int j = i; j<numberOfTowns-1; j++)
                nextTowns[j] = antRoute[j];
            double[] probabilityDistribution = countProbabilityDistribution(pheromonesMatrix,
                    visibilityMatrix, currentTown, nextTowns, pheromonesPower, visibilityPower);
            double happenedProbability = (new Random()).nextDouble();
            int newTownIndex = countNewTownIndex(probabilityDistribution, happenedProbability);
            swapTowns(i, newTownIndex);
        }
    }

    protected void swapTowns(int oldTownIndex, int newTownIndex){
        int temp = antRoute[newTownIndex];
        antRoute[newTownIndex] = antRoute[oldTownIndex];
        antRoute[oldTownIndex] = temp;
    }

}
