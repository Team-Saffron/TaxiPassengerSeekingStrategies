package taxidriverproject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class TaxiDriverProject {
    
    //Variable Declarations
    ArrayList<DataNode> data ;
    private double lat, lon, time;
    private String day;
    private int testID = 1;
    private int windowSize = 15;
    private int dayTime = 1440;
    private int noOfClusters = 12;
    private int forwardWindowWidth = 1500;
    private int backwardWindowWidth = 500;
    private ArrayList<Integer> crowdAtEachCentroid;
    private ArrayList<Double> densityAtEachCentroid;
    ArrayList<DataPoint> custData,resultantCentroids;
    private boolean IS_TEST = false; 
    PrintWriter testWriter; 
    //Variable Declaration Ended
    
    void go() throws Exception
    {
        data = new ArrayList();
        DataNode temp;   
        
        int i;
        
        //Load Data From DataSet
        String FileName;
        if(day.charAt(0) == 'S')            //If day is a weekend
        {
            System.out.println("here");
            FileName = "src/DataFiles/weekend.txt";
        }
        else                                //If day is WeekDay
        {
            FileName = "src/DataFiles/weekday.txt";
        }
        
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
        System.out.print("Data Loaded");
        System.out.println(lat + " " + lon + " " +data.size()); 
    }
    void execute() throws Exception
    {
        System.out.println(lat + " " + lon + " " +data.size());
        KMeans Obj = new KMeans();
        //filter the dataset(data Window)*/
        custData = getCustData(time, data);
        //filtering done
        
        //Calculate cluster centroids, Crowd, Density at each centroids
        resultantCentroids = Obj.KMeansAlgo(custData, noOfClusters);
        writeResultantCentroid();
        crowdAtEachCentroid = Obj.calculateCrowd();
        densityAtEachCentroid = Obj.calculateDensity();
  
        //Call Predictor
        CentroidPredictor CP = new CentroidPredictor(this);
        DataPoint prediction = CP.startExec();
        
        
        MapEngine googleMaps = new MapEngine(new MapNode(new DataPoint(lat,lon),prediction));
      
        
        if(IS_TEST)
        {   
            testWriter.println(testID + " " +googleMaps.getTime() + " " + googleMaps.getDistance());
            testID = testID + 1;
        }
        else
        {
            System.out.println(googleMaps.getDestination());
            System.out.println(googleMaps.getSource());
            System.out.println(googleMaps.getDistance());
            googleMaps.makeMap();
            String uri = googleMaps.displayMap();
            OutputFrame output = new OutputFrame(uri,googleMaps.getDestination(), googleMaps.getDistance(), googleMaps.getTime());
        }
    }
    private ArrayList<DataPoint>getCustData(double time,ArrayList<DataNode> data)
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
    
    public static void main(String[] args) throws Exception 
    {
       // new InputWindow().initialize();
        
        
        /*
        For Testing
        */
        
        Tester T = new Tester(1);   //1 For WeekDay, 0 for WeekEnd
        T.startTest();
       
        /*MapNode node = new MapNode(28.6797,77.0926,28.7129,77.1575);
        MapEngine googleMaps = new MapEngine(node);
        
        System.out.println(googleMaps.getSource());
        googleMaps.makeMap();*/
        
    }
    
    public class DataNode
    {
        double lat,time,lon;
        DataNode(){
            lat = time = lon =0.0;
        }
        DataNode(double x,double y,double z)
        {
            lat = x;
            lon = y;
            time = z;
        }
        @Override
        public String toString() 
        {
            return "DataNode{" + "lat=" + lat + ", time=" + time + ", lon=" + lon + '}';
        }

    };
    
    void writeResultantCentroid()
    {
         
    }

    //Interfacing Functions
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
    DataNode getSourceInfo()
    {
        return new DataNode(lat, lon, time);
    }
    
    public void setData(double t,double lt,double ln,String D)
    {
        time = t;
        lat = lt;
        lon = ln;
        day = D;
    }
    public void setAsTest()
    {
        IS_TEST = true;
    }
    public void setTestOutputFile() 
    {
        try {
            testWriter = new PrintWriter("testResults.txt", "UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TaxiDriverProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TaxiDriverProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void closeOutputWriter() 
    {
        testWriter.close();
    }
    
}
