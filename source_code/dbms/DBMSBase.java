package dbms;

/**
 * @author: JAVA Alliance
 * @purpose:
 * @date:
 * @filename: DBMSBase.java
 * @version:
 */
import java.sql.*;
import java.util.*;
import javax.swing.*;

/**
 * Base class for the DBMS classes
 * @author Java Alliance
 */
public class DBMSBase implements DBMSConstants {

    private DBConnection connection;
    private DBMSSql commands;

    /**
     * Constructor to create a connection object.
     * @param commands
     * @param hostname
     * @param username
     * @param password
     * @throws Exception
     */
    public DBMSBase(DBMSSql commands, String hostname, String username,
            String password) throws Exception {
        this.commands = commands;

        this.setConnection(this.commands.getURL(hostname),
                this.commands.getDriver(), username, password);
    }

    /**
     * Uses the DBMSSql commands object to retrieve a resultset for the specific DBMS
     * @param tableName
     * @return
     * @throws Exception
     */
    private ResultSet getResultSet(String tableName) throws Exception {
        return this.connection.getStatement().executeQuery(
                this.commands.getSelect() + tableName);
    }

    /**
     * concatenates the columnCreate string
     * @param columnNames
     * @param columnTypes
     * @return
     */
    private String createColumns(List columnNames, List columnTypes) {
        String columnCreate = EMPTY_STRING;
        for (int i = 0; i < columnNames.size(); i++) {
            columnCreate += columnNames.get(i).toString() +
                    SPACE + columnTypes.get(i).toString() +
                    COMMA + NEW_LINE;
        }
        return columnCreate;
    }

    /**
     * Creates a new connection to a database and generates both a Statement object and a DatabaseMetaData object these are stored as instance fields (statement / dbm)
     * @param url
     * @param driver
     * @param username
     * @param password
     * @throws Exception
     */
    private void setConnection(String url, String driver, String username,
            String password) throws Exception {
        this.connection = new DBConnection(url, driver, username, password);
    }

    /**
     * This creates a new database based on individual DBMS requirments
     * @param databaseName the name of the database to create
     * @throws Exception
     */
    public void setupDatabase(String databaseName) throws Exception {
        if (!(this.commands.getCreateDatabase().contains(LIMITED_DATABASE))) {
            this.getStatement().executeUpdate(this.commands.getCreateDatabase() + databaseName);
        }
        if (!(this.commands.getUseDatabase().contains(LIMITED_DATABASE))) {
            this.getStatement().executeQuery(this.commands.getUseDatabase() + databaseName);
        }
    }

    /**
     * Creates the table based on provided information
     * @param database
     * @param tableName
     * @param columnNames
     * @param columnTypes
     * @param data
     * @throws Exception
     */
    public void setup(String database, String tableName, List columnNames,
            List columnTypes, List<List<Object>> data) throws Exception {
        this.setupDatabase(database);
        this.setupTable(database, tableName, columnNames, columnTypes);
        this.insertData(tableName, columnNames, data);
    }

    /**
     * Updates the current list of Drivers from the config files to load a list of sqlDrivers
     * @param database
     * @param tableName
     * @param columnNames
     * @param columnTypes
     * @throws Exception
     */
    public void setupTable(String database, String tableName, List columnNames,
            List columnTypes) throws Exception {
        String query = "";
        if (database.contains("MS")) {
            JOptionPane.showMessageDialog(null, "MSSQL used");
            query = this.commands.getCreateTable() + SPACE;

            query += "dbo." + tableName + SPACE + OPEN_BRACE + NEW_LINE;
            query += createColumns(columnNames, columnTypes);

            query += CLOSE_BRACE;
        } else if (database.contains("post")) {
            JOptionPane.showMessageDialog(null, "Postgre used");
            query = this.commands.getCreateTable() + SPACE;
            query += DOUBLE_QUOTE + tableName + DOUBLE_QUOTE + SPACE +
                    OPEN_BRACE + NEW_LINE;
            query += createColumns(columnNames, columnTypes);
            query += this.commands.getPrimaryKey() + SPACE + OPEN_BRACE +
                    DOUBLE_QUOTE + "column0" + DOUBLE_QUOTE +
                    CLOSE_BRACE + NEW_LINE;
            query += CLOSE_BRACE;
        } else {
            JOptionPane.showMessageDialog(null, "MySQL used");
            query = this.commands.getCreateTable() + SPACE;

            query += SINGLE_QUOTE + tableName + SINGLE_QUOTE + SPACE +
                    OPEN_BRACE + NEW_LINE;

            query += createColumns(columnNames, columnTypes);
            query += this.commands.getPrimaryKey() + SPACE + OPEN_BRACE +
                    SINGLE_QUOTE + columnNames.get(0).toString() + SINGLE_QUOTE +
                    CLOSE_BRACE + NEW_LINE;
            query += CLOSE_BRACE;
            this.getStatement().executeUpdate("DROP TABLE IF EXISTS " + tableName);
        }
        this.getStatement().executeUpdate(query);
    }

    /**
     * retrieves a list of commands for the DBMS you are using
     * @param tableName
     * @param columnNames
     * @param data
     * @throws Exception
     */
    public void insertData(String tableName, List columnNames,
            List<List<Object>> data) throws Exception {

        ResultSet resultSet = this.getResultSet(tableName);
        int index = 0;
        for (List elements : data) {
            index = 0;
            resultSet.moveToInsertRow();
            for (Object column : columnNames) {
                resultSet.updateString(column.toString(),
                        elements.get(index).toString());
                index++;
            }
            resultSet.insertRow();
        }
    }

    /**
     * Gets a list of the catalogs on the DBMS
     * @param catalogs a list of catalogs to update
     * @throws SQLException
     */
    void getCatalogs(ArrayList<String> catalogs) throws SQLException {
        ResultSet rs = this.connection.getDatabaseMetaData().getCatalogs();
        while (rs.next()) {
            catalogs.add(rs.getString("TABLE_CAT"));
        }
    }

    /**
     * Gets a list of the tables on a catalog
     * @param tables
     * @param catalog
     * @throws SQLException
     */
    void getTables(ArrayList<String> tables, String catalog) throws SQLException {
        ResultSet rs = this.connection.getDatabaseMetaData().getTables(catalog,
                null, "%", null);
        while (rs.next()) {
            tables.add(rs.getString(3));
        }
    }

    /**
     * 
     * @return a statement object
     */
    private Statement getStatement() {
        return this.connection.getStatement();
    }
}