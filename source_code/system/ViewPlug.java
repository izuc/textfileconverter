package system;

import java.util.ArrayList;
import system.Bol.IOType;
import java.util.List;
import system.*;

/**
 * This class allows access to the system package for viewable classes.
 * It is the systems plug into the CLI and GUI Package.
 * @author: JAVA Alliance * 
 * @date:
 * @filename: ViewSocket.java
 * @version:
 */
public class ViewPlug {

    //The class wide variables.
    private static Bol bol;
    private static Viewable application;

    /**
     * Calls the viewable interface method.
     * @param application the application
     */
    public static void init(Viewable application) {
        bol = new Bol();
        ViewPlug.application = application;
    }

    /**
     * Gets the data to be placed into the API.
     * @param fileName the name of the file to be opened.
     * @param type the type of file the data should be opened as.
     * @return whether the data has been gotten.
     */
    public static boolean populate(String fileName, IOType type) {
        try{
            return ViewPlug.bol.populate(fileName, type);
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Updates the bol.
     */
    public static void update() {
        ViewPlug.application.init();
    }

    /**
     * Clears the data from the bol object.
     */
    public static void clearData() {
        try{
        ViewPlug.bol.clearData();
        }
        catch (Exception e){
        }
        }
    /**
     * Gets the data from the bol.
     * @return the list of
     */
    public static List<List<Object>> getData() {
        return ViewPlug.bol.getData();
    }

    /**
     * Sets the data to be converted
     * @param data to be converted
     */
    public static void setData(List<List<Object>> data) {
        ViewPlug.bol.setData(data);
    }

    /**
     * Returns a list of column names
     * @return the list of columnNames
     */
    public static ArrayList<Object> getColumnNames() {
        return ViewPlug.bol.getColumnNames();
    }

    /**
     * Sends through the data file type and the filename to be saved
     * @param type the type of data file
     * @param fileName the name of the file
     * @return confirmation of the operation
     */
    public static boolean saveData(IOType type, String fileName) {
        return ViewPlug.bol.saveData(type, fileName);
    }

    /**
     * Sets the default column names, if none exist
     */
    public static void setDefaultColumnNames() {
        ViewPlug.bol.setDefaultColumnNames();
    }

    /**
     * Gets the data type
     * @return the data structure
     */
    public static ArrayList<Object> getDataType() {
        return ViewPlug.bol.getDataType();
    }

    /**
     * Get the data type of the string and checks whether it matches the previously set data type.
     * @param value - string sent from user to change for validation check.
     * @param position - position of the data in the arraylist
     * @return the success of the validation, whether it is acceptable in the data type slot.
     */
    public static boolean getDataTypeSingle(Object value, int position) {
        return ViewPlug.bol.getDataTypeSingle(value, position);
    }

    /**
     * Gets the vendors of supplying DBMS.
     * @return the string array of vendors on the machine.
     */
    public static String[] getVendors() {

        return ViewPlug.bol.getVendors();

    }

    /**
     * Sends through the various parameters needed for the connection.
     * @param url the url of the connection stream.
     * @param vendor the vendor of the conection stream.
     * @param user the username of the conection stream.
     * @param password  the password of the conection stream.
     * @return whether the  connection was successful.
     */
    public static boolean getConnection(String url, int vendor, String user, String password) {
        return ViewPlug.bol.getConnection(url, vendor, user, password);
    }

    /**
     * Gets the databases tables.
     * @param catalog the catalogs to get the tables.
     * @return the arraylist of tables.
     */
    public static ArrayList<String> getTables(String catalog) {
        return ViewPlug.bol.getTables(catalog);
    }

    /**
     * Gets the catalogs.
     * @return the catalogs arraylist from the Bol2.
     */
    public static ArrayList<String> getCatalogs() {
        return ViewPlug.bol.getCatalogs();
    }

    /**
     * Gets the database name and table name.
     * @param database the database name.
     * @param tableName the database table.
     * @return whether the data was sent successfully.
     */
    public static boolean sendDataToDBMS(String database, String tableName) {
        return ViewPlug.bol.sendDataToDBMS(database, tableName);
    }

    /**
     * Gets the data classes of the Bol2.
     * @return the arraylist of the classes.
     */
    public static ArrayList<Object> getClasses() {
        return ViewPlug.bol.getClasses();
    }

    /**
     * Changes the column name.
     * @param column the column to have the new name changed.
     * @param newText the new name of the column.
     */
    public static void modifyColumnName(int column, Object newText) {
        ViewPlug.bol.modifyColumnName(column, newText);
    }

    /**
     * Deletes a record at the position selected.
     * @param row the position of the new record.
     */
    public static void deleteRow(int row) {
        ViewPlug.bol.deleteRow(row);
    }

    /**
     * Inserts a new record at the position selected.
     * @param pos the position of the new record.
     */
    public static void insertRow(int pos) {
        ViewPlug.bol.insertRow(pos);
    }

    /**
     * Deletes a column at the position selected.
     * @param col the position of the new column.
     */
    public static void deleteColumn(int col) {
        ViewPlug.bol.deleteColumn(col);
    }

    /**
     * Inserts a new column at the position selected.
     * @param pos the position of the new column.
     */
    public static void insertColumn(int pos) {
        ViewPlug.bol.insertColumn(pos);
    }

    /**
     * Sets the value of an individual cell.
     * @param row the row position in the table.
     * @param column the column position in the table.
     * @param newContent the new value of the cell.
     */
    public static void modifyCell(int row, int column, Object newContent) {
        ViewPlug.bol.modifyCell(row, column, newContent);
    }

    /**
     * Gets the list of available data types for a column.
     * @param colNumber position the column's position in the list.
     * @return the arraylist of available data types for the column.
     */
    public static ArrayList<String> writeDataTypes(int colNumber) {
        return ViewPlug.bol.writeDataTypes(colNumber);
    }

    /**
     * Changes the default data type for a particular column.
     * @param newDataType the new datatype for the column.
     * @param position the column's position in the list.
     */
    public static void changeDataType(Object newDataType, int position) {
        ViewPlug.bol.changeDataType(newDataType, position);
    }

    /**
     * Gets the errors from the error list.
     * @return the error that was generated.
     */
    public static String getErrors() {
        return Bol.getErrors();
    }
}
