package gui;

/**
 * 
 * @author: JAVA Alliance
 * @date: 27 March 2009
 * @filename: GuiFrame.java
 * @version: 1.0
 */
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import system.*;

/**
 * The GuiFrame class is the main interface which the client views; it
 * contains the tabbed pane, instantiates the single record and data table views, creates
 * the gui wizards, and monitors the actions triggered from the menu system.
 * The class implements the viewable interface (stored in the system package) which allows
 * the class to be instantiated and received within the view plug.
 * @author Java Alliance
 */
public class GuiFrame extends JFrame implements GuiConstants, ActionListener, Viewable {

    private static final String INT_ERROR = "You have entered either a word " +
            "or a number larger then the amount of rows in the data structure";
    private Wizard wizard;
    private Menu menu;
    private Container container;
    private DataPanel model;

    /**
     * The GuiFrame contructor initialises the main gui interface, by instantiating
     * the menu object (which extends JMenuBar) and sets it to the gui frame. The size of
     * the application is set to a specific fixed size, and loads a splash screen which
     * is set on a thread timer.
     */
    public GuiFrame() {
        MirrorMirror.setLookAndFeel(1, this);
        this.container = this.getContentPane();
        this.container.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        this.setJMenuBar(this.menu = new Menu(this));
        this.setVisible(true);
        this.setSize(500, 500);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(MAIN_HEADING);

        new Splash(SPLASH_SCREEN, this);
    }

    /**
     * The init method initialises the gui once the structure has been created; based on the
     * api, and the file import wizard. Once the structure has been loaded, it initialises the
     * main gui frame by creating the tabbed panes; containing the single record and data table views.
     */
    public void init() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                if (menu.updateDataMenuStatus(((JTabbedPane) e.getSource()).getSelectedIndex() == 1)) {
                    ((DataPanel) ((JTabbedPane) e.getSource()).getSelectedComponent()).refreshTableColumns();
                }
            }
        });
        JComponent singleRecord = new JPanel();
        singleRecord.add(new SingleRecord());
        tabbedPane.addTab(SINGLE_RECORD, singleRecord);
        this.model = new DataPanel();
        tabbedPane.addTab(DATA_TABLE, this.model);

        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.setAutoscrolls(true);
        this.container.removeAll(); // Ensures the JPanel is infact empty.
        this.container.add(tabbedPane);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    /**
     * The menu actions are handled within the following actionPerformed method.
     * The method instantiates, and loads the GuiWizards for file import and export.
     * The other options for adding and removing column names are handled.
     */
    private void deleteRecord() {
        int i;
        try {
            String s =
                    JOptionPane.showInputDialog("Please enter a row to " +
                    "delete: 1-" + ViewPlug.getData().size());
            i = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            i = -1;
        }
        if (i >= 1 && i <= ViewPlug.getData().size()) {
            ViewPlug.deleteRow(i - 1);
        } else if (i != -1) {
            JOptionPane.showMessageDialog(null, INT_ERROR);

        }
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void addRecord() {
        // adds a row at start
        int i;
        try {
            String s = JOptionPane.showInputDialog("Please enter the " +
                    "position to add row: 1-" +
                    (ViewPlug.getData().size() + 1));
            i = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            i = -1;
        }
        if (i >= 1 && i <= ViewPlug.getData().size() + 1) {
            ViewPlug.insertRow(i - 1);
        } else if (i != -1) {
            JOptionPane.showMessageDialog(null, INT_ERROR);
        }
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void addColumn() {
        int i;
        try {
            String s = JOptionPane.showInputDialog("Please enter the " +
                    "position to add column: 1-" +
                    (ViewPlug.getColumnNames().size() + 1));
            i = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            i = -1;
        }
        if (i >= 1 && i <= ViewPlug.getColumnNames().size() + 1) {
            ViewPlug.insertColumn(i - 1);
        } else if (i != -1) {
            JOptionPane.showMessageDialog(null, INT_ERROR);

        }
        SwingUtilities.updateComponentTreeUI(this);
        model.refreshTableColumns();
    }

    private void deleteColumn() {
        int i;
        try {
            String s = JOptionPane.showInputDialog("Please enter a " +
                    "column to delete: 1-" +
                    ViewPlug.getColumnNames().size());
            i = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            i = -1;
        }
        if (i >= 1 && i <= ViewPlug.getColumnNames().size()) {
            ViewPlug.deleteColumn(i - 1);
            SwingUtilities.updateComponentTreeUI(this);
            model.refreshTableColumns();
        } else if (i != -1) {
            JOptionPane.showMessageDialog(null, INT_ERROR);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.menu.getItmExit()) {
            System.exit(0);
        } else if (e.getSource() == this.menu.getItmImport()) {
            this.wizard = new Wizard(this);
            this.wizard.registerPanel("Page1", new GUIOpenFile());
            this.wizard.registerPanel("Page2", new GUIColumnNames());
            this.wizard.display();
        } else if (e.getSource() == this.menu.getItmExportDB()) {
            this.wizard = new Wizard(this);
            this.wizard.registerPanel("Page1", new GUIExportDatabase());
            this.wizard.registerPanel("Page2", new GUIDatabaseSetup());
            this.wizard.display();
        } else if (e.getSource() == this.menu.getItmExportText()) {
            this.wizard = new Wizard(this);
            this.wizard.registerPanel("Page1", new GUISaveFile());
            this.wizard.display();
        } else if (e.getSource() == this.menu.getItmAbout()) {
            this.createNewDialog("About", new AboutBox());

        } else if (e.getSource() == this.menu.getItmUserManual()){
            //This gets the current runtime environment
            Runtime rt = Runtime.getRuntime();
            
            //This nested try catch block looks for a PDF application
            //If can't find one an exception is caught and message appears
            try{
                Process p = rt.exec(PDF_LOCALE);
            }
            catch (java.io.IOException e2){
                    
                JOptionPane.showMessageDialog(null,RULES_ERROR);
            }
        }

        else if (e.getSource() == this.menu.getItmLF1()) {
            MirrorMirror.setLookAndFeel(1, this);
        } else if (e.getSource() == this.menu.getItmLF2()) {
            MirrorMirror.setLookAndFeel(3, this);
        } else if (e.getSource() == this.menu.getItmLF3()) {
            MirrorMirror.setLookAndFeel(4, this);
        } else if (e.getSource() == this.menu.getItmLF4()) {
            MirrorMirror.setLookAndFeel(5, this);
        } else if (e.getSource() == this.menu.getItmRecordDelete()) {
            this.deleteRecord();
        } else if (e.getSource() == this.menu.getItmRecordAdd()) {
            this.addRecord();
        } else if (e.getSource() == this.menu.getItmTableAddColumn()) {
            this.addColumn();
        } else if (e.getSource() == this.menu.getItmTableDeleteColumn()) {
            this.deleteColumn();
        }
    }

    /**
     * Creates a standard JDialog centred on the parent JFrame
     * @param String title: receives the title for the JDialog.
     * @param receives the JDialog to be centered.
     */
    private void createNewDialog(String title_, JDialog dialog_) {
        dialog_.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog_.setResizable(false);
        dialog_.setTitle(title_);
        dialog_.setVisible(true);
        dialog_.setLocationRelativeTo(this);
    }
}
