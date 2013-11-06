
package cli;

/**
 * Constants for the CLI package.
 * @author: JAVA Alliance
 * @date: 1 May 2009
 * @filename: MenuConstants.java
 * @version: 1.0
 */

import java.util.ArrayList;

public interface MenuConstants {

	// File Types.
    final static String[] IOTypes = {"CSV", "JSON", "XML"};
    // Menu Bar
    final static String[] myMenuBar = {"File", "Options", "Help", "Exit"};
    // Submenus
    final static ArrayList subMenus = new ArrayList();
    // FILE
    final static String[] subMenu1 = 
    					{"Read Text File", "Save As Text", 
    						"Export to Database", "Return to Main"}; 
    // OPTIONS
    final static String[] subMenu2 = 
    						{"Change column name", "Modify contents of cell", 
    						"Delete row", "Insert row", "Delete column", 
    						"Insert column", "Return to Main"}; 
    // HELP
    final static String[] subMenu3 = {"View Help files", "Return to Main"};                         
    //
    // User input prompts
    final static String ENTER_HOST = "Please enter a hostname: ";
    final static String ENTER_PASSWORD = "Please enter a password: ";
    final static String ENTER_USER_NAME = "Please enter a username: ";
    final static String ENTER_DATABASE = "Please enter database: ";
    final static String ENTER_TABLE = "Please enter table name: ";
    final static String SUCCESS_MESSAGE = "Success";
    final static String HELPFUL_MESSAGE = 
    						"\nYou\'re on your own buddy\nThis is a CLI ...";
    final static String ENTER_TEXTFILE_TYPE = "Enter text file type: ";
    final static String ENTER_DBMS_TYPE = "Enter connection details: ";
    final static String ENTER_FILE_PATH = "Enter full file path and name: ";
    final static String OUTPUT_ERROR = "\nThere is no data to output.\n";
}