package taxidriverproject;

import java.util.ArrayList;

public class FuzzyCMeans implements Clustering{

    private ArrayList<DataPoint> centroids,data;
    private int clusters,samples;
    private int noOfRandomInit;
    private Double[][] fuzzyMembership;
    
    FuzzyCMeans() {
        noOfRandomInit = 30;
    }
    
    @Override
    public ArrayList<DataPoint> doClustering(ArrayList<DataPoint> data, int clustersRequired) {
        
        double currCost = Double.MAX_VALUE;            
        this.data = data;
        samples = data.size();
        clusters = clustersRequired;

        centroids = new ArrayList<>(); //cluster Centroid Positions
        
        ArrayList<DataPoint> newCentroids = new ArrayList<>();

        fuzzyMembership = new Double[samples][clusters];
        initialCentroids();
          
         while(noOfRandomInit-- > 0) {
       
            for(int i = 0;i<samples;i++)
                    for(int j = 0;j<clusters;j++)
                            fuzzyMembership[i][j] = 0.0;
            
              //assign fuzzy membership
              for(int i = 0;i<samples;i++) {
                  for(int j = 0;j<clusters;j++) {
                        fuzzyMembership[i][j] = getFuzzyMembership(data.get(i),centroids.get(j));
                  }
              }
              
             newCentroids = getCentroidList();
             centroids = newCentroids;
         }
         
         
         return centroids;
    }
    
    //returns the new set of centroids adjusted after each iteration
    private ArrayList<DataPoint> getCentroidList() {
        
        ArrayList<DataPoint> result = new ArrayList<>();
        double sum;
        for(int j = 0;j<clusters;j++) {
            sum = 0.0;
            for(int i = 0;i<samples;i++)
                    sum = sum + fuzzyMembership[i][j] * fuzzyMembership[i][j]*1.0;
            
            double x=0.0;
            double y=0.0;
            for(int i = 0;i<samples;i++) {
                x = x + data.get(i).lat * fuzzyMembership[i][j] * fuzzyMembership[i][j] * 1.0;
                y = y + data.get(i).lon * fuzzyMembership[i][j] * fuzzyMembership[i][j] * 1.0;
            }   
            
            x/=(sum+1.0);
            y/=(sum+1.0);
            
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
    
    
    public ArrayList<Double> maxExpectedDistanceOfAClusterRegion() {
        
        ArrayList<Double> result = new ArrayList();
        
        for(int i =0;i<clusters;i++) {       
            double expectedMaxDist = 0.0;
            for(int j = 0;j<samples;j++)
                 expectedMaxDist = expectedMaxDist + (fuzzyMembership[j][i]*1.0*DataPoint.dist(centroids.get(i), data.get(j)));
     
            result.add(expectedMaxDist);
        }
        return result;
    }

    @Override
    public ArrayList<Double> calculateDensity() {
        
        //ecpected crowd by sqauare radius
        
        ArrayList<Integer> crowd = calculateCrowd();
        ArrayList<Double> maxDist = maxExpectedDistanceOfAClusterRegion();
        
        
        ArrayList<Double> result = new ArrayList();
        
        for(int i = 0 ;i<clusters;i++) {
            
            double temp = crowd.get(i)/(maxDist.get(i) * maxDist.get(i) *1.0);
            result.add(temp);
        }
        
        return result;
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
            
            dist2 = dist2 * dist2 * 1.0;
            dist2 = Math.exp(-dist2);
            
            sum = sum + dist2;
        }
        return ((dist1+1.0)/(sum+1.0));
    }
    
    @Override
    public void initialCentroids()
    {
        centroids.clear();
        for(int i=0;i<clusters;i++)
            centroids.add(data.get((int)(i*10)));
    }
}
