/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxidriverproject;

import java.util.ArrayList;

/**
 *
 * @author Abhishek
 */

public class KMeans {
    ArrayList<DataPoint> centroids,Data;
    ArrayList<Integer>cluster;
    int clusters,dataPoints;

    
    public ArrayList<DataPoint> KMeansAlgo(ArrayList<DataPoint> X,int Y)
    {
        Data = X;
        dataPoints = X.size();
        clusters = Y;
        setCentroidsRandomly();
        /////////////////////////////////
        
        for(int i=0;i<dataPoints;i++)
            cluster.add(0);
        
        int noOfIteration = 100,i,j;
        double minDist,curDist;
        while(noOfIteration-- > 0)
        {
            for(i=0;i<dataPoints;i++)
            {
                minDist = Math.pow(2, 100);
                for(j=0;j<clusters;j++)
                {
                    curDist = dist(centroids.get(j),Data.get(i)); 
                    if(curDist < minDist)
                    {
                        minDist = curDist;
                        cluster.set(i, j);
                    }
                }
            }
            //Update Centroids to mean of DataPoints
            
            for(i=0;i<clusters;i++)
            {
                DataPoint newCentroid = new DataPoint();
                int counter = 0;
                for(j=0;j<dataPoints;j++)
                {
                    if(cluster.get(i) ==  j)
                    {
                        counter++;
                        newCentroid.lat = newCentroid.lat + Data.get(i).lat;
                        newCentroid.lon = newCentroid.lon + Data.get(i).lon;
                    }
                }
                newCentroid.lat = newCentroid.lat/counter;
                newCentroid.lon = newCentroid.lon/counter;
                centroids.set(i, newCentroid) ;
            }
        }
        
        
        
        return centroids;
    }
    double dist(DataPoint A,DataPoint B)
    {
       double res;
       res = Math.pow((A.lat - B.lat),2) + Math.pow(A.lon - B.lon,2);
       return res; 
    }
    public void setCentroidsRandomly()
    {
        int i;
        for(i=0;i<clusters;i++)
        {
            centroids.add(Data.get((int)(Math.random()*dataPoints)));
        }
    }
}
