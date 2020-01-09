/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperRainbowReef;

import static SuperRainbowReef.SuperRainbowReef.State;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Ethan
 */
public class MouseInput implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
// public Rectangle playButton = new Rectangle(SuperRainbowReef.WIDTH / 4 + 120, 150, 100, 50);

    public void mousePressed(MouseEvent e) {

        if (State == SuperRainbowReef.STATE.MENU) {

            // hit music
            Clip gameMusic = null;
            AudioInputStream audIn = null;
            File soundFile = new File("src/RainbowReef resources/Sound_click.wav");

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

            gameMusic.start();
        }

        int mx = e.getX();
        int my = e.getY();

        if (mx >= 480 && mx <= 601) {
            if (my >= 260 && my <= 311) {

                SuperRainbowReef.State = SuperRainbowReef.STATE.GAME;
            }
        }

        if (mx >= 480 && mx <= 601) {
            if (my >= 330 && my <= 381) {
                System.exit(1);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
