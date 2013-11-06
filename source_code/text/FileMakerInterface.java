package text;

import java.util.List;

/**
 * Standard interface for FileMaker, made for possible future classes.
 * @author: JAVA Alliance
 * @date: 1 May 2009
 * @filename: FileMakerInterface.java
 * @version: 1.0
 */
public interface FileMakerInterface {

    /**
     * Declaration of the common method for all file maker classes.
     * @param columnNames - names for the columns.
     * @param data - list of the data structure.
     * @return
     */
    public String serialise(Object[] columnNames, List<List<Object>> data);
}