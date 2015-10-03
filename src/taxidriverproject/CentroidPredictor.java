/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxidriverproject;

import java.util.ArrayList;
import taxidriverproject.TaxiDriverProject.DataNode;

/**
 *
 * @author Abhishek
 */
public class CentroidPredictor {
    DataNode SourceInfo;
    private ArrayList<DataPoint> centroids;
    private ArrayList<Integer> crowdAtEachCentroid;
    private ArrayList<Double> densityAtEachCentroid;
    CentroidPredictor(TaxiDriverProject T)
    {
        SourceInfo = T.getSourceInfo();
        centroids = parseLatLon(T.getCentroids());
        crowdAtEachCentroid = T.getCrowdAtEachCentroid();
        densityAtEachCentroid = T.getDensityAtEachCentroid();   
    }
    CentroidPredictor()
    {
       
    }
    void startExec()
    {
        int i=0;
        for(i=0;i<centroids.size();i++)
        {
            MapNode node = new MapNode(centroids.get(i).lat,centroids.get(i).lon,centroids.get(0).lat,centroids.get(0).lon);
            MapEngine googleMaps = new MapEngine(node,0); 
            System.out.println(googleMaps.getSource());
        }
    }
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
    
}
