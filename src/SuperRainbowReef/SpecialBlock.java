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

public class SpecialBlock extends Entity {

    private int blockType;
    private int durability = 2;

    public SpecialBlock(int xCoordinate, int yCoordinate, int blockType) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.blockType = blockType;

        if (blockType == 1) {
            try {
                image = ImageIO.read(new File("src/RainbowReef Resources/Block_Solid.gif"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }  
        else if (blockType == 2) {
            try {
                image = ImageIO.read(new File("src/RainbowReef Resources/Block_Life.gif"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (blockType == 3) {
            try {
                image = ImageIO.read(new File("src/RainbowReef Resources/Block_Split.gif"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (blockType == 4) {
            try {
                image = ImageIO.read(new File("src/RainbowReef Resources/Block7.gif"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String toString() {
        String type = "";
        if(blockType == 1)
            type = "unbreakable";
        if(blockType == 2)
            type = "life";
        if(blockType == 3)
            type = "points";
        if(blockType == 4)
            type = "double";
        return "Special block type: " + type;
    }
    
    public void damageDouble() {
        durability--;
    }
    
    public int chkDurability() {
       return durability;
    }
}
