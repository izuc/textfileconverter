
package cli;

/**
 * Method to inplement command line interface to the text converter API 
 * @author: JAVA Alliance
 * @date: 4 June 2009
 * @filename: CLI.java
 * @version: 1.1
 */

import system.*;
import text.*;

public class CLI implements MenuConstants {

	// Stores the selected top level menu list item.
    private static int selMenuBar;
	// Stores the selected sub level menu list item.
    private static int selSubMenu;
    // Reference to the Bol.
    private Bol bol;
    // Stores entered file name.
    private String filename;
    // Stores entered file type.
    private int type;

    /**
     * Constructor.
     * @param bol - Reference to the Bol.
     */
    public CLI(Bol bol) {

        this.bol = bol;

        // Adds required sub menus to the menu system.
        subMenus.add(subMenu1);
        subMenus.add(subMenu2);
        subMenus.add(subMenu3);

        // Resets menu selections
        selMenuBar = 0;
        selSubMenu = 0;

        // Adds empty line
        System.out.println();

        // Gets the selection from top level menu
        selMenuBar = TextMenu.menu(myMenuBar);
        // While user has not chosen to exit
        while (!(selMenuBar == myMenuBar.length)) //EXIT
        {
        	// Display the sub menu for chosen top level menu item.
            CLI_SubMenu((String[]) subMenus.get(selMenuBar - 1));
            System.out.println();
            selMenuBar = TextMenu.menu(myMenuBar);
        }

        // Displays cute messages.
        System.out.println("----------------------------\n");
        System.out.println("Goodbye :)\n");
    }

    /**
     * Displays sub menu and gets selections.
     * @param subMenu - list of menu items.
     */
    private void CLI_SubMenu(String[] subMenu) {
        // Resets the sub menu item selection
    	selSubMenu = 0;
        // While user has not chosen to return to top level menu
        while (!(selSubMenu == subMenu.length)) //EXIT
        {
        	// Displays the sub menu
            System.out.println("\n" + myMenuBar[selMenuBar - 1] + " Menu");
            System.out.println("----------------------\n");
            // Gets selection from sub menu. 
            selSubMenu = TextMenu.menu(subMenu);
            // If the selected menu item is the first one
            if (selMenuBar == 1) { // File
                switch (selSubMenu) {
                    case 1: // Read Text File
                        try {
                            getIOFileInfo();
                            // Populate the data structure from the entered
                            // filename and file type.
                            this.bol.populate(filename, 
                            				Bol.IOType.valueOf(IOTypes[type]));
                            // Sets default column names.
                            this.bol.setDefaultColumnNames();
                            // Displays the loaded data structure on the screen.
                            outputDataToScreen();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    case 2: // Save as Text

                        try {
                            getIOFileInfo();
                            this.bol.saveData(Bol.IOType.
                            				valueOf(IOTypes[type]), filename);

                            //output the file to screen
                            //1 - read the generated file back in as a String                            
                            String content = TextFileIO.getContent(filename);

                            if (content != null) {
                                System.out.println(SUCCESS_MESSAGE);
                                System.out.println(content);
                            }
                        //2- display it

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    case 3:  //Export to database

                        System.out.println(ENTER_DBMS_TYPE);
                        try {
                            this.bol.getConnection(ReadKb.getString(ENTER_HOST),
                                    TextMenu.menu(bol.getVendors()) - 1,
                                    ReadKb.getString(ENTER_USER_NAME),
                                    ReadKb.getString(ENTER_PASSWORD));
                            this.bol.sendDataToDBMS(ReadKb.
                            				getString(ENTER_DATABASE),
                                    ReadKb.getString(ENTER_TABLE));
                            System.out.println(SUCCESS_MESSAGE);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    default:
                        break;
                }
            }
            else if (selMenuBar == 2) { //OPTIONS MENU
                switch (selSubMenu) {
                    case 1: //Modify column name

                        try {
                            this.bol.modifyColumnName(ReadKb.
                            	getInt("Enter Column number: "), 
                            	ReadKb.getString("Enter new Column name: "));

                            outputDataToScreen();

                            System.out.println(SUCCESS_MESSAGE);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;


                    case 2: // Modify individual cell
                        try {

                            this.bol.modifyCell(ReadKb.getInt("Enter row:"), 
                            		ReadKb.getInt("Enter Column number: "), 
                            		ReadKb.getString("Enter new content: "));
                            outputDataToScreen();
                            System.out.println(SUCCESS_MESSAGE);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    case 3: // Delete row
                        try {

                            this.bol.deleteRow(ReadKb.getInt("Enter row:"));
                            outputDataToScreen();
                            System.out.println(SUCCESS_MESSAGE);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4: // Insert row
                        try {

                            this.bol.insertRow(ReadKb.
                            			getInt("Enter position to insert:"));
                            outputDataToScreen();
                            System.out.println(SUCCESS_MESSAGE);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    case 5: // Delete column
                        try {

                            this.bol.deleteColumn(ReadKb.
                            						getInt("Select column:"));
                            outputDataToScreen();
                            System.out.println(SUCCESS_MESSAGE);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    case 6: // Delete column
                        try {

                            this.bol.insertColumn(ReadKb.
                            			getInt("Enter position to insert:"));
                            outputDataToScreen();
                            System.out.println(SUCCESS_MESSAGE);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    default:
                        break;
                }
            }
            else if (selMenuBar == 3) { // Help menu
                switch (selSubMenu) {
                    case 1: // CLI 'Help'

                        System.out.println(HELPFUL_MESSAGE);
                        break;

                    default:
                        break;
                }
            }
        }
    }

    /**
     * Dumps the current system data structure to the screen
     */
    private void outputDataToScreen() {

        if (this.bol.getData().size() > 0) {

            System.out.println(SUCCESS_MESSAGE);

            System.out.println(this.bol.getColumnNames());

            for (Object ob : this.bol.getData()) {
                System.out.println(ob);
            }
        }
        else {
        	// Displays error message.
            System.out.println(OUTPUT_ERROR);
        }
    }

    /**
     * Prompts for and reads the required File path and Filetype from keyboard.
     * Stored in 'type' variable (integer)
     *
     */
    private void getIOFileInfo() {
    	// Gets string of the file path
        filename = ReadKb.getString(ENTER_FILE_PATH);
        // Displays a menu that prompt for file type.
        System.out.println(ENTER_TEXTFILE_TYPE);
        // Gets the filetype from the displayed menu.
        type = TextMenu.menu(IOTypes) - 1;
    }
}
