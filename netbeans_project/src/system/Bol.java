package system;

import java.util.*;
import java.sql.SQLException;
import java.util.ArrayList;
import cli.*;
import gui.*;

/**
 * This class is the data storage and processing power of the entire API.
 * @author: JAVA Alliance
 * @date:
 * @filename: Bol.java
 * @version:
 */
public class Bol {

    static final String SPACE = " ";
    private static final String NEW_LINE = "\n";
    private static final String DEFAULT_COLUMN_NAME = "Column";
    private static final String[] dataTypes = {"Text", "Number", "Decimal", "Currency", "Date/Time", "True/False"};
    private CLI cli;
    private GuiFrame gui;

    
    /**
     *
     * @param cliFlag
     */
    public Bol(boolean cliFlag) {
        
            this.data = new ArrayList<List<Object>>();
            this.columnNames = new ArrayList<Object>();
            this.cli = new CLI(this);
    }

    /**
     *
     */
    public Bol() {
        
    }
    /**
     * The Input/Output types for text file loading/saving
     */
    public enum IOType {

        /**
         * CSV file type
         */
        CSV,
        /**
         * JSON file type
         */
        JSON,
        /**
         * XML file type
         */
        XML;
    };
    private static ArrayList<Exception> errors = new ArrayList<Exception>();
    private static final char COMMA = ',';
    private ArrayList<Object> columnNames = new ArrayList<Object>();
    private List<List<Object>> data = new ArrayList<List<Object>>();
    private ArrayList<Object> columnClassTypes = new ArrayList<Object>();
    private ArrayList<String> tables;
    private ArrayList<String> catalogs;

    /**
     * Removes the data and columnNames from the current BOL Object.
     */
    void clearData() {
        // This removes all the information from the data arraylist.
        this.data.clear();
        // This removes all the information from the columnNames arraylist.
        this.columnNames.clear();
    }

    /**
     * Returns the current BOL's data arraylist.
     * @return the data arraylist.
     */
    public List<List<Object>> getData() {
        return this.data;
    }

    /**
     * Sets the current BOL's data arraylist.
     * @param data - the data to be added to this BOL's data arraylist.
     */
    public void setData(List<List<Object>> data) {
        this.data = data;
    }

    /**
     * Returns the current BOL's columnNames arraylist.
     * @return the columnNames arraylist.
     */
    public ArrayList<Object> getColumnNames() {
        return this.columnNames;
    }

    /**
     * Populates the data arraylist.
     * @param fileName - the name of the file to be opened and the data copied into the API.
     * @param type - the users selected file type enum.
     * @return the success of the populate data arraylist.
     */
    public boolean populate(String fileName, IOType type) {
        // This try catch block is designed to give any errors to the errors arraylist variable.
        try {
            // This calls on the setData method with the returned arraylist.
            // It passes through the fileName param and the type's ordinal.
            this.setData(TextFilePlug.populate(fileName, COMMA, (type.ordinal() + 1)));
            // It is returned as true if this succeeds.
            return true;
        } catch (Exception ex) {
            // The error message is added to the errors arraylist.
            Bol.errors.add(ex);
            // It is returned as false if this needs to be done.
            return false;
        }
    }

    /**
     * Saves the data as a text document.
     * @param type the users selected file type enum to save as.
     * @param fileName the name of the file to be saved.
     * @return the success of the save data method call.
     */
    public boolean saveData(IOType type, String fileName) {
        // This try catch block is designed to give any errors to the errors arraylist variable.
        try {
            // This returns the value given by the return of the save data method in the TextFilePlug.
            // This sends through the filename, the column names, the data and the type's ordinal.
            return TextFilePlug.saveData(fileName, this.getColumnNames(),
                    this.getData(), (type.ordinal() + 1));
        } catch (Exception ex) {
            // The error message is added to the errors arraylist.
            Bol.errors.add(ex);
            // It is returned as false if this needs to be done.
            return false;
        }
    }

    /**
     * Sets the default column names if the column names are blank.
     */
    public void setDefaultColumnNames() {

        // Clears the column names arraylist.
        this.columnNames.clear();

        // This for loop gets the first record's size in the data arraylist.
        for (int i = 1; i <= this.data.get(0).size(); i++) {
            // It then adds the columns default name as the set constant as well as the count of the cycles.
            this.columnNames.add(DEFAULT_COLUMN_NAME + (i));
        }
    }

    /**
     * Checks the data type of the data and returns it.
     * @return the array list of the data type of the data.
     */
    public ArrayList<Object> getDataType() {
        // This sets the variable of columnClassTypes by calling
        // the Data Type Identifiers get Data Type method.
        // It send through the data arraylist.
        this.columnClassTypes = DataTypeIdentifier.getDataType(this.getData());
        // It returns the result of the getClasses method.
        return this.getClasses();
    }

