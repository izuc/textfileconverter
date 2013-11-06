package gui;

/**
 * @author: JAVA Alliance
 * @purpose: A Splash Screen
 * @date: 12th April 2009
 * @filename: Splash.java
 * @version: 1.0
 */
import java.awt.*;
import javax.swing.*;

/**
 * Creates a Splash screen for the application
 * @author Java Alliance
 */
public class Splash extends JPanel {

    /**
     * Constructor
     * @param filename
     * @param f
     */
    public Splash(String filename, Frame f) {

        //Adds the image file to a JLabel
        JLabel l = new JLabel(new ImageIcon(this.getClass().getResource(filename)));

        //Add the label to the frame
        f.add(l);

        // repack to size of image
        f.pack();
        //re-centre the Frame on screen
        f.setLocationRelativeTo(null);

        //Need to disable resize while splash displayed or causes problems
        f.setResizable(false);
        f.setVisible(true);

        // 8 seconds pause - Note (can operate menu while splash on)
        try {
            Thread.sleep(8 * 1000);
            f.remove(l);
            f.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //turn resize back on
        f.setResizable(true);
    }
}
