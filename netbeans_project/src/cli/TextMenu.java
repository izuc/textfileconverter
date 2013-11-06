
package cli;

/**
 * Implements a menu system for the command line interface.
 * @date: 1 May 2009
 * @filename: TextMenu.java
 * @version: 1.0
 */

public class TextMenu {

	/**
	 * Displays the Menu and returns the selection from it.
	 * @param menuOptions_
	 * @return user selection.
	 */
    public static int menu(String[] menuOptions_) {
        int selection;

        // Display Menu Options.
        displayMenuOptions(menuOptions_);

        // Get a valid selection from menu.
        selection = getValidSelection(menuOptions_);

        return selection;
    }

    /**
     * Displays menu options.
     * @param menuOptions_ - string array of menu options.
     */
    private static void displayMenuOptions(String[] menuOptions_) {
        for (int i = 1; i <= menuOptions_.length; i++) {
            System.out.print("[");

            printInWidth(i, 2);

            System.out.print("]\t" + menuOptions_[i - 1] + "\n");

        }

        System.out.println();
    }

    /**
     * Gets valid selection from menu options.
     * @param menuOptions_ - string array of menu options.
     * @return valid selection.
     */
    private static int getValidSelection(String[] menuOptions_) {
        // Variable to store selection.
    	int selection;
        // Displays message that prompts for selection.
        System.out.print("Enter your selection: ");
        // Gets integer from keyboard input
        selection = ReadKb.getInt();
        // While the selection is not within the range of menu options, 
        // Displays error message and prompts for another selection.
        while (selection < 1 || selection > menuOptions_.length) {
            System.out.print(">>> Invalid selection\n");

            System.out.print("Enter your selection: ");
            // Gets integer from keyboard input
            selection = ReadKb.getInt();

        }
        
        // return the valid selection
        return selection;
    }


    /**
     * Displays an int in width.
     * Use negative value for size to left align.
     * @param value of the int
     * @param required width to display the integer 
     */
    private static void printInWidth(final int value, int size_) {
        System.out.printf("%" + size_ + "s", value);
    }
}
