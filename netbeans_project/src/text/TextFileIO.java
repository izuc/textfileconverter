package text;

import java.io.*;

/**
 * TextFileIO Class receives the file name and either saves the file
 * or reads the file.
 * @implements: TextConstants
 * @imports java.io
 * @author: JAVA Alliance
 * @date: 5 March 2009
 * @filename: TextFileIO.java
 * @version: 1.2
 */
public class TextFileIO implements TextConstants {

    /**
     * This method reads each line of the file assigns it to a temporary
     * variable and then inserts an end line statement.
     * @param textFile is the file object to be read.
     * @return content - a string representation of what is in the file.
     * @throws IOException - this exception is caught at the Business
     * 		   				Object layer to signal the I/O problem occurred.
     */
    private static String readText(File textFile) throws IOException {

        // It is set as null by default.
        BufferedReader inBufferedReader = null;
        // This sets the content as empty by default.
        String content = EMPTY;
        String fileContent;
        // This instantiates a new BufferedReader object.
        inBufferedReader = new BufferedReader(new FileReader(textFile));
        // Reads the first file content line.
        fileContent = inBufferedReader.readLine();

        // Continues looping while the temporary variables value exists through
        // the lines in the file to be read. Sets the content to the temporary
        // variable and then inserts the new line constant.
        while (fileContent != null) {
            content += fileContent + DELIM;
            fileContent = inBufferedReader.readLine();
        }
        // Closes the BufferedReader variable.
        inBufferedReader.close();
        return content;
    }

    /**
     * Gets one empty space.
     * @return string of one empty space.
     */
    public static String getDELIM() {
        return DELIM;
    }

    /**
     * This method opens a file and returns the file's content.
     * @param filename is the name of the file to be opened.
     * @return content - a string representation of what is in the file.
     * @throws FileNotFoundException - this exception is caught at the Business
     * 		   				Object layer to signal the file does not exist.
     * @throws IOException - this exception is caught at the Business
     * 		   				Object layer to signal the I/O problem occurred.
     */
    public static String getContent(String filename)
            throws FileNotFoundException, IOException {

        // This sets the content as empty by default.
        String content = EMPTY;

        File textFile;
        // This instantiates a new file object of filename.
        textFile = new File(filename);
        // Calls readText method to populate the content variable.
        content = readText(textFile);

        return content;
    }

    /**
     * This method calls saveText method and returns boolean variable
     * representing success or failure of saving.
     * @param filename - the name of the file to be saved as.
     * @param content - the string representation of the data to be saved.
     * @return saved - a boolean variable representing success or failure.
     * @throws FileNotFoundException - this exception is caught at the Business
     * 		   				Object layer to signal the file does not exist.
     * @throws IOException - this exception is caught at the Business
     * 		   				Object layer to signal the I/O problem occurred.
     */
    public static boolean saveContent(String filename, String content)
            throws FileNotFoundException, IOException {

        // This is set as false by default.
        boolean saved = FALSE;
        saved = saveText(filename, content);

        return saved;
    }

    /**
     * This method attempts to write a file to the disk.
     * @param filename - the name of the file to be saved as.
     * @param content - the string representation of the data to be saved.
     * @return a boolean variable representing success of saving.
     * @throws IOException - this exception is caught at the Business
     * 		   				Object layer to signal the I/O problem occurred.
     */
    public static boolean saveText(String filename, String content)
            throws IOException {

        // Initialising new file.
        File saveFile = new File(filename);
        // Initialising new buffered writer.
        BufferedWriter outBufferedWriter =
                new BufferedWriter(new FileWriter(saveFile));
        // Write content to the buffered writer.
        outBufferedWriter.write(content);
        // Closes the buffered writer.
        outBufferedWriter.close();

        return TRUE;
    }
}


