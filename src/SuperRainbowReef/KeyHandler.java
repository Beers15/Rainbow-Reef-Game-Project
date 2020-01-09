package SuperRainbowReef;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private Katch katch;
    private Pop pop;

    public KeyHandler(Katch katch, Pop pop) {
        this.katch = katch;
        this.pop = pop;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            katch.leftPress();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            katch.rightPress();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pop.startPop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            katch.leftRelease();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            katch.rightRelease();
        }
    }

}
