package SuperRainbowReef;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;

/**
 * What does Katch need? dimensions, side to side movement,
 *
 * @author Hamish
 */
public class Katch extends Entity implements Observer {

    private double r = 3.0;
    private short angle;
    private int velocity;
    private boolean goLeft, goRight;
    private AffineTransform rotation;

    public Katch(int xCoordinate, int yCoordinate) {

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        angle = 0;
        velocity = 0;
        //For Collision checking
        bounds.setLocation(xCoordinate, yCoordinate);
        bounds.setSize(80, 30);

        try {
            image = ImageIO.read(new File("src/RainbowReef Resources/Katch.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void rightPress() {
        goRight = true;
    }

    public void leftPress() {
        goLeft = true;
    }

    public void rightRelease() {
        goRight = false;
    }

    public void leftRelease() {
        goLeft = false;
    }

    public String toString() {
        return "Katch";
    }

    @Override
    public void update(Observable o, Object o1) {
        if (this.goLeft) {
            this.moveLeft();
          
        }
        if (this.goRight) {
            this.moveRight();
        }
        bounds.setLocation((int)xCoordinate, (int)yCoordinate);
        super.setChanged();
    }

    private void moveLeft() {
        velocity = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        xCoordinate -= velocity;
        bounds.setLocation((int)xCoordinate, (int)yCoordinate);
        
        checkBorder();
    }

    private void moveRight() {
        velocity = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        xCoordinate += velocity;
        bounds.setLocation((int)xCoordinate, (int)yCoordinate);
        
        checkBorder();
    }
    
    private void checkBorder() {
        if (xCoordinate >= 550) {
            xCoordinate = 550;
        }
        if (xCoordinate <= 25) {
            xCoordinate = 25;
        }
    }
    
    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }
    
    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
    
}
