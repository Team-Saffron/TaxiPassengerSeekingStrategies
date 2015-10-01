/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxidriverproject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Abhishek
 */
public class JK {
     public static String mapDisplay = "https://m...content-available-to-author-only...s.com/maps/api/staticmap?center=37.405,-122.08&zoom=13&size=640x400&path=weight:3%7Ccolor:blue%7Cenc:aofcFz_bhVJ[n@ZpAp@t@b@uA`FuAzEoCdJiDpLs@VM@y@s@oBcBkAw@cCoAuBu@eEaAiAa@iAi@w@a@o@g@g@k@e@u@uAaCc@i@w@y@eAo@i@UaBc@kAGo@@]JyKA}EC{G?q@?IGKCeGA{CAyCAyEAwEBaFAkJ?yGEyAIiLAiB?{@BcBJ}@@aBGwBEo@A@j@BjBFTHjEl@fOD`C?|@RARAJERWPL@FE^S`AI`A&key=AIzaSyDbiWFfBDoZlFGvI1TPGBxLhj45z6ydp_U";
    
    void displayMap()
    {
        String imgData = "";
        try
        {
            URL url = new URL(mapDisplay);
            HttpURLConnection huc=(HttpURLConnection)url.openConnection();
            
            InputStream in = huc.getInputStream();
            int ch;
            while((ch=in.read())!=-1)
            {
                imgData = imgData + (char)ch;
            }
            System.out.println(imgData);
            huc.disconnect();
            
        }catch(Exception e)
        {
            
        }
    }
    
    public static void main(String[] args)throws Exception {
        // TODO code application logic here
        JK k = new JK();
        k.displayMap();
        
    }
   
}
