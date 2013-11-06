package text;

/**
 * A class which contains methods and variables common to all current and 
 * potential TextMaker classes.
 * @implements: TextConstants, FileMakerInterface
 * @author: JAVA Alliance
 * @date: 5 March 2009
 * @filename: FileMaker.java
 * @version: 1.2
 */
public abstract class FileMaker implements TextConstants, FileMakerInterface {

    // The String which will receive the generated Text File String content.
    private static String serialisedFile;

    /**
     * Constructor for the FileMaker class
     */
    public FileMaker() {

        serialisedFile = EMPTY;

    }

    /**
     * Concatenates a new component to the serialisedFile structure.
     * @param component
     */
    public static void addNextComponent(Object component) {

        serialisedFile += component.toString();

    }

    /**
     * Returns the private FileMaker variable serialisedFile.
     * @return String serialisedFile
     */
    public static String getSerialisedFile() {
        return serialisedFile;
    }

    /**
     * Adds a number of indents (4 spaces) to the serialised file.
     * @param indents - number of indents to add
     */
    public static void indent(int indents) {

        for (int i = 0; i < indents; i++) {
            serialisedFile += FOUR_SPACES;
        }
    }
}

