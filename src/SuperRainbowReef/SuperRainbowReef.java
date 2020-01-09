package SuperRainbowReef;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Alexander Beers
 */
public class SuperRainbowReef extends JFrame {

    private final int SCREEN_WIDTH = 645;
    private final int SCREEN_HEIGHT = 510;
    private Screen screen;
    private int score = 0;
    private GameArena arena;
    private Image titleIcon = new ImageIcon("src/RainbowReef Resources/star.png").getImage();
    private Entity katchObservable;
    private Katch katch;
    private Pop pop;
    private Menu menu;

    public enum STATE {
        MENU,
        GAME
    };

    public static STATE State = STATE.MENU;

    public SuperRainbowReef() {
        pop = new Pop(320, 350, 60);
        katch = new Katch(300, 450);
        arena = new GameArena(katch, pop, this);
        screen = new Screen(this, pop);
        menu = new Menu();

        addKeyListener(screen.getKeyHandler());
        addMouseListener(new MouseInput());
        this.katchObservable = new Entity();
        katchObservable.addObserver(katch);
        add(screen);

        //initalize JFrame settings
        setIconImage(titleIcon);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setTitle("Super Rainbow Reef! [Score: " + score * 100 + "]");

        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setVisible(true);
        requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SuperRainbowReef game = new SuperRainbowReef();
        game.update();
        game.initMusic();
        game.gameLoop();

    }

    public void update() {
        if (pop.isStartPos()) {
            pop.setXCoordinate((int) katch.getXCoordinate() + 30);
            pop.setYCoordinate((int) katch.getYCoordinate() - 30);
        }
        pop.setOldCoords((int) pop.getXCoordinate(), (int) pop.getYCoordinate());
        pop.updatePop();

        arena.setEnvironment(pop);
        BufferedImage arenaImage = arena.getGameArena();
        if (State == STATE.GAME) {
            screen.render(arenaImage);

        }
        // paintMenu(g);
        collisionChecks();
    }

    public void paint(Graphics g) {
        if (State == STATE.MENU) {
            menu.render(g);
        }

    }

    public void paintComponent(Graphics g) {

    }

    public void setGame() {
        State = STATE.GAME;
    }

