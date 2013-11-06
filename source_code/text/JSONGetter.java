package text;

/**
 * Retrieves data from a JSON file saved in TextConvertor format
 * into the data structure.
 * @author: JAVA Alliance
 * @date: 3 May 2009
 * @filename: JSONGetter.java
 * @version: 1.0
 */
public class JSONGetter extends FileGetter {

    /**
     * Default constructor.
     */
    public JSONGetter() {
    }
    // Stores the file's content.
    private static String content;

    /**
     * Constructs a string representing CSV file from the JSON file content.
     * @param content_ - JSON file content.
     * @return getCSV - a string representing CSV.
     */
    public String makeCSVString(String content_) {

        content = content_;
        // Adds column names to the string representing CSV.
        addColumnNames();
        // Adds data structure to the string representing CSV.
        addData();
        // Returns a string representing CSV.
        return getCSV();
    }

    /**
     * Adds column names to the content string.
     */
    public void addColumnNames() {

        counter = 0;
        // Increments counter until find the start of first record
        while (!(content.charAt(counter) == '{')) {
            counter++;
        }
        while (!(content.charAt(counter) == '[')) {
            counter++;
        }
        while (!(content.charAt(counter) == '{')) {
            counter++;
        }

        // Until the end of the first record is reached
        while (!(content.charAt(counter) == '}')) {
            if ((content.charAt(counter) == '\"')) {
                // Appends quotes
                appendToValue('\"');
                // Goes to the next position in the content string.
                counter++;
                // Until find the next quote (indicating end of column name)
                while (!(content.charAt(counter) == '\"')) {
                    // Append the character at the current counter position.
                    appendToValue(content.charAt(counter));
                    // Goes to the next position in the content string.
                    counter++;
                }
                // Append closing quotes to the current column name
                appendToValue('\"');

                // Skip over everything until the end of the line
                while (!(content.charAt(counter) == '\n')) {
                    counter++;
                }

                // If the last char before the current one is comma, (ie not
                // last field) append comma at the current counter position. 
                if (content.charAt(counter - 1) == ',') {
                    appendToValue(',');
                }
            }
            // Goes to the next position in the content string.
            counter++;
        }

        // Appends new line to the current counter position.
        appendToValue('\n');
        // Appends the current item to the CSV String.
        appendToCSV(getValue());
    }

    /**
     * Adds the data contained in the JSON file to the CSV string.
     */
    public void addData() {

        try {

            counter = 0;
            // Resets the Value to empty.
            clearValue();

            // Increments counter until find the first data item.
            while (!(content.charAt(counter) == '{')) {
                counter++;
            }
            counter++;
            while (!(content.charAt(counter) == '[')) {
                counter++;
            }
            counter++;
            // Until the end of file is reached, increments the counter.
            while (!(content.charAt(counter) == ']')) {
                counter++;
                // Until start of a record is reached, increments the counter.
                while (!(content.charAt(counter) == '{')) {
                    counter++;
                }
                // Until the end of the record is reached, 
                // increments the counter.
                while (!(content.charAt(counter) == '}')) {
                    // Until find the beginning of the data
                    if ((content.charAt(counter) == ':')) {
                        // increments the counter
                        counter++;

                        // Until find the end of the line
                        while (!(content.charAt(counter) == '\n')) {
                            // Adds the data on the line to the content.
                            appendToValue(content.charAt(counter));
                            counter++;
                        }
                    }
                    counter++;
                }
                // Appends new line to the current counter position.
                appendToValue('\n');
                // Appends the current line's value to the CSV String.
                appendToCSV(getValue());
                // Resets the Value to empty.
                clearValue();
            }
        } catch (Exception e) {
        }
    }
}

