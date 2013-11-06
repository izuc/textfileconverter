//Note: This class is 'functional' only
//It is in no way a generic XML reader
//To be beautified upon donations of enormous sums of money
package text;

/**
 * Retieves data from a XLM file saved in TextConvertor format 
 * into the data structure.
 * @author: JAVA Alliance
 * @date: 1 May 2009
 * @filename: XMLGetter.java
 * @version: 1.0
 */
public class XMLGetter extends FileGetter {

    /**
     * Default Constructor.
     */
    public XMLGetter() {
    }
    // Stores the file's content.
    private static String content;
    // Temporary stores a value used to extract the data.
    private String tmp;

    /**
     * Constructs a string representing CSV file from the XML file content.
     * @param content_ - XML file content.
     * @return getCSV - a string representing CSV.
     */
    public String makeCSVString(String content_) {

        content = content_;
        // Adds column names to the string representing CSV.
        addColumnNames();
        // Adds data structure to the string representing CSV.
        addData();
        // Returns a string to be stored in CSV file.
        return getCSV();
    }

    /**
     * Adds column names to the content string.
     */
    public void addColumnNames() {

        counter = 0;
        // Examines the next three characters of the content.
        String ID_or_Name = String.valueOf(content.charAt(counter)) +
                String.valueOf(content.charAt(counter + 1)) +
                String.valueOf(content.charAt(counter + 2));

        // Skip over characters until find the start of the first id element.
        while (!(ID_or_Name.equalsIgnoreCase(" id")) ||
                (ID_or_Name.equalsIgnoreCase(" na"))) {
            counter++;
            ID_or_Name = String.valueOf(content.charAt(counter)) +
                    String.valueOf(content.charAt(counter + 1)) +
                    String.valueOf(content.charAt(counter + 2));

        }
        // Until finds the start of element marker, increment the counter. 
        while (!(content.charAt(counter) == '<')) {
            counter++;
        }
        // Increment the counter.
        counter++;
        // Reset variables to empty.
        ID_or_Name = "";
        tmp = "";
        // Until finds the start of the next element, 
        while (!(ID_or_Name.equalsIgnoreCase(" id")) &&
                !(ID_or_Name.equalsIgnoreCase(" na")) && !(tmp.equals("</"))) {
            // Appends the characters representing the current element, with quotes
            appendToValue('\"');
            while (!(content.charAt(counter) == '>')) {

                appendToValue(content.charAt(counter));
                counter++;
            }
            appendToValue('\"');

            // Skip over characters until the end of the line.
            while (!(content.charAt(counter) == '\n')) {
                counter++;
            }
            // Skip over characters until the start of the next element.
            while (!(content.charAt(counter) == '<')) {

                counter++;
            }
            // Stores the next two characters in a temporary string.
            tmp = String.valueOf(content.charAt(counter)) +
                    String.valueOf(content.charAt(counter + 1));

            appendToCSV(getValue());
            // If the temporary string is not an end of record marker
            if (!(tmp.equals("</"))) {
                // Add a comma to the CSV string.
                appendToCSV(",");
            }
            // Increments the counter
            counter++;
            // Resets the value variable
            clearValue();
            // The string examines the next three characters of the content.
            ID_or_Name = String.valueOf(content.charAt(counter)) +
                    String.valueOf(content.charAt(counter + 1)) +
                    String.valueOf(content.charAt(counter + 2));
        }
        // Appends a new line chracter to the value variable
        appendToValue('\n');
        // Appends the value to the CSV string.
        appendToCSV(getValue());
    }

    /**
     * Adds the data contained in the XML file to the CSV string.
     */
    public void addData() {

        try {
            // Resets the counter
            counter = 0;
            // Resets the value variable
            clearValue();
            // Until reach the end of the content
            while (counter < content.length()) {
                // Examines the next three characters of the content.
                String ID_or_Name = String.valueOf(content.charAt(counter)) +
                        String.valueOf(content.charAt(counter + 1)) + String.valueOf(content.charAt(counter + 2));
                // Until finds the start of the next element, 
                while (!(ID_or_Name.equalsIgnoreCase(" id")) ||
                        (ID_or_Name.equalsIgnoreCase(" na"))) {
                    counter++;
                    // Examines the next three characters of the content.
                    ID_or_Name = String.valueOf(content.charAt(counter)) +
                            String.valueOf(content.charAt(counter + 1)) +
                            String.valueOf(content.charAt(counter + 2));
                }
                // Until reaches the end of the element, increments the counter
                while (!(content.charAt(counter) == '>')) {
                    counter++;
                }

                counter++;
                // Reset variables to empty.
                ID_or_Name = "";
                tmp = "";
                // Until finds the start of the next record, 
                while (!(ID_or_Name.equalsIgnoreCase(" id")) &&
                        !(ID_or_Name.equalsIgnoreCase(" na")) &&
                        !(tmp.equals("</"))) {
                    // Until reaches the end of the element,
                    // increments the counter
                    while (!(content.charAt(counter) == '>')) {
                        counter++;
                    }
                    counter++;
                    // Adds quotes to the element.
                    appendToValue('\"');
                    // Until reaches the start of the next element
                    while (!(content.charAt(counter) == '<')) {
                        // Adds the characters within the current element to
                        // the content and increment the counter.
                        appendToValue(content.charAt(counter));
                        counter++;
                    }
                    // Adds quotes to the element.
                    appendToValue('\"');

                    // Until reaches the new line, increments the counter.
                    while (!(content.charAt(counter) == '\n')) {
                        counter++;
                    }
                    // Until reaches the start of the next element, 
                    // increments the counter.
                    while (!(content.charAt(counter) == '<')) {

                        counter++;
                    }

                    // Stores the next two characters in a temporary string.
                    tmp = String.valueOf(content.charAt(counter)) +
                            String.valueOf(content.charAt(counter + 1));

                    // Adds the current value to the CSV string.
                    appendToCSV(getValue());
                    // If the temporary string is not an end of element marker
                    if (!(tmp.equals("</"))) {
                        // Adds a comma
                        appendToCSV(",");

                    } // Otherwise adds a new line to value and adds value
                    // to the CSV String.
                    else {
                        appendToValue('\n');
                        appendToCSV(getValue());
                    }
                    // Increments the counter.
                    counter++;
                    // Resets the value variable.
                    clearValue();
                    // Examines the next three characters of the content.
                    ID_or_Name = String.valueOf(content.charAt(counter)) +
                            String.valueOf(content.charAt(counter + 1)) +
                            String.valueOf(content.charAt(counter + 2));
                }
            }
        } catch (Exception e) {
        }
    }
}
