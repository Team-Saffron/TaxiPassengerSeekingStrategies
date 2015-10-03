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
    
    MapEngine(MapNode m,int type)
    {
        //type 0 distance matrix api
         String link = Utility.getURl(m, type);
         this.type = type;
         Obj =  Utility.requestJSON(link);
         time = -1;
         dist = -1;
         
         extractInfo();
    }
    
    void extractInfo()
    {
        switch(type)
        {
            case 0:
                getTimeAndDistance();
                break;
        }
    }
    private void getTimeAndDistance()
    {
        
        JSONArray rows = (JSONArray)Obj.get("rows");
        
        JSONObject elements = (JSONObject)rows.get(0);
        JSONArray innerElements  = (JSONArray)elements.get("elements");
        JSONObject aux = (JSONObject)innerElements.get(0);
 
        JSONObject duration = (JSONObject)aux.get("duration");
        JSONObject distance = (JSONObject)aux.get("distance");
        
        time = Double.parseDouble(duration.get("value").toString())/60;
        dist= Double.parseDouble(distance.get("value").toString());
    }
    
    double getTime()
    {
        return time;
    }
    
    double getDistance()
    {
        return dist;
    }
  
}
