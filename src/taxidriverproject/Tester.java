/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxidriverproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import taxidriverproject.TaxiDriverProject.DataNode;

/**
 *
 * @author Abhishek
 */
public class Tester {
    String testFile;
    String testDay;
    ArrayList<DataNode> testData;
    int IS_TEST;
    void startTest() throws Exception
    {
        IS_TEST = 1;
        
        TaxiDriverProject TP =  new TaxiDriverProject();
        TP.setTestOutputFile();
        TP.setAsTest();
        for (DataNode temp : testData) {
            TP.setData(temp.time, temp.lat, temp.lon, testDay);
            TP.go();
            TP.execute();
        } 
        TP.closeOutputWriter();
    }
    Tester(Integer day) throws FileNotFoundException
    {
        TaxiDriverProject TP = new TaxiDriverProject();
        if(day == 0)
        {
            testFile = "o2.txt";
            testDay = "Sunday";
        }
        else
        {
            testFile = "o2.txt";
            testDay = "Monday";
        }
       testData = new ArrayList<>();
        DataNode temp;
        Scanner freader = new Scanner(new File(testFile));
      temp = TP.new DataNode();
        while(freader.hasNextDouble())
        { 
            temp.time = freader.nextDouble();
          //    System.out.println(temp.time);
            temp.lat = freader.nextDouble();
          //    System.out.println(temp.lat);
            temp.lon = freader.nextDouble();
           //   System.out.println(temp.lon);
            testData.add(temp);
        }
        
    }
}
