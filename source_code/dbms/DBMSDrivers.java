package dbms;

/**
 * @author: JAVA Alliance
 * @purpose:
 * @date:
 * @filename: DBMSDrivers.java
 * @version:
 */
import java.io.*;
import java.util.ArrayList;
import text.TextFileSocket;

/**
 * Methods to determine the list of available DBMS drivers
 * @author Java Alliance
 */
public class DBMSDrivers implements DBMSConstants {

    public static final String DIRECTORY_EMPTY = "There are no drivers in the directory";
    public static final String INI = "ini";
    private ArrayList<DBMSSql> sqlDrivers = new ArrayList<DBMSSql>(); // todo: maybe store this int he bol?

    /**
     * Updates the current list of Drivers inside of sqlDrivers.
     */
    void update() throws Exception {
        sqlDrivers.clear();
        // open up the working directory and go to the 'drivers' sub folder

        String driverDirectory = System.getProperty(USER_DIR) +
                System.getProperty(FILE_SEPARATOR) + DRIVER_LOCATION +
                System.getProperty(FILE_SEPARATOR);
        File dir = new File(driverDirectory);
        String[] ini = dir.list();

        if (ini == null) {
            throw new Exception(DIRECTORY_EMPTY);
        }

        // put the driver names into the string array of drivers
        for (int i = 0; i < ini.length; i++) {
            String fileName = ini[i];
            if (fileName.indexOf(INI) > 0) {
                sqlDrivers.add(new DBMSSql(TextFileSocket.readTextFile(driverDirectory + fileName,
                        COLON_SEPARATOR, 1)));
            }
        }

    }

    /**
     *
     * @param driver an ordinal location of the driver needed
     * @return DBMSSql driver object
     */
    DBMSSql getCommand(int driver) {

        return sqlDrivers.get(driver);
    }

    /**
     * Gets a dynamic list of vendors.
     * @return A String Array of Vendor Names;
     */
    @SuppressWarnings("empty-statement")
    String[] getVendors() throws Exception {

        this.update();   // update the driver list
        String[] driverList = new String[this.sqlDrivers.size()];
        // loop through all the drivers in sqlDrivers and add them to the array;
        for (int i = 0; i < this.sqlDrivers.size(); driverList[i] =
                        this.sqlDrivers.get(i++).getVendor());
        return driverList;
    }
}
