package taxidriverproject;

import java.util.ArrayList;
import taxidriverproject.TaxiDriverProject.DataNode;


public class CentroidPredictor {
    DataNode SourceInfo;
    private ArrayList<DataPoint> centroids;
    private ArrayList<Double> crowdAtEachCentroid;
    private ArrayList<Double> densityAtEachCentroid, distanceFromSource, timeFromSource;
    private DataPoint preferredCentroid;
    
    CentroidPredictor(TaxiDriverProject T)
    {
        SourceInfo = T.getSourceInfo();
        centroids = parseLatLon(T.getCentroids());
        crowdAtEachCentroid = normaliseCrowd(T.getCrowdAtEachCentroid());
        densityAtEachCentroid = normaliseDouble(T.getDensityAtEachCentroid());   
    }
    CentroidPredictor()
    {
       
    }
    DataPoint startExec()
    {  
         int pos = 0;
        try{
       
        double maximum = 0.0;
        distanceFromSource = new ArrayList<>();
        timeFromSource = new ArrayList<>();
        
        
        // Extract Distance and Time
        for(int i = 0;i<centroids.size();i++)
        {
            MapNode node = new MapNode(SourceInfo.lat,SourceInfo.lon,centroids.get(i).lat,centroids.get(i).lon); 
            MapEngine googleMaps = new MapEngine(node);
            distanceFromSource.add(googleMaps.getDistance());
            timeFromSource.add(googleMaps.getTime());
        }
        
        //Normalise Distance and Time
        distanceFromSource = normaliseDouble(distanceFromSource);
        timeFromSource = normaliseDouble(timeFromSource);
        
        //Predict the best Centroid
        for(int i=0;i<centroids.size();i++)
        { 
            double prob_success = hypothesis(densityAtEachCentroid.get(i),crowdAtEachCentroid.get(i),distanceFromSource.get(i),timeFromSource.get(i));
            if(prob_success>maximum)
            {
                maximum = prob_success;
                pos = i;
            }
        }
        
        //return it
        preferredCentroid = centroids.get(pos);
        }
        catch(Exception E)
        {
            System.out.println(E);
        }
        return centroids.get(pos);
    }

    private double hypothesis(double crowdDensity,double crowd,double distance,double time)
    {
        double success =  (crowdDensity * crowd+1)/(distance * time+1);
        System.out.println(crowdDensity + " " + crowd + " " +distance + " "+ time);
        return success;
    }
    
    
    /**
     * Utility Functions
     */
    private ArrayList<DataPoint> parseLatLon(ArrayList<DataPoint> L)
    {
        double divisor = 1000000.0;
        ArrayList<DataPoint> res = new ArrayList<>();
        DataPoint temp;
        for(int i=0;i<L.size();i++)
        {
            temp = L.get(i);
            temp.lat = (temp.lat/divisor) + 37;
            temp.lon = -1 * ((temp.lon/divisor) + 122);
            res.add(temp);
        }
        return res;
    }
    private ArrayList<Double> normaliseCrowd(ArrayList<Integer> C)
    {
        ArrayList<Double> res = new ArrayList<>();
        double min = 1000, max = 0, temp;
        for(int i=0;i<C.size();i++)
        {
            min = Math.min(min, C.get(i));
            max = Math.max(max, C.get(i)); 
        }
        for(int i=0;i<C.size();i++)
        {
            temp = (C.get(i)- min)/(max-min);
            res.add(temp);
        }
        return res;
    }
    private ArrayList<Double> normaliseDouble(ArrayList<Double> C)
    {
        ArrayList<Double> res = new ArrayList<>();
        double min = Math.pow(2, 100), max = 0, temp;
        for(int i=0;i<C.size();i++)
        {
            min = Math.min(min, C.get(i));
            max = Math.max(max, C.get(i)); 
        }
        for(int i=0;i<C.size();i++)
        {
            temp = (C.get(i)- min)/(max-min);
            res.add(temp);
        }
        return res;
    }
    
}
 