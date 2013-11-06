package dbms;

/**
 * @author: JAVA Alliance
 * @purpose:
 * @date:
 * @filename: DBConnection.java
 * @version:
 */
import java.sql.*;

/**
 * Methods to establish a connection to a database
 * @author Java Alliance
 */
public class DBConnection {

    private Statement statement;
    private DatabaseMetaData dbm;

    /**
     * Constructor
     * @param url A url connection address (localhost)
     * @param driver A driver location
     * @param user A username for the dbms
     * @param password a password for the dbms
     * @throws java.lang.ClassNotFoundException
     * @throws SQLException 
     */
    public DBConnection(String url, String driver, String user, String password)
            throws ClassNotFoundException, SQLException {

        Connection conn;
        Class.forName(driver);

        conn = DriverManager.getConnection(url, user, password);
        if (conn != null) {
            this.statement =
                    conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            this.dbm = conn.getMetaData();
        }
    }

    /**
     * returns the current statement object
     * @return database statement object
     */
    public Statement getStatement() {
        return this.statement;
    }

    /**
     * returns the current database meta data object
     * @return DatabaseMetaDataObject
     */
    public DatabaseMetaData getDatabaseMetaData() {
        return this.dbm;
    }
}