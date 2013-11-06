package text;

import java.util.*;

/**
 * Makes XML file from the data structure.
 * @author: JAVA Alliance
 * @date: 24 March 2009
 * @filename: XMLMaker.java
 * @version: 1.0
 */
public class XMLMaker extends FileMaker {

    /**
     * Default constructor.
     */
    public XMLMaker() {
    }

    /**
     * Converts the two-dimensional data structure collection_
     * to a string representing an XML file.
     * @param columnNames - names for the columns.
     * @param data - list of the data structure.
     * @return string to be written to file in FileIO.
     */
    public String serialise(Object[] columnNames, List<List<Object>> data) {
        // Number of rows
        int numRows = data.size();
        // Number of columns
        int numCols = columnNames.length;
        // Array of string to store the elements of the XML file.
        String[] elements = new String[numRows + numCols + 1];

        // 1 root label [0]
        // Item labels [1 to numRows]
        // Field labels [numRows+1 to (numRows+1+numCols)
        //  - given values of columnName[0 to numCols-1]
        //  - given label of column 1 to numCols

        // Root label
        elements[0] = ELEMENT_DOCUMENT;

        // Item labels
        for (int d = 1; d <= numRows; d++) {
            elements[d] = ELEMENT_ITEM;
        }

        // Field labels
        for (int i = numRows + 1; i < numRows + 1 + numCols; i++) {
            elements[i] = (String) columnNames[i - numRows - 1];
        }
        // Adds XML blah and new line to the CSV string.
        addNextComponent(CREATED_BY_ZYX + NEW_LINE);
        // Adds more XML blah and new line to the CSV string.
        addNextComponent(XML_VERSION + NEW_LINE);

        // Concat root element opener.
        addNextComponent(LEFT_ANGLE + elements[0] + RIGHT_ANGLE + NEW_LINE);

        // Declares the variable, that is doing to store the current item.
        Object item = null;
        // For each row
        for (int items = 1; items <= numRows; items++) {

            indent(1);
            // Adds left angle
            addNextComponent(LEFT_ANGLE);
            // Adds other standard necessary XML code
            addNextComponent(elements[items] + ID_EQUALS + items +
                    DOUBLE_QUOTE);
            addNextComponent(RIGHT_ANGLE + NEW_LINE);
            // Declaring an iterator of the data items.
            ListIterator<Object> it =
                    ((List) (data.get(items - 1))).listIterator();
            // For each field in the column.
            for (int fields =
                    1 + numRows; fields < 1 + numRows + numCols; fields++) {
                // If the next item exist.
                if (it.hasNext()) {
                    item = it.next();
                }
                indent(2);
                // Adds next component that contains standard necessary XML code
                addNextComponent(LEFT_ANGLE + elements[fields] + RIGHT_ANGLE);

                addNextComponent(item);

                addNextComponent(LEFT_ANGLE + FORWARD_SLASH + elements[fields] +
                        RIGHT_ANGLE + NEW_LINE);

            }

            indent(1);
            // Adds the next element with opening and closing markers 
            // and new line.
            addNextComponent(LEFT_ANGLE + FORWARD_SLASH);
            addNextComponent(elements[items]);
            addNextComponent(RIGHT_ANGLE + NEW_LINE);
        }
        // Adds the next element with opening and closing markers 
        // and new line.
        addNextComponent(LEFT_ANGLE + FORWARD_SLASH + elements[0] +
                RIGHT_ANGLE + NEW_LINE);

        // Returns the CSV string.
        return getSerialisedFile();
    }
}

