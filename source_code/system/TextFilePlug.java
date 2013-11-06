package system;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import text.TextFileSocket;

/**
 * This class corresponds directly to the TextFileSocket class.
 * It is the systems plug into the Text Package.
 * @author: JAVA Alliance * 
 * @date:
 * @filename: TextFilePlug.java
 * @version:
 */
public class TextFilePlug {

    /**
     * Populates the data array list.
     * @param fileName the name of the file to be opened.
     * @param comma the deliminator to be passed.
     * @param getTextType the text type that is to be opened.
     * @return the list of list objects which contain the data.
     * @throws FileNotFoundException throws all exceptions to be caught in the Bol class.
     * @throws IOException throws all exceptions to be caught in the Bol class.
     * @throws InstantiationException throws all exceptions to be caught in the Bol class.
     * @throws IllegalAccessException throws all exceptions to be caught in the Bol class.
     */
    public static List<List<Object>> populate(String fileName, char comma,
            int getTextType)
            throws FileNotFoundException,
            IOException,
            InstantiationException,
            IllegalAccessException {
        return TextFileSocket.readTextFile(fileName, comma, getTextType);
    }

    /**
     * Sends the data to be saved.
     * @param fileName the name of the file the data should be saved.
     * @param columnNames the column names the data is grouped under.
     * @param data the data to be saved in the text file.
     * @param saveAsType the type of file the data should be saved as.
     * @return whether the data saved as a text file successfully.
     * @throws FileNotFoundException throws all exceptions to be caught in the Bol class.
     * @throws IOException throws all exceptions to be caught in the Bol class.
     */
    public static boolean saveData(String fileName,
            ArrayList<Object> columnNames, List<List<Object>> data,
            int saveAsType)
            throws FileNotFoundException, IOException {

        return TextFileSocket.saveTextFile(fileName, columnNames, data, saveAsType);
    }
}