    /**
     * Get the data type of the string and checks whether it matches the previously set data type.
     * @param value - string sent from user to change for validation check.
     * @param position - position of the data in the arraylist
     * @return the success of the validation, whether it is acceptable in the data type slot.
     */
    boolean getDataTypeSingle(Object value, int position) {
        // Casts the value as a string.
        String valueString = (String) value;
        return DataTypeIdentifier.testDataType(this.getClasses(), valueString,
                position);
    }

    /**
     * Gets the vendors of supplying DBMS.
     * @return the string array of vendors on the machine.
     */
    public String[] getVendors() {
        try {
            return DBMSPlug.getVendors();
        } catch (Exception e) {
            Bol.errors.add(e);
            return null;
        }
    }

    /**
     * Sends through the various parameters needed for the connection.
     * @param url the url of the connection stream.
     * @param vendor the vendor of the conection stream.
     * @param user the username of the conection stream.
     * @param password  the password of the conection stream.
     * @return whether the  connection was successful.
     */
    public boolean getConnection(String url, int vendor, String user, String password) {

        try {
            //attempts to return whether the connection was successful.
            //otherwise catches errors.
            return DBMSPlug.getConnection(url, vendor, user, password);
        } catch (ClassNotFoundException ex) {
            Bol.errors.add(ex);
        } catch (Exception e) {
            Bol.errors.add(e);
        }
        //If there are errors the value of false is returned and the errors are placecd in the error list.
        return false;
    }

    /**
     * Gets the databases tables.
     * @param catalog the catalogs to get the tables.
     * @return the arraylist of tables.
     */
    ArrayList<String> getTables(String catalog) {
        this.tables = new ArrayList<String>();

        //Attempt to get the tables from the DBMS package.
        //Any errors generated will be added to the error list.
        try {
            DBMSPlug.getTables(catalog, this.tables);
        } catch (SQLException ex) {
            Bol.errors.add(ex);
        }
        return this.tables;
    }

    /**
     * Gets the catalogs.
     * @return the catalogs arraylist from the Bol2.
     */
    ArrayList<String> getCatalogs() {
        this.catalogs = new ArrayList<String>();

        //Attempt to get the catalogs from the DBMS package.
        //Any errors generated will be added to the error list.
        try {
            DBMSPlug.getCatalogs(this.catalogs);
        } catch (SQLException ex) {
            Bol.errors.add(ex);
        }
        return catalogs;
    }

    /**
     * Gets the database name and table name.
     * @param database the database name.
     * @param tableName the database table.
     * @return whether the data was sent successfully.
     */
    public boolean sendDataToDBMS(String database, String tableName) {

        ArrayList<Object> columnTypesDB = new ArrayList<Object>();
        columnTypesDB = DBMSPlug.getDBColumnTypes(this.getDataType());

        //Attempt to get insert data to the database through the DBMS package.
        //Any errors generated will be added to the error list.
        try {
            DBMSPlug.sendDataToDBMS(database, tableName, this.getColumnNames(),
                    columnTypesDB, this.getData());
        } catch (Exception ex) {
            Bol.errors.add(ex);
            return false;
        }
        return true;
    }

    /**
     * Gets the data classes of the Bol2.
     * @return the arraylist of the classes.
     */
    ArrayList<Object> getClasses() {
        return this.columnClassTypes;
    }

    /**
     * Changes the column name.
     * @param column the column to have the new name changed.
     * @param newText the new name of the column.
     */
    public void modifyColumnName(int column, Object newText) {
        columnNames.set(column, newText);
    }

    /**
     * Deletes a record at the position selected.
     * @param row the position of the new record.
     */
    public void deleteRow(int row) {
        data.remove(row);
    }

    /**
     * Inserts a new record at the position selected.
     * @param pos the position of the new record.
     */
    public void insertRow(int pos) {

        //Inserts a blank row
        int size = data.get(0).size();
        LinkedList<Object> blankRow = new LinkedList<Object>();
        //Loops through the list and adds the row in the position set.
        for (int i = 0; i < size; i++) {
            blankRow.add("");
        }
        data.add(pos, blankRow);
    }

    /**
     * Deletes a column at the position selected.
     * @param col the position of the new column.
     */
    public void deleteColumn(int col) {
        LinkedList<Object> thisRow = new LinkedList<Object>();

        columnNames.remove(col);

        //Loops through the list and removes the column in the position set.
        int size = data.size();
        for (int i = 0; i < size; i++) {
            thisRow = (LinkedList) data.get(i);
            thisRow.remove(col);
        }
    }

