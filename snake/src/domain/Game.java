package domain;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private Display display;
    private BufferStrategy bs;
    private Graphics g;
    private Field field;
    private final int width, height;
    private String title;
    private boolean running = false;
    private Thread thread;
    private KeyManager keyManager;
    private Handler handler;

    /**CONSTRUCTOR*/
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        keyManager = new KeyManager();
    }

    /** initialize graphics */
    public void init() {
        display = new Display(title, width,height);
        display.getFrame().addKeyListener(keyManager);
        handler = new Handler(this);
        field = new Field(handler);
    }

    public void tick() {
        keyManager.tick();
        field.tick();
    }

    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        //create buffer strategy
        if(bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        field.render(g);

        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();

        int fps = 10; //ticks per second

        //there are 1E9 nanosecs in 1 sec
        double timePerTick = 1E9 / fps; //max time to run 1 tick
        double delta = 0;
        long now;
        long lastTime = System.nanoTime(); //current time of computer
        long timer = 0;
        int ticks = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1E9){
                //System.out.println("FPS: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public synchronized void start() {
        /* If start method gets called while game already running
        prevent from reinitializing the thread */
        if (running) {return;}
        running = true;
        thread = new Thread(this);
        thread.start(); //calls run method
    }

    public synchronized void stop() {
        /* If stop method gets called while game already stopped
        prevent from throwing a bunch of errors */
        if (!running) {return;}
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
