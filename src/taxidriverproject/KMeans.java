package taxidriverproject;
import java.util.ArrayList;

public class KMeans {
    
    private ArrayList<DataPoint> centroids, data, tempCentroids;
    private ArrayList<Integer> clusterOf, tempClusterOf;
    private int clusters,samples;
    private int noOfIterations, noOfRandomInit;
    
    
    KMeans()
    {
        noOfRandomInit = 12;
        noOfIterations = 50;
    }
    
    public ArrayList<DataPoint> KMeansAlgo(ArrayList<DataPoint> x,int y)
    {
        data = x;           //dataset
        samples = x.size(); //number of datapoints
        clusters = y;       //number of clusters
        
        centroids = new ArrayList<>(); //cluster Centroid Positions
        clusterOf = new ArrayList<>(); //cluster assigned to the ith sample
        
        double minDist,curDist, minCost;
        minCost = Math.pow(2, 100);
       
        while(noOfRandomInit-- > 0)
        {   
            setCentroidsRandomly();    
            clusterOf = new ArrayList<>();
            for(int i=0;i<samples;i++)
                clusterOf.add(0);

            while(noOfIterations-- > 0)
            {
                //assignment of the cluster centroids
                for(int i=0;i<samples;i++)
                {
                    minDist = DataPoint.dist(centroids.get(0), data.get(i));
                    for(int j=1;j<clusters;j++)
                    {
                        curDist = DataPoint.dist(centroids.get(j),data.get(i)); 
                        if(curDist < minDist)
                        {
                            minDist = curDist;
                            clusterOf.set(i, j);
                        }
                    }
                }
                //assignment of the cluster centroids done
                //moving the cluster centroid to their new position
                for(int i=0;i<clusters;i++)
                {
                    DataPoint newCentroid = new DataPoint();
                    double counter = 0;
                    for(int j=0;j<samples;j++)
                    {
                        if(clusterOf.get(j) ==  i)
                        {
                            counter++;
                            newCentroid.lat = newCentroid.lat + data.get(j).lat;
                            newCentroid.lon = newCentroid.lon + data.get(j).lon;
                        }
                    }
                    newCentroid.lat = newCentroid.lat/counter;
                    newCentroid.lon = newCentroid.lon/counter;
                    centroids.set(i, newCentroid) ;
                }
                //cluster centroids updated


                //System.out.println(cost);   
            }
            double cost = costFunc(); 
            if(cost < minCost)
            {
                   tempCentroids = centroids;  //Temporary storing centroids and Clusterof
                   tempClusterOf = clusterOf;
                   minCost = cost;
            }
        }
        centroids = tempCentroids;
        clusterOf = tempClusterOf;
        return centroids;
    }
    
    ArrayList<Integer> calculateCrowd()
    {
        ArrayList<Integer> toReturn =  new ArrayList<>();
        int i;
        /**
         * Initialization
         */    
        for(i=0;i<clusters;i++)
        {
            toReturn.add(0);
        }   
        /**
         * Count Crowd at each Centroid
         */
        for(i=0;i<data.size();i++)
        {
            toReturn.set(clusterOf.get(i), toReturn.get(clusterOf.get(i))+1);
        }
        return toReturn;
    }
    ArrayList<Double> calculateDensity()
    {
        ArrayList<Double> toReturn = new ArrayList<>();
        ArrayList<Double> maxDist = new ArrayList<>();
        int i;
        
        //Initializetion
        for(i=0;i<clusters;i++)
        {
            toReturn.add(0.0);
            maxDist.add(0.0);
        }
        
        //Setting Distance of Furthest point in a Cluster
        for(i=0;i<samples;i++)
        {
            if(DataPoint.dist(data.get(i), centroids.get(clusterOf.get(i))) > maxDist.get(clusterOf.get(i)))
            {
                maxDist.set(clusterOf.get(i), DataPoint.dist(data.get(i), centroids.get(clusterOf.get(i))));
            }
        }
        
        //Calling findDensityUtility for finding density
        toReturn = findDensityUtility(maxDist);
        return toReturn;            
    }
    ArrayList<Double> findDensityUtility(ArrayList<Double> maxDist)
    {
        ArrayList<Double> toReturn = new ArrayList<>();
        double K = Math.pow(2, 20);
        ArrayList<Integer> crowdAtEachCentroid;
        int i;
        crowdAtEachCentroid = calculateCrowd();
        
        //Find Density and Multiply it with a constant
        
        for(i=0;i<clusters;i++)
        {
            double temp = (crowdAtEachCentroid.get(i)/maxDist.get(i)) *  K;
            toReturn.add(temp);
        }
        return toReturn;
    }
    private double costFunc()
    {
        double res = 0.0;
        for(int i=0;i<samples;i++)
            res+=(DataPoint.dist(data.get(i),centroids.get(clusterOf.get(i))))/10000000;
        return res;
    }
    private void setCentroidsRandomly()
    {
        centroids.clear();
        for(int i=0;i<clusters;i++)
            centroids.add(data.get((int)(Math.random()*samples)));
    }
}
