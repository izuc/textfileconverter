package text;

import java.util.*;

/**
 * Makes JSON file from the data structure.
 * @author: JAVA Alliance
 * @date: 24 March 2009
 * @filename: JSONMaker.java
 * @version: 1.0
 */
public class JSONMaker extends FileMaker {

    /**
     * Default constructor
     */
    public JSONMaker() {
    }

    /**
     * Converts the two-dimensional data structure collection
     * to a string representing a JSON file.
     * @param columnNames - names for the columns.
     * @param data - list of the data structure.
     * @return string to be written to file in FileIO
     */
    public String serialise(Object[] columnNames, List<List<Object>> data) {
        // Number of rows
        int numRows = data.size();
        // Number of columns
        int numCols = columnNames.length;
        // Array of string to store the elements of the JSON file.
        String[] elements = new String[numCols + 2];

        // Root label is element 0
        elements[0] = ELEMENT_DOCUMENT;

        // Items label is element 1
        elements[1] = ELEMENT_ITEM;

        // Field labels
        for (int i = 2; i < numCols + 2; i++) {
            elements[i] = (String) columnNames[i - 2];
        }
        // Adds opener for the file
        addNextComponent("{" + NEW_LINE);

        // Concat root element opener
        indent(1);
        addNextComponent(DOUBLE_QUOTE + elements[1] + DOUBLE_QUOTE +
                SEMICOLON + LEFT_BRACKET + NEW_LINE);

        Object item = null;
        // For each item in the row
        for (int items = 1; items < numRows + 1; items++) {
            // Declaring an iterator of the data items.
            ListIterator<Object> it =
                    ((List) (data.get(items - 1))).listIterator();
            indent(2);
            addNextComponent(LEFT_BRACE + NEW_LINE);
            // For each field in the column.
            for (int fields = 2; fields < 2 + numCols; fields++) {
                // If the next item exist.
                if (it.hasNext()) {
                    item = it.next();
                }


                indent(2);
                // Adds next component that contains element fields in
                // double quotes and the semicolon.
                addNextComponent(DOUBLE_QUOTE + elements[fields] +
                        DOUBLE_QUOTE + SEMICOLON);
                // If the item is String then adds double quote to open it.
                if (item.getClass().getName().equals(JAVA_LANG_STRING)) {
                    addNextComponent(DOUBLE_QUOTE);
                }
                // Adds item to the CSV string
                addNextComponent(item);
                // If the next item is String then adds double quote to close it
                if (item.getClass().getName().equals(JAVA_LANG_STRING)) {
                    addNextComponent(DOUBLE_QUOTE);
                }
                // If there is another item in the list, adds comma.
                if (it.hasNext()) {
                    addNextComponent(COMMA);
                }
                // Adds new line
                addNextComponent(NEW_LINE);

            }
            indent(2);
            // If its not the last item in the record,
            if (items < numCols) {
                // Adds right brace, comma and new line.
                addNextComponent(RIGHT_BRACE + COMMA + NEW_LINE);
            } // Otherwise adds right brace and new line.
            else {
                addNextComponent(RIGHT_BRACE + NEW_LINE);
            }
        }

        indent(1);

        // Adds right bracket and new line to close the last record.
        addNextComponent(RIGHT_BRACKET + NEW_LINE);
        // Adds right brace and new line to close the file.
        addNextComponent(RIGHT_BRACE + NEW_LINE);

        // Return the CSV string.
        return getSerialisedFile();
    }
}
