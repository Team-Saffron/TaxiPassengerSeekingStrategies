package taxidriverproject;

public class DataPoint {
    double lat,lon;
    
    DataPoint(){
        lat = lon = 0;
    }
    DataPoint(double x, double y)
    {
        lat = x;
        lon = y;
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
