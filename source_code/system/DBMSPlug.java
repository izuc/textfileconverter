package system;

/**
 * This class corresponds directly to the DBMSSocket class.
 * It is the systems plug into the DBMS Package.
 * @author: JAVA Alliance
 * @purpose: 
 * @date:
 * @filename: DBMSPlug.java
 * @version:
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbms.DBMSSocket;

/**
 * This class corresponds directly to the DBMSSocket class.
 * It is the systems plug into the DBMS Package.
 * @author: JAVA Alliance * 
 * @date:
 * @filename: DBMSPlug.java
 * @version:
 */
public class DBMSPlug {

    /**
     * Gets the connection from the DBMS package
     * @param url the url of the connection stream.
     * @param vendor the vendor of the conection stream.
     * @param user the username of the conection stream.
     * @param password  the password of the conection stream.
     * @return the confirmation of whether the connection was established.
     * @throws ClassNotFoundException throws all exceptions to be caught in the Bol class.
     * @throws Exception throws all exceptions to be caught in the Bol class.
     */
    public static boolean getConnection(String url, int vendor, String user,
            String password) throws ClassNotFoundException, Exception {
        return DBMSSocket.getConnection(url, vendor, user, password);
    }

    /**
     * Gets the table names of a particular database file.
     * @param catalog the catalog needed for reference.
     * @param tables the tables needed to be populated.
     * @return the completed list of table names.
     * @throws SQLException throws all exceptions to be caught in the Bol class.
     */
    public static ArrayList<String> getTables(String catalog,
            ArrayList<String> tables) throws SQLException {

        //Gets the table names of the database nad returns them.
        DBMSSocket.getTables(tables, catalog);

        return tables;
    }

    /**
     * Gets the catalogs from the DBMS package.
     * @param catalogs the catalogs needing to be populated.
     * @return the completed list of catalogs.
     * @throws SQLException throws all exceptions to be caught in the Bol class.
     */
    public static ArrayList<String> getCatalogs(ArrayList<String> catalogs)
            throws SQLException {

        //Gets the catalogs from the database nad returns them.
        DBMSSocket.getCatalogs(catalogs);

        return catalogs;
    }

    /**
     * Sends the data to the database table to be inserted.
     * @param database the database the data is to be inserted into.
     * @param tableName the table the data is to be inserted into.
     * @param columnNames the column names the data is grouped under.
     * @param columnTypes the types of data the columns have.
     * @param data the data to be inserted into the database.
     * @throws Exception throws all exceptions to be caught in the Bol class.
     */
    public static void sendDataToDBMS(String database, String tableName,
            ArrayList<Object> columnNames,
            ArrayList<Object> columnTypes,
            List<List<Object>> data)
            throws Exception {

        DBMSSocket.sendDataToDBMS(database, tableName, columnNames,
                columnTypes, data);
    }

    /**
     * Gets the vendors of supplying DBMS.
     * @return the string array of vendors on the machine.
     * @throws Exception throws all exceptions to be caught in the Bol class.
     */
    public static String[] getVendors() throws Exception {

        return DBMSSocket.getVendors();
    }

    //HACK - This needs changing to correspond with the actual data type.
    //This tests the column Type class and changes it to a database equivalent.
    //Changed to 1000
    /**
     * Sets the database column types based on the column types supplied by
     * the valiadation and the user.
     * @param columnTypesJava the datatypes of the columns of data.
     * @return the database equivalent to the java datatypes.
     */
    public static ArrayList<Object> getDBColumnTypes(ArrayList<Object> columnTypesJava) {
        ArrayList<Object> columnTypes = new ArrayList<Object>();

        int i = 0;
        for (Object o : columnTypesJava) {
            if (o.toString().equals(DataTypeIdentifier.CLASS_NAMES[0])) {
                columnTypes.add(i, "varchar(1000) NOT NULL default ''");
            } else if (o.toString().equals(DataTypeIdentifier.CLASS_NAMES[1])) {
                columnTypes.add(i, "varchar(1000) NOT NULL default ''");
            } else if (o.toString().equals(DataTypeIdentifier.CLASS_NAMES[2])) {
                columnTypes.add(i, "varchar(1000) NOT NULL default ''");
            } else if (o.toString().equals(DataTypeIdentifier.CLASS_NAMES[3])) {
                columnTypes.add(i, "varchar(1000) NOT NULL default ''");
            } else if (o.toString().equals(DataTypeIdentifier.CLASS_NAMES[4])) {
                columnTypes.add(i, "varchar(1000) NOT NULL default ''");
            }
            i++;
        }

        return columnTypes;
    }
}
