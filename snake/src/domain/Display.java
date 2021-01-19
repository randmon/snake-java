package domain;

import javax.swing.*;
import java.awt.*;

/** Game display
 * JFrame: title (String), width, height (pixels)
 * */

public class Display {

    private JFrame frame; //Window
    private Canvas canvas; //Graphics

    private final String title;
    private final int width, height;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay();
    }

    private void createDisplay() {

        //JFRAME
        frame = new JFrame(title);
        frame.setSize(width, height);

        //Make sure game exits on window close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false); //make window not resizable
        frame.setLocationRelativeTo(null); //center window on screen

        //frame.setUndecorated(true);

        frame.setVisible(true); //default is false(?)

        //CANVAS
        canvas = new Canvas();
        //size
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));

        canvas.setFocusable(false);//canvas shouldn't be focused

        //ADD
        frame.add(canvas);
        frame.pack();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }

}
