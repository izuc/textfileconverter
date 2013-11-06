package text;

/**
 * Standard interface for FileGetter, made for possible future classes.
 * @author: JAVA Alliance
 * @date: 1 May 2009
 * @filename: FileGetterInterface.java
 * @version: 1.0
 */
public interface FileGetterInterface {

    /**
     * Declaration of the method to make a CSV String from file content
     * @param content_
     * @return
     */
    String makeCSVString(String content_);

    /**
     * Declaration of the method for adding column names to the file.
     */
    void addColumnNames();

    /**
     * Declaration of the method for adding data structure to the file.
     */
    void addData();
}
