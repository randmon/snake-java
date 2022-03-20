package domain;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private final boolean[] keys;
    public boolean up, down, left, right; //directions of movement

    public KeyManager() {
        keys = new boolean[256]; //Each key has an ID
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void tick() {
        up = keys[KeyEvent.VK_UP];
        if(up) keys[KeyEvent.VK_UP] = false;
        down = keys[KeyEvent.VK_DOWN];
        if(down) keys[KeyEvent.VK_DOWN] = false;
        left = keys[KeyEvent.VK_LEFT];
        if(left) keys[KeyEvent.VK_LEFT] = false;
        right = keys[KeyEvent.VK_RIGHT];
        if(right) keys[KeyEvent.VK_RIGHT] = false;
    }
}
