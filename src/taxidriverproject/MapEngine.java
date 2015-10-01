
package taxidriverproject;


import java.net.*;
import java.io.*;

public class MapEngine {
    
    private double sLat,sLon,dLat,dLon;
    public static String distanceMatrixApi;
    
    MapEngine(double sLat,double sLon,double dLat,double dLon)
    {
        this.sLat = sLat;
        this.sLon = sLon;
        this.dLat = dLat;
        this.dLon = dLon;
  
         distanceMatrixApi = "https://maps.googleapis.com/maps/api/distancematrix/json?"
                + "origins="
                + sLat + "," + sLon
                + "&destinations="
                + dLat + "," + dLon
                + "&key=AIzaSyAQeBAutaj4zNav-fgtQnzCwBNpYtMag4A";
    }
    
     String requestJSON(String s)
    {
        String dJSON = "";
        try{
            
            URL url = new URL(distanceMatrixApi);
            HttpURLConnection hps = (HttpURLConnection)url.openConnection();
            
            InputStream in = hps.getInputStream();
            
            int ch;
            while((ch=in.read())!=-1)
                dJSON = dJSON + (char)ch;
            
            hps.disconnect();
        
        }catch(Exception e){}
        return dJSON;
    }
    /* private void parseJSON()
     {
         
     }
     double eDistance()
     {
         
     }
     double eTime()
     {
         
     }
     String polyline()
     {
         
     }
     String generateDirections()
     {
         
     }
     void saveImage()
     {
         
     }*/
}