    /**
     * Inserts a new column at the position selected.
     * @param pos the position of the new column.
     */
    public void insertColumn(int pos) {
        LinkedList<Object> thisRow = new LinkedList<Object>();

        columnNames.add(pos, "New column");

        //Loops through the list and adds the column in the position set.
        int size = data.size();
        for (int i = 0; i < size; i++) {
            thisRow = (LinkedList) data.get(i);
            thisRow.add(pos, "");
        }
    }

    /**
     * Sets the data in an individual record and field.
     * @param row - the record to be modified.
     * @param column - the field to be modified.
     * @param newContent - the new data to be modified.
     */
    public void modifyCell(int row, int column, Object newContent) {
        // Gets the individual record in the arraylist and then sets the field with the new content.
        this.data.get(row).set(column, newContent);
    }

    /**
     * Gets the list of available data types for a column.
     * @param colNumber position the column's position in the list.
     * @return the arraylist of available data types for the column.
     */
    ArrayList<String> writeDataTypes(int colNumber) {

        Object className = this.getDataType().get(colNumber);

        ArrayList<String> dataTypesAvailable = new ArrayList<String>();
        dataTypesAvailable.add(dataTypes[0]);

        //Checks to see if the datatype of the value is a boolean.
        if (className.toString().equals(DataTypeIdentifier.CLASS_NAMES[1])) {
            //Calls the change for the datatype.
            dataTypesAvailable.add(0, dataTypes[5]);

        //Checks to see if the datatype of the value is a int.
        } else if (className.toString().
                equals(DataTypeIdentifier.CLASS_NAMES[2])) {
            //adds the data type to the arraylist.
            dataTypesAvailable.add(0, dataTypes[1]);

        //Checks to see if the datatype of the value is a double.
        } else if (className.toString().
                equals(DataTypeIdentifier.CLASS_NAMES[3])) {
            //adds the data types to the arraylist.
            dataTypesAvailable.add(0, dataTypes[2]);
            dataTypesAvailable.add(1, dataTypes[3]);

        //Checks to see if the datatype of the value is a date.
        } else if (className.toString().
                equals(DataTypeIdentifier.CLASS_NAMES[4])) {
            //adds the data type to the arraylist.
            dataTypesAvailable.add(0, dataTypes[5]);

        //Sets the class type as a string.
        } else {
            //adds the data type to the arraylist.
            dataTypesAvailable.add(dataTypes[3]);
        }

        return dataTypesAvailable;
    }

    /**
     * Changes the classes based on the users choice of dataType.
     * @param newDataType - the new data type of the field.
     * @param position - the position of the data type in the data type array.
     */
    void changeDataType(Object newDataType, int position) {

        // If statement compares the string value against the datatypes contant array.
        // If it matches, the data type is set to the corresponding data type.
        if (newDataType.toString().equals(dataTypes[0])) {
            // This sets the column classes type array at the position sent,
            // to the value of the String data type array.
            this.columnClassTypes.set(position,
                    DataTypeIdentifier.CLASS_NAMES[0]);
        } else if (newDataType.toString().equals(dataTypes[1])) {
            // This sets the column classes type array at the position sent,
            // to the value of the integer data type array.
            this.columnClassTypes.set(position,
                    DataTypeIdentifier.CLASS_NAMES[2]);
        } else if (newDataType.toString().equals(dataTypes[2])) {
            // This sets the column classes type array at the position sent,
            // to the value of the double data type array.
            this.columnClassTypes.set(position,
                    DataTypeIdentifier.CLASS_NAMES[3]);
        } else if (newDataType.toString().equals(dataTypes[3])) {
            // This sets the column classes type array at the position sent,
            // to the value of the String data type array.
            this.columnClassTypes.set(position,
                    DataTypeIdentifier.CLASS_NAMES[0]);
        } else if (newDataType.toString().equals(dataTypes[4])) {
            // This sets the column classes type array at the position sent,
            // to the value of the Date data type array.
            this.columnClassTypes.set(position,
                    DataTypeIdentifier.CLASS_NAMES[4]);
        } else if (newDataType.toString().equals(dataTypes[5])) {
            // This sets the column classes type array at the position sent,
            // to the value of the boolean data type array.
            this.columnClassTypes.set(position,
                    DataTypeIdentifier.CLASS_NAMES[1]);
        }
    }

    /**
     * Gets the errors from the error list.
     * @return the error that was generated.
     */
    static String getErrors() {

        StringBuffer b = new StringBuffer();
        //Searches through the bol errors to get the error and the
        for (Exception ex : Bol.errors) {
            b.append(ex.getMessage() + NEW_LINE);
        }
        //Clears the errors.
        Bol.errors.clear();
        return b.toString();
    }
}