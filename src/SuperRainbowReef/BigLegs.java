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

public class BigLegs extends Entity {

    private int blockType;

    public BigLegs(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        
        bounds.setSize(40,40);
        
        try {
            image = ImageIO.read(new File("src/RainbowReef Resources/BigLeg_small.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }      
    }

    public String toString() {
        return "BigLegs";
    }
}
