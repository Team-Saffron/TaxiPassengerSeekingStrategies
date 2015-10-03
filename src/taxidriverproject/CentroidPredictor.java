package taxidriverproject;
import java.util.ArrayList;
import taxidriverproject.TaxiDriverProject.DataNode;


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
        return centroids.get(pos);
    }
    
    private double hypothesis(double croudDensity,int croud,double distance,double time)
    {
        double sucess =  10.0 * croudDensity + 5.0*croud - 10.0*distance - 10.0*time; //preformance reward
        double prob = 1 - Math.exp(-sucess); //logistic probabilty sigmoid function
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
 