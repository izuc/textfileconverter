package gui;

import javax.swing.*;
import javax.swing.plaf.metal.*;

/**
 * Methods enables users to set the look and feel of the application.
 * @author: JAVA Alliance
 * @date: 1 May 2009
 * @filename: MirrorMirror.java
 * @version: 1.0
 */
public class MirrorMirror {

    private final static String MOTIF_LOOK =
            "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    private final static String WINDOWS_LOOK =
            "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    private final static String GTK_LOOK =
            "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
    private final static String CROSS_PLATFORM =
            "javax.swing.plaf.metal.MetalLookAndFeel";

    /**
     * Sets the look of the chosen frame.
     * @param look - an integer whoch identify the look.
     * @param frame - reference to the GuiFrame.
     */
    public static void setLookAndFeel(int look, GuiFrame frame) {

        // Apply chosen look and feel

        // 1        Windows
        // 2        Motif
        // 3        GTK      only runs on Unix systems with GTK 2.2 or later
        //					 installed

        // 4        Metal - DefaultMetalTheme (which is not the default!)
        // Default  Metal - Ocean Theme - CROSS PLATFORM JAVA
        //- this is used when Look and Feel is not set, or not available

        try {
            // Available values for looks.
            switch (look) {
                case 1:
                    UIManager.setLookAndFeel(WINDOWS_LOOK);
                    break;

                case 2:
                    UIManager.setLookAndFeel(MOTIF_LOOK);
                    break;

                case 3:
                    UIManager.setLookAndFeel(GTK_LOOK);
                    break;

                case 4:
                    UIManager.setLookAndFeel(CROSS_PLATFORM);
                    MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
                default:
                    UIManager.setLookAndFeel(CROSS_PLATFORM);
                    MetalLookAndFeel.setCurrentTheme(new OceanTheme());
            }
            // Updates the frame with the selected look.
            SwingUtilities.updateComponentTreeUI(frame);
        } // If the selected look is not valid, keeps the existing look.
        catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "That look and feel is not supported." +
                    "\n\n The current settings will remain.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (UnsupportedLookAndFeelException e) {
            // sets to default if not supported and not currently set
        }
    }
}
