package gui;

/**
 * @author: JAVA Alliance
 * @purpose: The About Box to show information about the program. Includes
 * 			 inner class for hyperlink clicks.
 * @date: 14 April 2009
 * @filename: AboutBox.java
 * @version: 1.1
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * AboutBox
 * @extends JDialog
 * @implements ActionListener, GuiConstants
 */
public class AboutBox extends JDialog implements ActionListener, GuiConstants {

    private static final long serialVersionUID = 1L; // Serial Version UID
    private JButton btnOK; // OK Button

    /**
     * Constructor.
     * The About box going to have heading with application's
     * name, text area with some stuff writing there and
     * the button to close it.
     */
    public AboutBox() {

        // Sets the location of the window relative to GuiFrame.
        this.setLocationRelativeTo(this.getParent());
        // Panel for the heading.
        JPanel pnlHeading = new JPanel(new BorderLayout());
        // Panel for the text.
        JPanel pnlText = new JPanel();
        pnlText.setLayout(new BorderLayout());
        // Panel for the button.
        JPanel pnlButtons = new JPanel();
        // Label for the image.
        JLabel lblImage = new JLabel(new ImageIcon(IMAGE_PATH));
        //Adds label to the heading panel.
        pnlHeading.add(lblImage, BorderLayout.WEST);

        /**
         * Takes about box content from the html page. 
         */
        // Initializes new editorPane.
        JEditorPane editorPane = new JEditorPane();
        // Desables editing.
        editorPane.setEditable(false);
        // Gets URL for About Box content.
        final URL helpURL = getClass().getResource(HTML_ABOUT);
        // If the URL exists
        if (helpURL != null) {
            try {
                // Fill the editorPane with URL's content.
                editorPane.setPage(helpURL);
                // Add listener for the Hyperlink.
                editorPane.addHyperlinkListener(new MyHyperlinkListener());

            } catch (IOException e) {
                // If the page is not accessible log the error
                JOptionPane.showMessageDialog(null, BAD_URL + helpURL);
            }
        } else {
            // If there URL doesn't exists, throws error message.
            JOptionPane.showMessageDialog(null, ABOUT_NOT_FOUND);
        }

        // Puts the editor pane into a scroll pane.
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        // Makes vertical scrollbar to appear  always in the scrollpane. 
        editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // Sets the preferred size of the ScrollPane.
        editorScrollPane.setPreferredSize(new Dimension(250, 145));
        // Sets the minimum size of the ScrollPane.
        editorScrollPane.setMinimumSize(new Dimension(10, 10));


        // Adds editorScrollPane to the text panel.
        pnlText.add(editorScrollPane);
        // The container to put panels and the button.
        Container cn = this.getContentPane();
        // Sets Layout for the Container.
        cn.setLayout(new BorderLayout());
        // Adds panels to the container.
        cn.add(pnlHeading, BorderLayout.NORTH);
        cn.add(pnlText, BorderLayout.CENTER);

        // Initializes OK Button.
        btnOK = new JButton(OK);
        // Sets button's size.
        btnOK.setSize(10, 10);
        pnlButtons.add(btnOK);
        // Adds button to the panel.
        cn.add(pnlButtons, BorderLayout.SOUTH);
        // Sets title 
        this.setTitle(ABOUT_FILE_FORMAT_CONVERTER);
        // Sets window's size.
        this.setSize(457, 480);
        // Adds action listener to the button.
        btnOK.addActionListener(this);
    }

    /**
     * Closing event for the About Box.
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        // Closes the AboutBox when press OK button.
        if (e.getSource() == btnOK) {
            this.dispose();
        }
    }
}

/**
 * Inner class - handler for hyperlink clicks
 * @implements HyperlinkListener, GuiConstants.
 */
class MyHyperlinkListener implements HyperlinkListener, GuiConstants {

    /**
     * Handles Hyperlink clicks.
     * @param evt - Hyperlink event.
     */
    public void hyperlinkUpdate(HyperlinkEvent evt) {

        // Initializes URI
        URI uri = null;
        try {
            // Sets site name.
            uri = new URI(SITE_NAME);
        } // Checked exception thrown to indicate that a string could not be
        // parsed as a URI reference. 
        catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if (Desktop.isDesktopSupported()) {
                // Desktop instance of the current browser context. 
                Desktop desktop = Desktop.getDesktop();
                try {
                    // Launches the default browser to display a URI.
                    desktop.browse(uri);
                } // When the website does not exist, throws error message.
                catch (IOException e) {
                    JOptionPane.showMessageDialog(null, WEBSITE_NOT_EXIST);
                }
            } else {
                // If there is no browser installed, throws error message.
                JOptionPane.showMessageDialog(null, BROWSER_NOT_INSTALLED);
            }
        }
    }
}

