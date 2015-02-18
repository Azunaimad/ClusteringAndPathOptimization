package SweepAlgorithm;

public class Cluster {

    double[][] coordinates;
    double[] need;
    double maxWeight;


    public Cluster(double[][] coordinates, double[] need, double maxWeight) {
        this.coordinates = coordinates;
        this.need = need;
        this.maxWeight = maxWeight;
    }

    public double[][] doClusterization(){
        double[] xcity=coordinates[0];
        double[] ycity=coordinates[1];
        int Ncity=need.length;
        double[] polar=new double[Ncity];
        double[] tang = new double[Ncity];
        for (int i=1;i<Ncity; i++)
        {
            tang[i] = (ycity[i] - ycity[0])/(xcity[i] - xcity[0]);
            polar[i] = Math.atan(tang[i]);
        }

        double min = polar[0];
        int ind = 0;
        int[] cityn = new int[Ncity];

        for (int j=0; j<Ncity;j++) {
            for (int i = 0; i < polar.length; i++) {
                if (min > polar[i]) {
                    min = polar[i];
                    ind = i;
                }
            }
            polar[ind]=10000.0;
            min=10000.0;
            cityn[j]=ind;

        }

        double totalneed=0.0;

        for (int k=0; k<Ncity;k++) {
            totalneed=totalneed+need[k];
        }

        double nroutes=Math.ceil(totalneed / maxWeight);

        int breakcityn=0;
        int[] breakM=new int[need.length];

        for (int w=0;w<Ncity;w++) {
            double sum=0.0;
            for (int q = breakcityn; q < Ncity; q++) {
                sum = need[cityn[q]] + sum;
                if (sum > maxWeight) {
                    breakcityn = q;
                    break;
                }
            }
            breakM[w]=breakcityn;
            if (breakcityn==Ncity-1)  break;

        }

        int counter = 0;
        for( int i=1; i<breakM.length; i++ ) {
            if( breakM[i] != 0 ) {
                if (breakM[i] != breakM[i - 1]) {
                    counter++;
                }
            }
        }

        counter=counter+1;

        int width=breakM[0];
        for (int r=0;r<counter-1;r++) {
            if (width<breakM[r+1]-breakM[r]) {
                width=breakM[r+1]-breakM[r];
            }
        }

        if (Ncity-breakM[counter-1]>width) {
            width=Ncity-breakM[counter-1];
        };



        double[][] m=new double[counter+1][width];

        for (int i=0;i<counter+1;i++) {
            for (int j=0;j<width;j++) {
                m[i][j]=-1.0;
            }
        }

        for (int i=0;i<breakM[0];i++){
            m[0][i]=cityn[i];
        }

        for (int i=1;i<counter;i++){
            for (int j=breakM[i-1];j<breakM[i];j++){
                m[i][j-breakM[i-1]]=cityn[j];

            }
        }

        for (int j=breakM[counter-1];j<Ncity;j++){
            m[counter][j-breakM[counter-1]]=cityn[j];
        }

        return m;
    }
}
