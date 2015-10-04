/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxidriverproject;

import java.awt.*;
import javax.swing.*;

public class ShowImage extends JPanel{
  
  Image image; 
  
  public ShowImage(){
   super();
   
   image = Toolkit.getDefaultToolkit().getImage("img.jpeg");
  }

  public void paintComponent(Graphics g){
   g.drawImage(image,0,0,640,400, this);
   
  }

  public static void displayMap(){
   JFrame frame = new JFrame("Map");
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.setBounds(50, 50, 640, 400);
   ShowImage panel = new ShowImage();
   frame.setContentPane(panel); 
   frame.setVisible(true); 
  }
}