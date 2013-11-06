package dbms;

/**
 * @author: JAVA Alliance
 * This is a public interface socket for accesing the services of the 
 * 			 DBMS package. This is a static socket. You will need to import 
 * 			 this socket into your own packages to use it: 
 * 			 import dbms.DBMSSocket;
 * @date:
 * @filename: DBMSSocket.java
 * @version: 1.0
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a public interface socket for accesing the services of the
 * DBMS package
 * @author Java Alliance
 */
public class DBMSSocket {

    private static DBMSBase conn;
    private static DBMSDrivers drivers = new DBMSDrivers();

    /**
     * An array of vendor names. Use the String Array's ordinals for selecting 
     * a vendor when using 'getConnection();
     * @return A String array of vendor names.
     * @throws Exception
     */
    public static String[] getVendors() throws Exception {

        return drivers.getVendors();
    }

    /**
     * Gets a database connection from supplied information
     * @param hostname
     * @param vendor
     * @param user
     * @param password 
     * @return Boolean if connection successful
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public static boolean getConnection(String hostname, int vendor,
            String user, String password)
            throws ClassNotFoundException, Exception {
        conn = new DBMSBase(drivers.getCommand(vendor),
                hostname, user, password);
        return true;
    }

    /**
     * This method allows access to storing information on a database.
     * @param database
     * @param tableName
     * @param columnNames
     * @param columnTypes
     * @param data
     * @throws Exception
     */
    public static void sendDataToDBMS(String database, String tableName,
            ArrayList<Object> columnNames, ArrayList<Object> columnTypes,
            List<List<Object>> data) throws Exception {

        conn.setup(database, tableName, columnNames, columnTypes, data);
    }

    /**
     * This method allows access to storing inormation on a database.
     * @param tables
     * @param catalog
     * @throws SQLException
     */
    public static void getTables(ArrayList<String> tables, String catalog)
            throws SQLException {

        conn.getTables(tables, catalog);
    }

    /**
     * Updates an arraylist you provide to contain all the catalogs (databases) in the currently connected DBMS
     * @param catalogs
     * @throws SQLException
     */
    public static void getCatalogs(ArrayList<String> catalogs)
            throws SQLException {

        conn.getCatalogs(catalogs);
    }
}
