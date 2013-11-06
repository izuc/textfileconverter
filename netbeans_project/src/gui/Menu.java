package gui;

/**
 * @author: JAVA Alliance
 * @purpose: 
 * @date: 16 March 2009
 * @filename: Menu.java
 * @version: 1.2
 */
import javax.swing.*;

/**
 * The Menu bar for the application.
 * Draws Menu and adds action listeners for the menu items.
 * @author Java Alliance
 */
public class Menu extends JMenuBar implements GuiConstants {

    private static final long serialVersionUID = 9876543210L;
    // Menu => File 
    private JMenuItem itmImport,  itmExportToText,  itmExportToDB,  itmExit;
    // Menu => Tools
    private JMenuItem itmRecordAdd,  itmRecordDelete,  itmColumnAdd,  itmColumnDelete;
    // Menu => Settings
    private JMenuItem itmLF1,  itmLF2,  itmLF3,  itmLF4;
    // Menu => Help
    private JMenuItem itmAbout,  itmUserManual;

    /**
     * Constructor for Menu. 
     * Builds Menu, adds action listeners to the menu items. 
     * @param parent - GuiFrame.
     */
    public Menu(GuiFrame parent) {

        // Initializes menus and menu items.
        JMenu mnuFile = new JMenu(MENU_FILE);
        JMenu mnuData = new JMenu(MENU_DATA);
        JMenu mnuSettings = new JMenu(MENU_SETTINGS);
        JMenu mnuHelp = new JMenu(MENU_HELP);

        itmImport = new JMenuItem(MENU_ITEM_FILE_IMPORT);
        JMenu mnuExport = new JMenu(MENU_ITEM_FILE_EXPORT);
        JMenu mnuRecord = new JMenu(MENU_ITEM_DATA_RECORD);
        JMenu mnuColumn = new JMenu(MENU_ITEM_DATA_COLUMN);
        JMenu mnuLookAndFeel = new JMenu(MENU_ITEM_SETTINGS_LOOK);


        // Adds menu items to the File menu.
        mnuFile.add(itmImport = new JMenuItem(MENU_ITEM_FILE_IMPORT));
        mnuFile.add(mnuExport = new JMenu(MENU_ITEM_FILE_EXPORT));
        mnuFile.addSeparator(); // Separator between menu items.
        mnuExport.add(itmExportToDB = new JMenuItem(MENU_ITEM_FILE_EXPORT_DB));
        mnuExport.add(itmExportToText =
                new JMenuItem(MENU_ITEM_FILE_EXPORT_TEXT));
        mnuFile.add(itmExit = new JMenuItem(MENU_ITEM_FILE_EXIT));


        // Adds menu items for the Data menu.
        mnuData.add(mnuRecord = new JMenu(MENU_ITEM_DATA_RECORD));
        mnuRecord.add(itmRecordAdd = new JMenuItem(MENU_ITEM_DATA_RECORD_ADD));
        mnuRecord.add(itmRecordDelete =
                new JMenuItem(MENU_ITEM_DATA_RECORD_DELETE));
        mnuData.addSeparator(); // Separator between menu items.
        mnuData.add(mnuColumn = new JMenu(MENU_ITEM_DATA_COLUMN));
        mnuColumn.add(itmColumnAdd = new JMenuItem(MENU_ITEM_DATA_COLUMN_ADD));
        mnuColumn.add(itmColumnDelete =
                new JMenuItem(MENU_ITEM_DATA_COLUMN_DELETE));

        // Adds menu items for the Settings menu. 
        mnuSettings.add(mnuLookAndFeel = new JMenu(MENU_ITEM_SETTINGS_LOOK));
        mnuLookAndFeel.add(itmLF1 = new JMenuItem(MENU_ITEM_SETTINGS_LOOK_1));
        mnuLookAndFeel.add(itmLF2 = new JMenuItem(MENU_ITEM_SETTINGS_LOOK_2));
        mnuLookAndFeel.add(itmLF3 = new JMenuItem(MENU_ITEM_SETTINGS_LOOK_3));
        mnuLookAndFeel.add(itmLF4 = new JMenuItem(MENU_ITEM_SETTINGS_LOOK_4));

        // Adds menu items for the About menu.
        mnuHelp.add(itmAbout = new JMenuItem(MENU_ITEM_HELP_ABOUT));
        mnuHelp.add(itmUserManual = new JMenuItem(MENU_ITEM_HELP_MANUAL));

        // Adds menus to the menu bar.
        this.add(mnuFile);
        this.add(mnuData);
        this.add(mnuSettings);
        this.add(mnuHelp);

        // Adds action listeners to the menu items.
        itmExit.addActionListener(parent);
        itmAbout.addActionListener(parent);
        itmImport.addActionListener(parent);
        itmExportToText.addActionListener(parent);
        itmExportToDB.addActionListener(parent);

        itmRecordAdd.addActionListener(parent);
        itmRecordDelete.addActionListener(parent);
        itmColumnAdd.addActionListener(parent);
        itmColumnDelete.addActionListener(parent);
        itmUserManual.addActionListener(parent);

        itmLF1.addActionListener(parent);
        itmLF2.addActionListener(parent);
        itmLF3.addActionListener(parent);
        itmLF4.addActionListener(parent);
        this.updateDataMenuStatus(false);
    }

    /**
     * Getters for the menu items.
     */
    public JMenuItem getItmImport() {
        return itmImport;
    }

    public JMenuItem getItmExportText() {
        return itmExportToText;
    }

    public JMenuItem getItmExportDB() {
        return itmExportToDB;
    }

    public JMenuItem getItmRecordAdd() {
        return itmRecordAdd;
    }

    public JMenuItem getItmRecordDelete() {
        return itmRecordDelete;
    }

    public JMenuItem getItmTableAddColumn() {
        return itmColumnAdd;
    }

    public JMenuItem getItmTableDeleteColumn() {
        return itmColumnDelete;
    }

    public JMenuItem getItmUserManual() {
        return itmUserManual;
    }

    public JMenuItem getItmExit() {
        return itmExit;
    }

    public JMenuItem getItmAbout() {
        return itmAbout;
    }

    public JMenuItem getItmLF1() {
        return itmLF1;

    }

    public JMenuItem getItmLF2() {
        return itmLF2;
    }

    public JMenuItem getItmLF3() {
        return itmLF3;
    }

    public JMenuItem getItmLF4() {
        return itmLF4;
    }

    public JMenuItem getItmColumnAdd() {
        return itmColumnAdd;
    }

    public void setItmColumnAdd(JMenuItem itmColumnAdd) {
        this.itmColumnAdd = itmColumnAdd;
    }

    public JMenuItem getItmColumnDelete() {
        return itmColumnDelete;
    }

    public void setItmColumnDelete(JMenuItem itmColumnDelete) {
        this.itmColumnDelete = itmColumnDelete;
    }

    public boolean updateDataMenuStatus(boolean showDataMenu) {
        this.getItmRecordAdd().setEnabled(showDataMenu);
        this.getItmColumnAdd().setEnabled(showDataMenu);
        this.getItmColumnDelete().setEnabled(showDataMenu);
        this.getItmRecordDelete().setEnabled(showDataMenu);
        return showDataMenu;
    }
}
