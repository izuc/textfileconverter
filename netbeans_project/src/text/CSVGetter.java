package text;

import java.util.List;
import java.util.StringTokenizer;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The CSV Getter is overloaded, which supports reading from a variety of formats,
 * it accepts a file object, or a string of content which gets read, and passed through
 * a method to separate the content based on a comma. The commas are found by looping through each character and
 * the system also detects quotes - therefore it allows commas to be inside of quoted text. The content doesn't need to
 * be separated on a new line, it can detect when content has been sparsed out over multiple lines.
 * Overloaded and Chained Constructors.
 *1: 	new CSVGetter<List<Object>>(TextFileIO.getContent(name)); <br />
 *2: 	new CSVGetter<List<Object>>(new File(name));<br />
 *3: 	new CSVGetter<List<Object>>(new LinkedList<Object>(), new File(name));<br />
 *4: 	new CSVGetter<List<Object>>(new Stack<Object>(), TextFileIO.getContent(name));<br />
 *5:	new CSVGetter<List<Object>>(new Vector<Object>(), TextFileIO.getContent(name));<br />
 *6:	new CSVGetter<List<Object>>(new ArrayList<Object>(), TextFileIO.getContent(name));<br />
 * @author: JAVA Alliance
 * @date: 1st March 2009
 * @filename: CSVGetter.java
 * @version: 1.1
 */
public class CSVGetter<L extends List> extends LinkedList<L> {

    private static final long serialVersionUID = 1L;
    private static final String NEW_LINE = "\n";
    private static final char SPACE = ' ';
    private static final char QUOTE = '"';
    private static final char COMMA = ',';

    /**
     * The constructors are chained, and the default constructor accepts a 
     * File Object of the desired file to be read, and creates the enternal 
     * structure as a LinkedList.
     * @param file - A File Object of the Desired File.
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.io.IOException
     */
    @SuppressWarnings("unchecked")
    public CSVGetter(File file) throws InstantiationException, IllegalAccessException, IOException {
        this(((L) new LinkedList<Object>()), file, COMMA);
    }

