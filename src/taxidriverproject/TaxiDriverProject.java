/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxidriverproject;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Abhishek
 */
class DataNode
{
    double lat,time,lon;
    DataNode(){
        lat = time = lon =0.0;
    }
};
public class TaxiDriverProject {

    /**
     * @param args the command line arguments
     */
    ArrayList<DataNode>data = new ArrayList();
    int windowSize = 15;
    int dayTime = 1440;
    int noOfClusters = 12;
    void go() throws Exception
    {
        
        int i;
        DataNode temp;   
        KMeans Obj = new KMeans();
        ArrayList<DataPoint>custData,results;
        double time,lat,lon;
        /**
         * Load Data
         */
        String FileName = "D:\\TaxiDriverProject\\TaxiDriverProject\\Data.txt";
        Scanner freader = new Scanner(new File(FileName));
        
        while(freader.hasNextDouble())
        {
            temp = new DataNode();
            temp.time = freader.nextDouble();
            temp.lat = freader.nextDouble();
            temp.lon = freader.nextDouble();
            data.add(temp);
        }
        /** 
         * Take Input
         */
        Scanner in = new Scanner(System.in);
        
        time = in.nextDouble();
        lat = in.nextDouble();
        lon = in.nextDouble();
        custData = getCustData(time, data);
        results = Obj.KMeansAlgo(custData, noOfClusters);
        for(i=0;i<results.size();i++)
        {
           // System.out.println(results.get(i).lat + " " +  results.get(i).lon );
        }  
    }
    ArrayList<DataPoint>getCustData(double time,ArrayList<DataNode> data)
    {
        ArrayList<DataPoint> temp = new ArrayList();
        int i,pos = 0;
        for(i=0;i<data.size();i++)
        {
            if(data.get(i).time >=  time)
            {
                pos = i;
                break;
            }
        }
        i = pos;
        while(i < (pos + 1500))
        {
            i++;
            DataPoint D =  new DataPoint();
            D.lat = data.get(i%data.size()).lat;
            D.lon = data.get(i%data.size()).lon;
            temp.add(D);
            
        }
        i = pos;
        while(i > (pos - 500))
        {
            i--;
            DataPoint D = new DataPoint();
            D.lat = data.get((i+data.size())%data.size()).lat;
            D.lon = data.get((i+data.size())%data.size()).lon;
            temp.add(D);
        }
        return temp;
    }
    public static void main(String[] args) throws Exception {

        TaxiDriverProject Obj = new TaxiDriverProject();
        Obj.go();
        
    }
    
}
