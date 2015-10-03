package taxidriverproject;import java.net.*;
import java.io.*;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 

class MapNode
{
    double sLat,sLon,dLat,dLon;
    MapNode(double sLat,double sLon,double dLat,double dLon)
    {
        this.sLat = sLat;
        this.sLon = sLon;
        this.dLat = dLat;
        this.dLon = dLon;
    }
}

public class MapEngine {
    
    private JSONObject Obj;
    private int type;
    private double time,dist;
    private String srcAddress, destAddress;
    MapEngine(MapNode m,int type)
    {
        //Type 0: Distance matrix api
        
         this.type = type;
         Obj =  Utility.requestJSON(m, type);
         time = -1;
         dist = -1;
         setInfo();
    }
  
    private void setInfo()
    {
        switch(type)
        {
            case 0:
                setTimeAndDistance();
                break;
        }
    }
    private void setTimeAndDistance()
    {
        JSONArray destination_address = (JSONArray)Obj.get("destination_addresses");
        JSONArray origin_address = (JSONArray)Obj.get("origin_addresses");
        JSONArray rows = (JSONArray)Obj.get("rows");
        srcAddress = (String) origin_address.get(0);
        destAddress = (String) destination_address.get(0);
        
        JSONObject elements = (JSONObject)rows.get(0);
        JSONArray innerElements  = (JSONArray)elements.get("elements");
        JSONObject aux = (JSONObject)innerElements.get(0);
 
        JSONObject duration = (JSONObject)aux.get("duration");
        JSONObject distance = (JSONObject)aux.get("distance");
      
        
        time = Double.parseDouble(duration.get("value").toString())/60;
        dist= Double.parseDouble(distance.get("value").toString());
    }
    
    
    //InterFacing Functions
    
    public double getTime()
    {
        return time;
    }
    
    public double getDistance()
    {
        return dist;
    }
    public String getSource()
    {
        return srcAddress;
    }
    public String getDestination()
    {
        return destAddress;
    }
  
}