    /**
     * The chained constructor accepts a instance of a List, and a File Object.
     * @param list - Accepts a instantiated instance of the desired inner List;
     * which could be anything that extends the List super type.
     * @param file - The File Object of the desired File to be read.
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public CSVGetter(L list, File file)
            throws InstantiationException, IllegalAccessException, IOException {
        this(list, file, COMMA);
    }

    /**
     * The chained constructor accepts a list instance, a file object, and a separating char.
     * @param list - The instance of the desired inner List.
     * @param file - The desired File to be separated
     * @param separator - The separator character.
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public CSVGetter(L list, File file, char separator)
            throws InstantiationException, IllegalAccessException, IOException {
        this(list, file, separator, QUOTE);
    }

    /**
     * The chained contructor accepts a List instance, a file object, a separator, and a quote char.
     * @param list - The instance of the desired List.
     * @param file - The desired File object.
     * @param separator - The character to be separated at.
     * @param quote - The Quote around the text, which could possibly contain separating characters.
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public CSVGetter(L list, File file, char separator, char quote)
            throws InstantiationException,
            IllegalAccessException, IOException {
        this(list, file, separator, quote, NEW_LINE);
    }

    /**
     * The chained constructor is the final chained constructor used with the File object.
     * It directly from here goes into the process for converting the content of the file.
     * -- It contains a inner File content retrieval method for getting the content from the object,
     * allowing for the class to work indepently if it is taken out of the text file package at some point.
     * @param list - A instance of the inner List.
     * @param file - A desired File Object
     * @param separator - Separating Character
     * @param quote - Quota Character
     * @param newline - New Line Character
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public CSVGetter(L list, File file, char separator, char quote,
            String newline) throws InstantiationException,
            IllegalAccessException, IOException {
        this.readSource(list, this.getContent(file, newline), separator, quote, newline);
    }

    /**
     * The series of chained constructors don't accept a file Object. Instead it accepts
     * the String of the content. It instantiates a default inner list of Linked List,
     * and has the separator value of a Comma.
     * @param content String - The String content, which has already been grabbed from a file.
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public CSVGetter(String content) throws InstantiationException, IllegalAccessException {
        this(((L) new LinkedList<Object>()), content, COMMA);
    }

    /**
     * The chained contructor receives a instance of a List, and the String content.
     * @param list - A instance of the desired List format.
     * @param content - A String of the content to be parsed.
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public CSVGetter(L list, String content)
            throws InstantiationException, IllegalAccessException {
        this(list, content, COMMA);
    }

    /**
     * The chained constructor recieves a List, a String of Content, and a Char
     * separating value. It by default places the quote char as the quoting character.
     * @param list - Instance of a List.
     * @param content - String of content.
     * @param separator - Char separating value.
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public CSVGetter(L list, String content, char separator)
            throws InstantiationException, IllegalAccessException {
        this(list, content, separator, QUOTE);
    }

    /**
     * The chained constructor receives a instance list, a string of content, a char separating value,
     * and a quoting char.
     * @param list - Instance of a List.
     * @param content - String of content.
     * @param separator - Char separating value.
     * @param quote - Quota Character
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public CSVGetter(L list, String content, char separator, char quote)
            throws InstantiationException, IllegalAccessException {
        this(list, content, separator, quote, NEW_LINE);
    }

    /**
     * This is the final chained constructor for the use with a already existing String of content.
     * It now calls the readSource method which does the processing.
     * @param list - A instance of the inner List.
     * @param content -String of content.
     * @param separator - Separating Character
     * @param quote - Quota Character
     * @param newline - New Line Character
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public CSVGetter(L list, String content, char separator, char quote,
            String newline)
            throws InstantiationException, IllegalAccessException {
        this.readSource(list, content, separator, quote, newline);
    }

    /**
     * The readSource method receives the inner instance of the List, the String content,
     * the char separating value, and a char quote. It loops through each line tokenized on the
     * new line separating value. It creates a new instance of the inner list received for each 
     * new line (which is a record); the inner list is used for the adding of each separated element.
     * 
     * The reader itself is very flexible. It can read a file sparsed out over multiple lines, and the file does
     * not have to be evenly structured. It will only separate on the separating value at times where it is not within
     * the quoting character, and if its still inside the quotes once it has reached the end of the line - it will continue to
     * the next. For each of the lines, it will loop through each character - which provides a more structured way of 
     * deciphering each element.
     * 
     * For each of the elements it will create a string buffer, and the string buffer gets characters appended for each of the
     * until it has reached a separating value. It will add that element into the inner list instance, and create a new
     * string buffer for the next element. Once the line has been completed, it will add the instance of the inner list to itself (which is the 
     * class, which extends LinkedList) and creates a new instance of the inner list and starts the cycle again until it
     * has reached the end of the content.
     * @param list - Instance of the inner list.
     * @param content - The content of String which will get separated.
     * @param separator - The separating value
     * @param quote - The quoting character - for containing occurences of the separating value.
     * @param newline - The line breaking character, which the content string gets tokenized at.
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    private void readSource(L list, String content, char separator, char quote,
            String newline)
            throws InstantiationException, IllegalAccessException {
        StringTokenizer lines = new StringTokenizer(content, newline);
        while (lines.hasMoreTokens()) {
            String line = lines.nextToken();
            boolean insideQuotes = false;
            list = (L) list.getClass().newInstance(); // Used to create a new instance of the unknown object received.
            StringBuffer element = new StringBuffer();
            do { // Completes a do while loop, while its inside the quotes.
                if (insideQuotes) { // If its inside the quotes, it grabs the contents of the next line, then recalls the method.
                    element.append(SPACE);
                    line = lines.nextToken();
                    if (line == null) {
                        break;
                    }
                }
                for (int i = 0; i < line.length(); i++) { // Loops through all the characters contained in the String
                    char character = line.charAt(i); // Gets the character from the line based upon the index.
                    if (character == quote) { // If the first character is a quote,.
                        insideQuotes = !insideQuotes; // It flags that it is inside the quotes.
                    } else if ((character == separator) && (!(insideQuotes))) { // If the character is a separater and its outside the quotes.
                        list.add(element.toString()); // It adds the finished char element to the list
                        element = new StringBuffer(); // Recreates the StringBuffer to clear it.
                    } else {
                        element.append(character); // Otherwise it just adds the char to the StringBuffer until it reaches one of the other conditions.
                    }
                }
            } while (insideQuotes);
            list.add(element.toString()); // Adds the last element into the List.
            this.add(list);
        }
    }

    /**
     * This method isn't used in the TextFilePackage, but it adds additional flexibility for further expansion, and for uses external to this application.
     * It allows the CSV getter to act independent from the other classes; thus giving it more freedom.
     * @param file Object which will get read.
     * @param newline - separating the newline character.
     * @return String of content.
     * @throws java.io.IOException
     */
    // 
    private String getContent(File file, String newline) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuffer content = new StringBuffer();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            if (line != null) {
                content.append(line + newline);
            }
        }
        br.close();
        return content.toString();
    }

    /**
     * The getElement method provides a way of retrieving the stored element within a specified
     * record.
     * @param row Integer - the index of the record in the structure.
     * @param col Integer - The stored element
     * @return
     */
    public Object getElement(int row, int col) {
        return this.get(row).get(col);
    }

    /**
     * The getSet method will provide a way of adding the list to a Map. By retrieving the
     * HasSet, it can easily be placed into a map structure. Therefore supporting Map structures.
     * @return HashSet
     */
    @SuppressWarnings("unchecked")
    public Set getSet() {
        return new HashSet(this);
    }

    /**
     * The getArray method will convert the CSVGetter into a 2 Dimensional Array of Objects.
     * @return Object[][].
     */
    public Object[][] getArray() {
        Object[][] array = new Object[this.size()][];
        for (int i = 0; i < array.length; i++) {
            array[i] = this.get(i).toArray();
        }
        return array;
    }
}
