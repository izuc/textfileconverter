package gui;

/**
 * Contains constants for user interface.
 * @author: JAVA Alliance
 * @date: 17 March 2009
 * @filename: GuiConstants.java
 * @version: 1.1
 */
interface GuiConstants {

    // Common
    static final String MAIN_HEADING = "File Format Converter";
    public static final String LS = System.getProperty("line.separator");
    public static final String SPACE = " ";
    static final String EMPTY = "";

    // Main Menu
    static final String MENU_FILE = "File";
    static final String MENU_DATA = "Tools";
    static final String MENU_SETTINGS = "Settings";
    static final String MENU_HELP = "Help";
    // File Menu Items
    static final String MENU_ITEM_FILE_IMPORT = "Import Data";
    static final String MENU_ITEM_FILE_EXPORT = "Export";
    static final String MENU_ITEM_FILE_EXPORT_TEXT = "--> Text";
    static final String MENU_ITEM_FILE_EXPORT_DB = "--> Database";
    static final String MENU_ITEM_FILE_EXIT = "Exit";
    // Tools Menu Items
    static final String MENU_ITEM_DATA_RECORD = "Record";
    static final String MENU_ITEM_DATA_RECORD_ADD = "Add Record";
    static final String MENU_ITEM_DATA_RECORD_DELETE = "Delete Record";
    static final String MENU_ITEM_DATA_COLUMN = "Column";
    static final String MENU_ITEM_DATA_COLUMN_ADD = "Add Column";
    static final String MENU_ITEM_DATA_COLUMN_DELETE = "Delete Column";
    // Settings Menu Items
    static final String MENU_ITEM_SETTINGS_LOOK = "Look and feel";
    static final String MENU_ITEM_SETTINGS_LOOK_1 = "Windows";
    static final String MENU_ITEM_SETTINGS_LOOK_2 = "GTK (Unix only)";
    static final String MENU_ITEM_SETTINGS_LOOK_3 = "Java-default";
    static final String MENU_ITEM_SETTINGS_LOOK_4 = "Java-ocean";

    // Help Menu Items
    static final String MENU_ITEM_HELP_ABOUT = "About";
    static final String MENU_ITEM_HELP_MANUAL = "User Manual";
    // Tab Panes
    static final String SINGLE_RECORD = "Single Record";
    static final String DATA_TABLE = "Data Table";
    static final String CHANGE_DATA_TYPE = "Change Data Type";
    static final String[] buttonNames = {"|<", "<<", ">>", ">|"};
    static final String FIELD_HEADING = "Field:";
    static final String DATA_HEADING = "Record:";
    static final String TYPE_HEADING = "Data Type:";
    static final String RECORD = "Record ";
    static final String OF = " of ";
    static final String COLUMN_NAME_TOOLTIP =
            "Click here to change the column name";
    // About Box
    static final String FILE_FORMAT_CONVERTER = " File Format Converter";
    static final String IMAGE_PATH = "write.png";
    static final String HTML_ABOUT = "About.html";
    static final String BAD_URL = "Attempted to read a bad URL: ";
    static final String ABOUT_NOT_FOUND = "Couldn't find file: About.html";
    static final String SITE_NAME = "http://www.java-alliance.com";
    static final String BROWSER_NOT_INSTALLED = "Sorry! No Browser Installed!";
    static final String WEBSITE_NOT_EXIST =
            "Sorry! This website does not exist!!!";
    static final String OK = "OK";
    static final String ABOUT_FILE_FORMAT_CONVERTER =
            "About File Format Converter";
    static final String SPLASH_SCREEN = "/images/splash.png";

    //PDF app
    static final String PDF_LOCALE="rundll32 url.dll,FileProtocolHandler rules\\UserManual.pdf";
    static final String RULES_ERROR="PDF Reader required. Rules cannot be displayed";

}