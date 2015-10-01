/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxidriverproject;

/**
 *
 * @author Abhishek
 */
public class DataPoint {
    double lat,lon;
    
    DataPoint(){
        lat = lon = 0;
    }
    public static double dist(DataPoint a,DataPoint b)
    {
       double res;
       res = Math.pow((a.lat - b.lat),2) + Math.pow(a.lon - b.lon,2);
       return res;
    }

    @Override
    public String toString() {
        return "DataPoint{" + "lat=" + lat + ", lon=" + lon + '}';
    }
    
};