    private void gameLoop() {
        long lastUpdate = System.nanoTime();
        double fps = 60.0;
        double nanoseconds = 1000000000 / fps;
        double delta = 0;
        long timer = System.currentTimeMillis();

        //int frameCount = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastUpdate) / nanoseconds;
            lastUpdate = now;
            try {
                katchObservable.setChanged();
                katchObservable.notifyObservers();
                Thread.sleep(6);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }

            while (delta >= 0) {
                update();
                delta--;
            }
            //frameCount++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //frameCount = 0;
            }
        }
    }

    /**
     * initMusic(): This function loads and initializes the game's music and
     * plays it on an infinite loop
     */
    private void initMusic() {
        Clip gameMusic = null;
        AudioInputStream audIn = null;
        File soundFile = new File("src/RainbowReef resources/Music.wav");

        try {
            audIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a sound clip resource.
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("Unsupported Audio File");
        } catch (IOException ex) {
            System.out.println("Input Output Exception when assigning audio input stream object");
        }

        try {
            gameMusic = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            System.out.println("Failure Assigning Clip object");
        }

        try {
            gameMusic.open(audIn);
        } catch (LineUnavailableException ex) {
            System.out.println("Failure Opening Clip object");
        } catch (IOException ex) {
            System.out.println("Input Output Exception when Opening Clip Object");
        }
        gameMusic.loop(Clip.LOOP_CONTINUOUSLY);

    }

    //Check if there if there is an intersection between a pop and Katch
    private void katchCheck() {
        //Check for intersection then execute
        if (pop.getBounds().intersects(katch.getBounds())) {
            Rectangle overlapRect = pop.getBounds().intersection(katch.getBounds());

            //the side that pop hits katch determines which direcion pop deflects in
            if (pop.getBounds().getX() + 15 < katch.getBounds().getX() + 40) {
                pop.setXVelocity(Math.abs(pop.getXVelocity()) * -1);
            } else if (pop.getBounds().getX() + 15 > katch.getBounds().getX() + 40) {
                pop.setXVelocity(Math.abs(pop.getXVelocity()));
            } else if (pop.getBounds().getX() + 30 < katch.getBounds().getY() + 10) {
                pop.setXVelocity(Math.abs(pop.getYVelocity()) * -1);
            } else if (pop.getBounds().getX() < katch.getBounds().getY() + 5) {
                pop.setXVelocity(Math.abs(pop.getYVelocity()));
            }

            // fixed the crazy error
            if (overlapRect.width >= overlapRect.height) {
                pop.setYVelocity(pop.getYVelocity() * -1);
                pop.updatePop();
            } else {
                pop.setYVelocity(pop.getYVelocity());
                pop.updatePop();
            }
            pop.katchHit();
            if (State == STATE.GAME) {
                //initMusic();
            }
        }
    }

    private void wallCheck() {
        int xStart = ((int) (pop.getXCoordinate() / 40) - 1);
        if (xStart < 0) {
            xStart = 0;
        }

        int yStart = ((int) (pop.getYCoordinate() / 20) - 1);
        if (yStart < 0) {
            yStart = 0;
        }

        int xEnd = xStart + 3;
        if (xEnd > 17) {
            xEnd = 17;
        }
        int yEnd = yStart + 3;
        if (yEnd > 25) {
            yEnd = 25;
        }

        Entity[][] object2D = arena.getObjects();
        int numOfCollisions = 0;

        for (int i = xStart; i != xEnd; i++) {
            for (int j = yStart; j != yEnd; j++) {
                Rectangle obj = object2D[i][j].getBounds();
                if ((pop.getBounds().intersects(obj))) {
                    numOfCollisions++;

                    boolean incScore = true;

                    if ((pop.getBounds().intersects(obj)) && obj.toString() != "empty") {
                        Rectangle overlapRect = pop.getBounds().intersection(obj.getBounds());

                        //remove breakable blocks that are hit by pop
                        if (!"Special block type: unbreakable".equals(object2D[i][j].toString()) && !"empty".equals(object2D[i][j].toString())) {

                            //check to see if special actions are necessary for abnormal block types
                            if ("BigLegs".equals(object2D[i][j].toString())) {
                                score += 5;
                                incScore = false;
                                arena.decBigLegs();
                            } else if ("Special block type: double".equals(object2D[i][j].toString())) {
                                ((SpecialBlock) object2D[i][j]).damageDouble();
                                //if the double block can sustain another hit, reverse velocity and don't remove
                                if (((SpecialBlock) object2D[i][j]).chkDurability() > 0) {
                                    if (overlapRect.height >= overlapRect.width) {
                                        pop.setXVelocity(pop.getXVelocity() * -1);
                                        pop.updatePop();
                                        pop.updatePop();
                                        pop.updatePop();
                                    }
                                    if (overlapRect.width >= overlapRect.height) {
                                        pop.setYVelocity(pop.getYVelocity() * -1);
                                        pop.updatePop();
                                        pop.updatePop();
                                        pop.updatePop();
                                        pop.updatePop();
                                    }
                                    continue;
                                }
                            } else if ("Special block type: life".equals(object2D[i][j].toString())) {
                                screen.addLife();
                            } else if ("Special block type: points".equals(object2D[i][j].toString())) {
                                score += 9;
                            }

                            //based on how pop overlaps the object it hits, deflect off at correct direction
                            if (overlapRect.height >= overlapRect.width) {
                                pop.setXVelocity(pop.getXVelocity() * -1);
                                pop.updatePop();
                            } else if (overlapRect.width >= overlapRect.height) {
                                pop.setYVelocity(pop.getYVelocity() * -1);
                                pop.updatePop();
                            } else {
                                pop.setYVelocity(pop.getYVelocity() * -1);
                                pop.updatePop();
                            }

                            object2D[i][j] = new EmptyTile(i * 80, j * 30);

                            if (incScore) {
                                score++;
                            }

                            setTitle("Super Rainbow Reef! [Score: " + score * 100 + "]");
                        } //for unbreakable blocks only deflect, don't remove
                        else if ("Special block type: unbreakable".equals(object2D[i][j].toString())) {
                            if (overlapRect.height >= overlapRect.width) {
                                pop.setXVelocity(pop.getXVelocity() * -1);
                                pop.updatePop();
                                pop.updatePop();
                                pop.updatePop();
                            }
                            if (overlapRect.width >= overlapRect.height) {
                                pop.setYVelocity(pop.getYVelocity() * -1);
                                pop.updatePop();
                                pop.updatePop();
                                pop.updatePop();
                                pop.updatePop();
                            }
                        }

                        if (State == STATE.GAME) {
                            //initMusic();
                        }

                    }
                }
            }
        }
        if (numOfCollisions > 1) {
            System.out.println(numOfCollisions + " : Simultaneous collisions occured");
        }
    }

    //iterate through entire objects 2d array and check to see if anything collides with pop
    public void oldWallCheck() {
        Entity[][] temp = arena.getObjects();
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 25; j++) {
                Rectangle obj = temp[i][j].getBounds();

                boolean incScore = true;

                if ((pop.getBounds().intersects(obj))) {
                    Rectangle overlapRect = pop.getBounds().intersection(obj.getBounds());

                    //remove breakable blocks that are hit by pop
                    if (!"Special block type: unbreakable".equals(temp[i][j].toString()) && !"empty".equals(temp[i][j].toString())) {

                        //check to see if special actions are necessary for abnormal block types
                        if ("BigLegs".equals(temp[i][j].toString())) {
                            score += 5;
                            incScore = false;
                            arena.decBigLegs();
                        } else if ("Special block type: double".equals(temp[i][j].toString())) {
                            ((SpecialBlock) temp[i][j]).damageDouble();
                            //if the double block can sustain another hit, reverse velocity and don't remove
                            if (((SpecialBlock) temp[i][j]).chkDurability() > 0) {
                                if (overlapRect.height >= overlapRect.width) {
                                    pop.setXVelocity(pop.getXVelocity() * -1);
                                    //multiple calls to avoid glitching
                                    pop.updatePop();
                                    pop.updatePop();
                                    pop.updatePop();
                                }
                                if (overlapRect.width >= overlapRect.height) {
                                    pop.setYVelocity(pop.getYVelocity() * -1);
                                    pop.updatePop();
                                    pop.updatePop();
                                    pop.updatePop();
                                    pop.updatePop();
                                }
                                continue;
                            }
                        } else if ("Special block type: life".equals(temp[i][j].toString())) {
                            screen.addLife();
                        } else if ("Special block type: points".equals(temp[i][j].toString())) {
                            score += 9;
                        }

                        //based on how pop overlaps the object it hits, deflect off at correct direction
                        if (overlapRect.height >= overlapRect.width) {
                            pop.setXVelocity(pop.getXVelocity() * -1);
                            pop.updatePop();
                        } else if (overlapRect.width >= overlapRect.height) {
                            pop.setYVelocity(pop.getYVelocity() * -1);
                            pop.updatePop();
                        } else {
                            pop.setYVelocity(pop.getYVelocity() * -1);
                            pop.updatePop();
                        }

                        temp[i][j] = new EmptyTile(i * 80, j * 30);

                        if (incScore) {
                            score++;
                        }

                        setTitle("Super Rainbow Reef! [Score: " + score * 100 + "]");
                    } //for unbreakable blocks only deflect, don't remove
                    else if ("Special block type: unbreakable".equals(temp[i][j].toString())) {
                        if (overlapRect.height >= overlapRect.width) {
                            pop.setXVelocity(pop.getXVelocity() * -1);
                            pop.updatePop();
                            pop.updatePop();
                            pop.updatePop();
                        }
                        if (overlapRect.width >= overlapRect.height) {
                            pop.setYVelocity(pop.getYVelocity() * -1);
                            pop.updatePop();
                            pop.updatePop();
                            pop.updatePop();
                            pop.updatePop();
                        }
                    }

                    if (State == STATE.GAME) {
                        //initMusic();
                    }

                }
            }
        }
    }

    public void collisionChecks() {
        //check to see if pop collides with katch
        katchCheck();
        
        //WIP...
        //wallCheck();
        
        //iterate through entire objects 2d array and check to see if anything collides with pop
        oldWallCheck();
    }

    public Katch getKatch() {
        return katch;
    }

    public Pop getPop() {
        return pop;
    }

    public int getScore() {
        return score;
    }
}
