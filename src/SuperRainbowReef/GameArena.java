/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperRainbowReef;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author Alexander Beers
 */
public class GameArena extends JComponent {

    private final Entity objects[][] = new Entity[17][25];
    private Dimension screenSize = new Dimension(666, 700);
    private BufferedImage backgroundImage;
    private Katch katch;
    private Pop pop;
    private SuperRainbowReef game;
    private int numBigLegs = 0;
    
    //can change to start at lvl 2 or 3
    private int loadThisLvl = 1;
    
    private String scoreMsg = "";
    private String highScoreMsg = "";

    public GameArena(Katch katch, Pop pop, SuperRainbowReef game) {
        this.katch = katch;
        this.pop = pop;
        this.game = game;
        loadLevel(loadThisLvl);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawKatch(g);
        drawPop(g);
        //doesn't show anything until congrats screen if reached
        drawScore(g);

        for (Entity[] objectArray : objects) {
            for (Entity obj : objectArray) {
                g.drawImage(obj.getImage(), (int)obj.getXCoordinate(), (int)obj.getYCoordinate(), this);
            }
        }
    }

    public BufferedImage getGameArena() {
        BufferedImage bi = new BufferedImage((int) screenSize.getWidth(), (int) screenSize.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        paintComponent(g);
        return bi;
    }

    public void drawBackground(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, this);
    }

    private void drawKatch(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(katch.getXCoordinate(), katch.getYCoordinate());
        rotation.rotate(Math.toRadians(katch.getAngle()), katch.getImage().getWidth() / 2, katch.getImage().getHeight() / 2);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(katch.getImage(), rotation, null);
    }
    
    private void drawPop(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(pop.getXCoordinate(), pop.getYCoordinate());
        rotation.rotate(Math.toRadians(pop.getAngle()), pop.getImage().getWidth() / 2, pop.getImage().getHeight() / 2);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(pop.getImage(), rotation, null);
    }

    public void loadLevel(int level) {
        String line;
        File arenaFile;
        BufferedReader buf;
        int row = 0;
        
        pop.setXCoordinate(320);
        pop.setYCoordinate(418);
        katch.setXCoordinate(300);
        katch.setYCoordinate(450);

        //load appropriate lvl and initialize used variables
        String fileName = "";
        if (level == 1) {
            fileName = "src/RainbowReef Resources/lvl1.txt";
            //hard coded for now until better solution found
            numBigLegs = 6;
            
            try {
                backgroundImage = ImageIO.read(new File("src/RainbowReef Resources/background1.bmp"));
            } catch (IOException e) {
                System.out.println("Background Image not found...");
            }
        }       
        else if (level == 2) {
            fileName = "src/RainbowReef Resources/lvl2.txt";
            numBigLegs = 3;
            try {
                backgroundImage = ImageIO.read(new File("src/RainbowReef Resources/Background2.bmp"));
            } catch (IOException e) {
                System.out.println("Background Image not found...");
            }
        } 
        else if(level == 3) {
            fileName = "src/RainbowReef Resources/lvl3.txt";
            numBigLegs = 4;
            try {
                //image url https://www.pandotrip.com/top-10-wonders-of-the-underwater-world-21847/
                backgroundImage = ImageIO.read(new File("src/RainbowReef Resources/background3.jpg"));
            } catch (IOException e) {
                System.out.println("Background Image not found...");
            }
        }
        else if(level == 4) {
            fileName = "src/RainbowReef Resources/gameover.txt";
            
            try {
                backgroundImage = ImageIO.read(new File("src/RainbowReef Resources/Congratulation.jpg"));
            } catch (IOException e) {
                System.out.println("Background Image not found...");
            } 
            
            highScoreChk(game.getScore() * 100);
        }

        //create the appropriate object based on its integer representation in the lvl loading .txt file
        //then add to objects array thats used for painting and collisions
        try {
            arenaFile = new File(fileName);
            buf = new BufferedReader(new FileReader(arenaFile));

            line = buf.readLine();

            while (line != null) {
                for (int column = 0; column < line.length(); column++) {
                    char objNum = line.charAt(column);

                    switch (objNum) {
                        //checks for loading of 1 of 7 types of normal blocks
                        case '1':
                            objects[column][row] = new Block((column * 40), (row * 20), 1);
                            objects[column][row].initBounds();
                            break;
                        case '2':
                            objects[column][row] = new Block((column * 40), (row * 20), 2);
                            objects[column][row].initBounds();
                            break;
                        case '3':
                            objects[column][row] = new Block((column * 40), (row * 20), 3);
                            objects[column][row].initBounds();
                            break;
                        case '4':
                            objects[column][row] = new Block((column * 40), (row * 20), 4);
                            objects[column][row].initBounds();
                            break;
                        case '5':
                            objects[column][row] = new Block((column * 40), (row * 20), 5);
                            objects[column][row].initBounds();
                            break;
                        case '6':
                            objects[column][row] = new Block((column * 40), (row * 20), 6);
                            objects[column][row].initBounds();
                            break;
                        case '7':
                            objects[column][row] = new Block((column * 40), (row * 20), 7);
                            objects[column][row].initBounds();
                            break;
                        //checks for loading of 1 of 3 types of special blocks
                        case 'x':
                            objects[column][row] = new SpecialBlock((column * 40), (row * 20), 1);
                            objects[column][row].initBounds();
                            break;
                        case 'l':
                            objects[column][row] = new SpecialBlock((column * 40), (row * 20), 2);
                            objects[column][row].initBounds();
                            break;
                        case 's':
                            objects[column][row] = new SpecialBlock((column * 40), (row * 20), 3);
                            objects[column][row].initBounds();
                            break;
                        case 'd':
                            objects[column][row] = new SpecialBlock((column * 40), (row * 20), 4);
                            objects[column][row].initBounds();
                            break;
                        //checks for loading bigLegs enemy
                        case 'b':
                            objects[column][row] = new BigLegs((column * 40), (row * 20));
                            objects[column][row].initBounds();
                            break;
                        case '0':
                            //add empty objs that do nothing for spaces denoted with 0
                            objects[column][row] = new EmptyTile(column * 40, row * 20);
                            break;
                        default:
                            objects[column][row] = new EmptyTile(column * 40, row * 20);
                            break;
                    }
                }
                row++;
                line = buf.readLine();
            }
        } catch (IOException e) {
        }   
        loadThisLvl++;
    }

