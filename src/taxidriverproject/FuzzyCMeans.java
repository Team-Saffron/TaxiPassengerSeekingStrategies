package taxidriverproject;

import java.util.ArrayList;

public class FuzzyCMeans implements Clustering{

    private ArrayList<DataPoint> centroids,data;
    private int clusters,samples;
    private int noOfRandomInit;
    private Double[][] fuzzyMembership;
    
    FuzzyCMeans() {
        noOfRandomInit = 1;
    }
    
    @Override
    public ArrayList<DataPoint> doClustering(ArrayList<DataPoint> data, int clustersRequired) {
        
        this.data = data;
        samples = data.size();
        clusters = clustersRequired;
        ArrayList<DataPoint> newCentroids = new ArrayList<>();
        
        fuzzyMembership = new Double[samples][clusters];
        
        for(int i = 0;i<samples;i++)
                for(int j = 0;j<clusters;j++)
                        fuzzyMembership[i][j] = 0.0;
        
         while(noOfRandomInit-- > 0) {
             
              initialCentroids();
              
              //assign fuzzy membership
              for(int i = 0;i<samples;i++) {
                  for(int j = 0;j<clusters;j++) {
                        fuzzyMembership[i][j] = getFuzzyMembership(data.get(i),centroids.get(j));
                  }
              }
              
             newCentroids = getCentroidList();
          
         }
         return newCentroids;
    }
    
    //returns the new set of centroids adjusted after each iteration
    private ArrayList<DataPoint> getCentroidList() {
        
        ArrayList<DataPoint> result = new ArrayList<>();
        double sum;
        for(int j = 0;j<clusters;j++) {
            sum = 0.0;
            for(int i = 0;i<samples;i++)
                    sum = sum + fuzzyMembership[i][j];
            
            double x=0.0;
            double y=0.0;
            for(int i = 0;i<samples;i++) {
                x = x + data.get(i).lat * fuzzyMembership[i][j];
                y = y + data.get(i).lon * fuzzyMembership[i][j];
            }   
            result.add(new DataPoint(x,y));
        }
        return result;
    }

    @Override
    public ArrayList<Integer> calculateCrowd() {
        ArrayList<Integer> result = new ArrayList<>(); 
        for(int i =0;i<clusters;i++) {       
            double expectedCrowd = 0.0;
            for(int j = 0;j<samples;j++)
                 expectedCrowd = expectedCrowd + (fuzzyMembership[j][i]*1.0);
     
            result.add((int)expectedCrowd);
        }
        return result;
    }

    @Override
    public ArrayList<Double> calculateDensity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getCost() {
        double result = 0.0;
        for(int i = 0;i<samples;i++) {
            for(int j = 0;j<clusters;j++) {
                result = result + (fuzzyMembership[i][j]*1.0*DataPoint.dist(data.get(i), centroids.get(j)));
            }
        }
        return result;
    }
   
    private Double getFuzzyMembership(DataPoint x,DataPoint y) {
        double sum = 0.0;
        double dist1 = DataPoint.dist(x,y);
        for(int i = 0;i<clusters;i++) {
            double dist2 = DataPoint.dist(x, data.get(i));
            if(dist2 == 0.0)
                    return 0.0;
            sum = sum + (dist1 * 1.0)/dist2;
        }
        return (1.0/sum);
    }
    
    @Override
    public void initialCentroids()
    {
        centroids.clear();
        for(int i=0;i<clusters;i++)
            centroids.add(data.get((int)(i*10)));
    }
    
}
