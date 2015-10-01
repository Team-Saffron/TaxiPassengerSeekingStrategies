package taxidriverproject;
import java.util.ArrayList;

public class KMeans {
    
    private ArrayList<DataPoint> centroids,data;
    private ArrayList<Integer> cluster;
    private int clusters,samples;
    private int noOfIterations;
    
    KMeans()
    {
        noOfIterations = 100;
    }
    
    public ArrayList<DataPoint> KMeansAlgo(ArrayList<DataPoint> x,int y)
    {
        data = x;  //dataset
        samples = x.size(); //number of datapoints
        clusters = y; //number of clusters
        
        centroids = new ArrayList<DataPoint>(); //cluster Centroid Positions
        cluster = new ArrayList<Integer>(); //cluster assigned to the ith sample
        
        double minDist,curDist;
       
        setCentroidsRandomly();       
        for(int i=0;i<samples;i++)cluster.add(0);
 
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
                        cluster.set(i, j);
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
                    if(cluster.get(j) ==  i)
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
            
            double cost = costFunc(); 
            //System.out.println(cost);   
        }
        return centroids;
    }
    private double costFunc()
    {
        double res = 0.0;
        for(int i=0;i<samples;i++)
            res+=(DataPoint.dist(data.get(i),centroids.get(cluster.get(i))))/10000000;
        return res;
    }
    private void setCentroidsRandomly()
    {
        centroids.clear();
        for(int i=0;i<clusters;i++)
            centroids.add(data.get((int)(Math.random()*samples)));
    }
}
