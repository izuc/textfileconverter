package text;

/**
 * FileGetter is an abstract class which acts as a meeting point for all classes
 * which wish to open a file.
 * @implements: TextConstants, FileGetterInterface
 * @author: JAVA Alliance 
 * @date: 5 March 2009
 * @filename: FileGetter.java
 * @version: 1.2
 */
public abstract class FileGetter implements TextConstants, FileGetterInterface {

    // Stores the generated String representing the CSV file.
    private static String CSV;
    // Stores the value of the current item.
    private static String value;
    // Used in JSONGetter and XML Getter for storing the current position 
    // within the file currently being read.
    /**
     *
     */
    public static int counter;

    /**
     * Constructor. 
     * Calls Methods, that reset the CSV and Value Strings to empty.
     */
    public FileGetter() {

        clearValue();
        clearCSV();
    }

    /**
     * Resets the CSV to empty.
     */
    public static void clearCSV() {
        CSV = EMPTY;
    }

    /**
     * Adds the new character to the string representing the value of the 
     * current item.
     * @param newValue - new character to be added to the value.
     */
    public static void appendToValue(char newValue) {
        value = value + newValue;
    }

    /**
     * Resets the Value to empty.
     */
    public static void clearValue() {
        value = EMPTY;
    }

    /**
     * Getter for value
     * @return value
     */
    public static String getValue() {
        return value;
    }

    /**
     *Getter for CSV
     * @return CSV
     */
    public static String getCSV() {
        return CSV;
    }

    /**
     * Adds new item to the CSV string.
     * @param newValue - String representation of the new item to be added
     * to the CSV string.
     */
    public static void appendToCSV(String newValue) {
        CSV = CSV + newValue;
    }
}

