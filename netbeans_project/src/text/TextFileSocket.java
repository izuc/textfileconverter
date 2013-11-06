package text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

/**
 * Text File Socket Class acts as a socket between all Text file converters
 * and the business object layer.
 * @implements: TextConstants
 * @imports FileNotFoundException, IOException, List, LinkedList.
 * @author: JAVA Alliance
 * @date: 5 March 2009
 * @filename: TextFileSocket.java
 * @version: 1.1
 */
public class TextFileSocket implements TextConstants {

    /**
     * This method chooses the file saved type and calls the other
     * appropriate methods needed.
     * @param fileName - name is the chosen name of the file.
     * @param columnNames is the list of objects.
     * @param content is the data needed to be saved.
     * @param number is the menu choice between various save as types
     * @return
     * @throws FileNotFoundException - this exception is caught at the Business
     * 		   Object layer to signal the file does not exist.
     * @throws IOException
     */
    public static boolean saveTextFile(String fileName,
            List<Object> columnNames, List<List<Object>> content, int number)
            throws FileNotFoundException, IOException {

        // This sets the content as empty by default.
        String formattedText = EMPTY;

        if (number == 1) {

            formattedText =
                    new CSVMaker().serialise(columnNames.toArray(), content);

        } else if (number == 2) {
            formattedText =
                    new JSONMaker().serialise(columnNames.toArray(), content);
        } else if (number == 3) {

            formattedText =
                    new XMLMaker().serialise(columnNames.toArray(), content);
        }

        return TextFileIO.saveContent(fileName, formattedText);
    }

    /**
     * This method chooses the file to be opened type and calls the other
     * appropriate methods needed.
     * @param name is the given name of the file
     * @param seperator is the designated data value delimination.
     * @param type
     * @return
     * @throws FileNotFoundException - this exception is caught at the Business
     * 		   				Object layer to signal the file does not exist.
     * @throws IOException - this exception is caught at the Business
     * 		   				Object layer to signal the I/O problem occurred.
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static List<List<Object>> readTextFile(String name, char seperator,
            int type) throws FileNotFoundException, IOException,
            InstantiationException, IllegalAccessException {

        // CSV default
        String content = TextFileIO.getContent(name);

        if (type == 2) {

            content = new JSONGetter().makeCSVString(content);

        }

        if (type == 3) {
            content = new XMLGetter().makeCSVString(content);
        }

        return new CSVGetter<List<Object>>(new LinkedList<Object>(), content,
                seperator);
    }
}