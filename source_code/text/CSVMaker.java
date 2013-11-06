package text;

import java.util.*;

/**
 * Generates CSV file from the data structure.
 * @author: JAVA Alliance
 * @date: 10 April 2009
 * @filename: CSVMaker.java
 * @version: 1.0
 */
public class CSVMaker extends FileMaker {

    /**
     * Default constructor.
     */
    public CSVMaker() {
    }
    private static Object item = null;

    /**
     * Converts the two-dimensional data structure collection.
     * to a string representing a CSV file.
     * @param columnNames - names for the columns.
     * @param data - list of the data structure.
     * @return string (to be written to file in FileIO)
     */
    public String serialise(Object[] columnNames, List<List<Object>> data) {

        // Number of rows
        int numRows = data.size();
        // Number of columns
        int numCols = columnNames.length;

        // Adds the column headers to the csv file.
        for (int i = 0; i < numCols; i++) {
            addNextComponent('\"');
            addNextComponent(columnNames[i]);
            addNextComponent('\"');
            if (i < numCols - 1) {
                addNextComponent(COMMA);
            }
        }

        addNextComponent(NEW_LINE);

        // Adds data rows to the csv file.
        // For all the rows in a data structure:
        for (int items = 0; items < numRows; items++) {
            // Declaring an iterator of the data items.
            ListIterator<Object> it = ((List) (data.get(items))).listIterator();
            // For each item in the row 
            for (int fields = 0; fields < numCols; fields++) {
                // If the next item exist
                if (it.hasNext()) {

                    item = it.next();
                }
                // Adds the item to the file and adds comma after the item
                // if it's not the last item.
                addNextComponent(DOUBLE_QUOTE + item + DOUBLE_QUOTE);
                if (fields < numCols - 1) {
                    addNextComponent(COMMA);
                }
            }
            // Adds a new line
            addNextComponent(NEW_LINE);
        }
        // Returns String to be written to file in FileIO.
        return getSerialisedFile();
    }
}