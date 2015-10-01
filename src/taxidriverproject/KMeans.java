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
        /**
         * Variable Declarations
         */
        Data = X;
        dataPoints = X.size();
        clusters = Y;
        centroids = new ArrayList<DataPoint>();
        cluster = new ArrayList<Integer>();
        int noOfIteration = 100,i,j;
        double minDist,curDist;
        
        /**
         * Initialization
         */
        setCentroidsRandomly();
        
        for(i=0;i<dataPoints;i++)
            cluster.add(0);
        /**
         * K - Means Algorithm
         */
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
            /**
             * Update Centroids to mean of DataPoints
             */
            for(i=0;i<clusters;i++)
            {
                DataPoint newCentroid = new DataPoint();
                double counter = 0;
                for(j=0;j<dataPoints;j++)
                {
                    if(cluster.get(j) ==  i)
                    {
                        counter++;
                        newCentroid.lat = newCentroid.lat + Data.get(j).lat;
                        newCentroid.lon = newCentroid.lon + Data.get(j).lon;
                    }
                }
                newCentroid.lat = newCentroid.lat/counter;
                newCentroid.lon = newCentroid.lon/counter;
                centroids.set(i, newCentroid) ;
            }
            
            
            /**
             * Evaluate Cost Function
             */
            
            double cost = costFunc(); 
            System.out.println(cost/10000000);   
        }
        return centroids;
    }
    double costFunc()
    {
        double res = 0.0;
        int i;
        for(i=0;i<dataPoints;i++)
        {
            res = res + (dist(Data.get(i),centroids.get(cluster.get(i))));
        }
        return res;
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
