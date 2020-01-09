/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperRainbowReef;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Alexander Beers
 */
//Can we Has-A JPanel instead of Is-A?
//if screen needs to extend something else then we could, otherwise im not sure why thats necessary
public class Screen extends JPanel {

    private BufferedImage arena;
    private BufferedImage lifeImage;
    private SuperRainbowReef game;
    private Pop pop;
    private int numLives = 3;
    
    public Screen(SuperRainbowReef game, Pop pop) {
        try {
            lifeImage = ImageIO.read(new File("src/RainbowReef Resources/Katch_small.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
        }
 
        this.pop = pop;
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(arena, -20, 0, this);
        
        if(pop.lifeLost()) 
            numLives--;
        
        if(numLives == 0) {
            System.out.println("GAME OVER");
            System.exit(0);
        }
        drawLives(g);
    }

    public void render(BufferedImage buf) {
        this.arena = buf;
        repaint();
    }
    
    public KeyHandler getKeyHandler() {
        KeyHandler keyHandler = new KeyHandler(game.getKatch(), game.getPop());

        return keyHandler;
    }
    
    public void drawLives(Graphics g) {
        for(int i = 0; i < numLives; i++)
            g.drawImage(lifeImage, 20 + i * 35, 460, this);
    }
    
    public void addLife() {
        if(numLives < 11)
            numLives++;
    }

}