    public void setEnvironment(Pop pop) {
        //this.katch = katch;
        this.pop = pop;
    }
    
    public Entity[][] getObjects() {
        return objects;
    } 
    
    public void decBigLegs() {
        numBigLegs--;
        if(numBigLegs == 0) {
            loadLevel(loadThisLvl);
        }
    }
    
    //executes when the "4th lvl" is loaded, which isn't actually a lvl and just a congrats screen
    public void highScoreChk(int score) {
        BufferedReader buf;
        BufferedWriter wBuf;
        
        try {
            buf = new BufferedReader(new FileReader(new File("src/RainbowReef Resources/scores.txt")));   

            int [] scores = new int[3];
        
            for (int i = 0; i < 3; i++) {
                scores[i] = Integer.parseInt(buf.readLine());
                System.out.println(scores[i]);
            }
        
            int index = -1;
            for (int i = 0; i < 3; i++) {
                if(score > scores[i]) {
                    scores[i] = score;
                    index = i;
                    break;
                }
            }   
            if(index != -1) {
                index++;
                highScoreMsg = "Congratulations! You made the top 3!!  (Placed " + index + ")";
            }
            
            wBuf = new BufferedWriter(new FileWriter(new File("src/RainbowReef Resources/scores.txt"))); 
            String str = "";
            
            for (int i = 0; i < 3; i++) {
              System.out.println(scores[i]);
                wBuf.write(Integer.toString(scores[i]) + "\n");
            }   
            
            wBuf.close();
            
        } catch (IOException e) {
            System.out.println("error checking high scores file");       
        } 
        
        scoreMsg = " Your score was " + score; 
    }
    
    public void drawScore(Graphics g) {
        Font font = new Font("sansserif", Font.BOLD+Font.ITALIC, 24);
        g.setFont(font);
        g.setColor(Color.blue);
        g.drawString(highScoreMsg, 60, 120);
        g.drawString(scoreMsg, 220, 145);
    }
}
