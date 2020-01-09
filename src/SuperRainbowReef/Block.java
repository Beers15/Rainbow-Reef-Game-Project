/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperRainbowReef;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Alexander Beers
 */

public class Block extends Entity {

    private int blockType;

    public Block(int xCoordinate, int yCoordinate, int blockType) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.blockType = blockType;
        
        //load in correct cosmetic block image based on an integer representation in .txt file 
        if (blockType == 1) {
            try {
                image = ImageIO.read(new File("src/RainbowReef Resources/Block1.gif"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } 
        else if (blockType == 2) {
            try {
                image = ImageIO.read(new File("src/RainbowReef Resources/Block2.gif"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (blockType == 3) {
            try {
                image = ImageIO.read(new File("src/RainbowReef Resources/Block3.gif"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (blockType == 4) {
            try {
                image = ImageIO.read(new File("src/RainbowReef Resources/Block4.gif"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (blockType == 5) {
            try {
                image = ImageIO.read(new File("src/RainbowReef Resources/Block5.gif"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (blockType == 6) {
            try {
                image = ImageIO.read(new File("src/RainbowReef Resources/Block6.gif"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
      
    }

    public String toString() {
        return "Block Type " + blockType;
    }
}
