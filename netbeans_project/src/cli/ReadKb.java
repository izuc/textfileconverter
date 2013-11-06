/*
 ** @Author: JAVA Alliance
 */
package cli;

// File:	ReadKb.java
// Purpose:	Provide methods to read an int, a double and String from the keyboard
// From:	The Java Way 2nd Edition Chapter 6, Listing 6.2

// modified to make static methods
import java.util.Scanner;				// Access the I/O package

/**
 * Another collection of methods for getting keyboard input
 * from The Java Way text
 * @author Gerard Sparke The Java Way 2nd edition
 */
public class ReadKb {

    static Scanner scanner = new Scanner(System.in);

    /**
     * Reads a Double from the keyboard
     * @author Gerard Sparke The Java Way 2nd edition
     */
    public static double getDouble() { 			// Read a double
        double d = 0.0;

        if (scanner.hasNextDouble()) {		// Test that user entered a double
            d = scanner.nextDouble();	// Read a double
        }
        else {					// Non-numeric data
            String s = scanner.next();	// Skip token
            System.out.println("You entered: " + s + " - a double was expected.");
        }
        return d;
    }

    /**
     * Reads a Double from the keyboard with prompt
     * @author Gerard Sparke The Java Way 2nd edition
     */
    public static double getDouble(String prompt_) { 			// Read a double
        double d = 0.0;
        System.out.print(prompt_);

        if (scanner.hasNextDouble()) {		// Test that user entered a double
            d = scanner.nextDouble();	// Read a double
        }
        else {					// Non-numeric data
            String s = scanner.next();	// Skip token
            System.out.println("You entered: " + s + " - a double was expected.");
        }
        return d;
    }

    /**
     * Reads a Double from the keyboard with prompt in range min-max inclusive
     * @author Gerard Sparke The Java Way 2nd edition
     */
    public static double getDouble(String prompt_, double min_, double max_) { 			// Read a double
        double d = min_ - 1;
        while (d < min_ || d > max_) {

            System.out.print(prompt_);

            if (scanner.hasNextDouble()) {		// Test that user entered a double
                d = scanner.nextDouble();	// Read a double
                if (d < min_ || d > max_) {
                    System.out.println("Error - out of range");
                }
            }
            else {					// Non-numeric data
                String s = scanner.next();	// Skip token
                System.out.println("You entered: " + s + " - a double was expected.");
            }


        }
        return d;
    }

    /**
     * Reads an Integer from the keyboard
     * @author Gerard Sparke The Java Way 2nd edition
     */
    public static int getInt() { 				// Read an int
        int i = 0;

        if (scanner.hasNextInt()) {		// Test that user entered an integer
            i = scanner.nextInt();		// Read an int
        }
        else {					// Non-numeric data
            String s = scanner.next();	// Skip token
            System.out.println("You entered: " + s + " - an integer was expected.");
        }
        return i;
    }

    /**
     * Reads a Integer from the keyboard with prompt
     * @author Gerard Sparke The Java Way 2nd edition
     */
    public static int getInt(String prompt_) { 			// Read a double
        int i = 0;
        System.out.print(prompt_);

        if (scanner.hasNextInt()) {		// Test that user entered a integer
            i = scanner.nextInt();	// Read a integer
        }
        else {					// Non-numeric data
            String s = scanner.next();	// Skip token
            System.out.println("You entered: " + s + " - a integer was expected.");
        }
        return i;
    }

    /**
     * Reads a Integer from the keyboard with prompt in range min-max inclusive
     * @author Gerard Sparke The Java Way 2nd edition
     */
    public static int getInt(String prompt_, int min_, int max_) { 			// Read a integer
        int i = min_ - 1;
        while (i < min_ || i > max_) {

            System.out.print(prompt_);

            if (scanner.hasNextInt()) {		// Test that user entered a integer
                i = scanner.nextInt();       // Read a integer
                if (i < min_ || i > max_) {
                    System.out.println("Error - out of range");
                }
            }
            else {					// Non-numeric data
                String s = scanner.next();	// Skip token
                System.out.println("You entered: " + s + " - a integer was expected.");
            }


        }
        return i;
    }

    /**
     * Reads a String from the keyboard
     * @author Gerard Sparke The Java Way 2nd edition
     */
    public static String getString() { 			// Read a String

        String s = scanner.next();		// Read up to the Enter key
        return s;
    }

    /**
     * Reads a String from the keyboard with prompt
     * @author Gerard Sparke The Java Way 2nd edition
     */
    public static String getString(String prompt_) { 			// Read a String

        System.out.print(prompt_);
        String s = scanner.next();		// Read up to the Enter key
        return s;
    }
}
