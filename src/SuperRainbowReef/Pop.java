/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperRainbowReef;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Alexander Beers
 */
public class Pop extends Entity {

    private double xVelocity;
    private double yVelocity;
    private boolean startPosition = true;
    private int oldXCoord;
    private int oldYCoord;
    private double hitCount = 1.0;
    private boolean updateSpeed = false;
    private double speedInc;
    private boolean lifeLost = false;

    public Pop(int xCoordinate, int yCoordinate, int angle) {
        this.angle = angle;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.oldXCoord = xCoordinate;
        this.oldYCoord = yCoordinate;
        xVelocity = 1.0;//Math.round(Math.cos(Math.toRadians(angle)));
        yVelocity = 1.0;//Math.round(Math.sin(Math.toRadians(angle)));

        try {
            image = ImageIO.read(new File("src/RainbowReef Resources/pop.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        bounds.setSize(30, 30);
    }

    public BufferedImage getImage() {
        return image;
    }

    public String toString() {
        return "Pop";
    }

    public void updatePop() {
        //xVelocity = Math.round(8 * Math.cos(Math.toRadians(angle))) / popSpeed;
        //yVelocity = Math.round(8 * Math.sin(Math.toRadians(angle))) / popSpeed;
        if (updateSpeed && Math.abs(xVelocity) < 3.0 && Math.abs(yVelocity) < 4.0) {
            System.out.println(" Velocity: " + xVelocity);
            if (xVelocity < 0) {
                xVelocity += speedInc;
            } else {
                xVelocity -= speedInc;
            }
            if (yVelocity < 0) {
                yVelocity += speedInc;
            } else {
                yVelocity -= speedInc;
            }

            speedInc = hitCount / 20.0;

            if (xVelocity < 0) {
                xVelocity -= speedInc;
            } else {
                xVelocity += speedInc;
            }
            if (yVelocity < 0) {
                yVelocity -= speedInc;
            } else {
                yVelocity += speedInc;
            }

            updateSpeed = false;
        }

        xCoordinate += xVelocity;
        yCoordinate += yVelocity;
        checkBorder();
        bounds.setLocation((int) xCoordinate, (int) yCoordinate);
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    private void checkBorder() {
        if (xCoordinate >= 626) {
            System.out.println("Life lost from touching X Right");
            lifeLost = true;
        }
        if (xCoordinate <= 20) {
            System.out.println("Life lost from touching X Left");
            lifeLost = true;
        }
        if (yCoordinate >= 530) {
            lifeLost = true;
        }
        if (yCoordinate <= 5) {
            System.out.println("Life lost from touching Y Top");
            lifeLost = true;
        }
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public boolean isStartPos() {
        return startPosition;
    }

    public void startPop() {
        //once activated (when spacebar is pressed) pop can start moving 
        startPosition = false;
    }

    public void setOldCoords(int oldXCoord, int oldYCoord) {
        this.oldXCoord = oldXCoord - (int) xVelocity;
        this.oldYCoord = oldYCoord - (int) yVelocity;

    }

    public int getOldX() {
        return oldXCoord;
    }

    public int getOldY() {
        return oldYCoord;
    }

    public void katchHit() {
        hitCount++;
        updateSpeed = true;
    }

    public boolean lifeLost() {
        if (lifeLost) {
            if (xVelocity > 2.0) {
                xVelocity--;
            }

            if (yVelocity > 2.0) {
                yVelocity--;
            }

            lifeLost = false;
            startPosition = true;
            return true;
        }

        return false;
    }
}
