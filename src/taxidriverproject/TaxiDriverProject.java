package taxidriverproject;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class DataNode
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
public class TaxiDriverProject {
    
    ArrayList<DataNode> data = new ArrayList();
    
    private int windowSize = 15;
    private int dayTime = 1440;
    private int noOfClusters = 12;
    private int forwardWindowWidth = 1500;
    private int backwardWindowWidth = 1500;
    
    void go() throws Exception
    {
        DataNode temp;   
        KMeans Obj = new KMeans();
        
        ArrayList<DataPoint>custData,results;
        double time,lat,lon;
        
        //input from the dataset
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
        //input from the dataset finished

        //user gps location and curr time input
        Scanner in = new Scanner(System.in);
        time = in.nextDouble();
        lat = in.nextDouble();
        lon = in.nextDouble();
        
        //filter the dataset(data Window)
        custData = getCustData(time, data);
        //filtering done
        
        //cluster the window
        results = Obj.KMeansAlgo(custData, noOfClusters);
  
        for(int i=0;i<results.size();i++)
           System.out.println(results.get(i));
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

        TaxiDriverProject Obj = new TaxiDriverProject();
        Obj.go();   
    }
    
}
