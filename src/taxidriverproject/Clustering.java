package taxidriverproject;

import java.util.ArrayList;

public interface Clustering {
    
     public ArrayList<DataPoint> doClustering(ArrayList<DataPoint> data,int clustersRequired);
     public ArrayList<Integer> calculateCrowd();
     public ArrayList<Double> calculateDensity();
     public double getCost();
     public void initialCentroids();
     
}
