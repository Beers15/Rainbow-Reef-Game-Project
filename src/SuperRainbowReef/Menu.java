/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperRainbowReef;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.*;
import javax.swing.ImageIcon;

/**
 *
 * @author Ethan
 */
public class Menu {
    public Rectangle playButton = new Rectangle(480, 260, 121, 51);
    public Rectangle quitButton = new Rectangle(480, 330, 121, 51);
    
    Image imgBackground = new ImageIcon("src/RainbowReef Resources/Title.gif").getImage();
    Image imgStart = new ImageIcon("src/RainbowReef Resources/Button_start.gif").getImage();
    Image imgScores = new ImageIcon("src/RainbowReef Resources/Button_scores.gif").getImage();
    Image imgQuit = new ImageIcon("src/RainbowReef Resources/Button_quit.gif").getImage();
    
    public void render(Graphics g) {
        
        g.drawImage(imgBackground, 0, 20, 666, 500, null);
        Graphics2D g2d = (Graphics2D) g;
        
        Font fn = new Font("arial", Font.BOLD,50);
        //g.setFont(fn);
       // g.setColor(Color.blue);
        //g.drawString("Super Rainbow Reef", SuperRainbowReef.WIDTH / 4, 100);
        
        //Font fn1 = new Font("arial", Font.BOLD,30);
        //g.setFont(fn1);
        
        g.setColor(Color.white);
        g2d.draw(playButton);
        g.drawImage(imgStart, playButton.x , playButton.y, 120, 50, null);
      
       
        
         g.setColor(Color.white);
        g2d.draw(quitButton);
        g.drawImage(imgQuit, playButton.x, playButton.y + 70, 120, 50, null);
        
        Font fn2 = new Font("arial", Font.BOLD,15);
        g.setFont(fn2);
        g.drawString("Current High Scores",quitButton.x, quitButton.y + 90);
        g.drawString("__________________",quitButton.x, quitButton.y + 95);
       
       
        String fileName = "src/RainbowReef Resources/scores.txt";

        String line = null;

        try {
            
            FileReader fileReader = 
                new FileReader(fileName);

  
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            int i = quitButton.y + 115;
            while((line = bufferedReader.readLine()) != null) {
              
                g.drawString(line,quitButton.x, i);
                i += 20;
            }   
  
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            
        }
        
    }
        
}
