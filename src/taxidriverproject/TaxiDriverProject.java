package taxidriverproject;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TaxiDriverProject {    
    ArrayList<DataNode> data = new ArrayList();
    
    private int windowSize = 15;
    private int dayTime = 1440;
    private int noOfClusters = 12;
    private int forwardWindowWidth = 1500;
    private int backwardWindowWidth = 500;
    private ArrayList<Integer> crowdAtEachCentroid;
    private ArrayList<Double> densityAtEachCentroid;
    ArrayList<DataPoint>custData,resultantCentroids;
    
    void go() throws Exception
    {
        DataNode temp;   
        KMeans Obj = new KMeans();
        int i;
        double time,lat,lon;
        
        //Load Data From DataSet
        String FileName = "Data.txt";
        Scanner freader = new Scanner(new File(FileName));
        while(freader.hasNextDouble())
        {
            temp = new DataNode();
            temp.time = freader.nextDouble();
            temp.lat = freader.nextDouble();
            temp.lon = freader.nextDouble();
            data.add(temp);
        }
        freader.close();
        //Data Loaded

        //user gps location and curr time input
        Scanner in = new Scanner(System.in);
        time = in.nextDouble();
        lat = in.nextDouble();
        lon = in.nextDouble();
        
        //filter the dataset(data Window)
        custData = getCustData(time, data);
        //filtering done
        
        //cluster the window
        resultantCentroids = Obj.KMeansAlgo(custData, noOfClusters);
        crowdAtEachCentroid = Obj.calculateCrowd();
        densityAtEachCentroid = Obj.calculateDensity();
        for(i=0;i<resultantCentroids.size();i++)
        {
           // System.out.println(results.get(i).lat + " " +  results.get(i).lon );
            System.out.println(crowdAtEachCentroid.get(i));
            System.out.println(densityAtEachCentroid.get(i));
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
        while(i < (pos + forwardWindowWidth))
        {
            i++;
            DataPoint D =  new DataPoint();
            D.lat = data.get(i%data.size()).lat;
            D.lon = data.get(i%data.size()).lon;
            temp.add(D);
        }
        i = pos;
        while(i > (pos - backwardWindowWidth))
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

        //TaxiDriverProject Obj = new TaxiDriverProject();
        //Obj.go();
        
        MapNode node = new MapNode(28.6797,77.0926,28.7129,77.1575);
        MapEngine googleMaps = new MapEngine(node,0);
        
        System.out.println(googleMaps.getDistance());
        System.out.println(googleMaps.getTime());
        
        
    }
    
    
    
    private class DataNode
    {
        double lat,time,lon;
        DataNode(){
            lat = time = lon =0.0;
        }
        @Override
        public String toString() {
            return "DataNode{" + "lat=" + lat + ", time=" + time + ", lon=" + lon + '}';
        }

    };
    
    
    //Utility Functions
    ArrayList<DataPoint> getCentroids()
    {
        return resultantCentroids;
    }
    
    ArrayList<Double> getDensityAtEachCentroid()
    {
        return densityAtEachCentroid;
    }
    ArrayList<Integer> getCrowdAtEachCentroid()
    {
        return crowdAtEachCentroid;
    }
    
}
