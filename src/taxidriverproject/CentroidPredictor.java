package taxidriverproject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import taxidriverproject.TaxiDriverProject.DataNode;


public class CentroidPredictor {
    DataNode SourceInfo;
    private ArrayList<DataPoint> centroids;
    private ArrayList<Integer> crowdAtEachCentroid;
    private ArrayList<Double> densityAtEachCentroid;
    private DataPoint preferredCentroid;
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
    DataPoint startExec()
    {   
        int pos = 0;
        double maximum = 0.0;
        for(int i = 0;i<centroids.size();i++)
        {
            MapNode node = new MapNode(SourceInfo.lat,SourceInfo.lon,centroids.get(i).lat,centroids.get(i).lon);
         
            MapEngine googleMaps = new MapEngine(node,0);
            //System.out.println(node);

            double prob_success = hypothesis(densityAtEachCentroid.get(i),crowdAtEachCentroid.get(i),googleMaps.getDistance(),googleMaps.getTime());
            if(prob_success>maximum)
            {
                maximum = prob_success;
                pos = i;
            }
        }
         preferredCentroid = centroids.get(pos);
        makeMap();
        return centroids.get(pos);
    }
    private void makeMap()
    {
        String U = "https://maps.googleapis.com/maps/api/staticmap?zoom=13&size=410x500&markers=color:blue%7Clabel:S%7C" +
         preferredCentroid.lat + "," + preferredCentroid.lon +
         "&markers=size:mid%7Ccolor:0xFFFF00%7Clabel:C%7C" +
         SourceInfo.lat + "," + SourceInfo.lon +
         "&key=AIzaSyD1cN1JPKEGxrbZ9qLyS_Eqsc-8AIwvHkQ";
        System.out.println(U);
        String dJSON = "";  
        System.out.println(SourceInfo);
        System.out.println(preferredCentroid);
        try
        {
            URL url = new URL(U);
            HttpURLConnection hps = (HttpURLConnection)url.openConnection();
          
            InputStream in = hps.getInputStream();
            FileOutputStream fwriter = new FileOutputStream(new File("img.jpeg"));
            int ch;
            while((ch=in.read())!=-1)
            {
                fwriter.write(ch);
                //dJSON = dJSON + (char)ch;
            }
            //System.out.println(dJSON);
            
            
            //Closing Connection
            hps.disconnect();
        
        }
        catch(Exception e)
        {}
    }
    private double hypothesis(double croudDensity,int crowd,double distance,double time)
    {
        double sucess =  10.0 * croudDensity + 5.0*crowd - 10.0*distance - 10.0*time; //preformance reward
        double prob = 1 - Math.exp(-sucess);                //logistic probabilty sigmoid function
        prob = 1.0/prob;
        return prob;
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
 