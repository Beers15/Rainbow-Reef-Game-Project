/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperRainbowReef;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 *
 * @author Alexander Beers
 */
public class Entity extends Observable {

    protected BufferedImage image;
    protected double xCoordinate;
    protected double yCoordinate;
    protected int angle = 0;
    protected Rectangle bounds = new Rectangle(0, 0, 40, 20);
    protected boolean empty = false;

    public Entity() {
    }

    protected double getXCoordinate() {
        return xCoordinate;
    }

    protected double getYCoordinate() {
        return yCoordinate;
    }

    protected BufferedImage getImage() {
        return image;
    }

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }

    //for collision checking
    public boolean collisionChk(Entity pop) {
        return bounds.intersects(pop.bounds);
    }

    public void initBounds() {
      bounds.setLocation((int)xCoordinate, (int)yCoordinate);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getAngle() {
        return angle;
    }
    
    public boolean isEmpty(){
        return empty;
    }
}

