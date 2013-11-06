package dbms;

import java.util.*;

/**
 * DBMSSqlcommand object. Contains all the commmands required for a
 * DBMS sepecific commandset.
 * @author: JAVA Alliance
 * @date:
 * 
 */
public class DBMSSql implements DBMSConstants {

    private ArrayList<String> DBMSStrings = new ArrayList<String>();

    /**
     * Adds each command into the array DBMSStrings. 
     * These are called with getters.
     * @param data this is a *.ini configutation file's contents.
     */
    DBMSSql(List<List<Object>> data) {

        for (List elements : data) {
            this.DBMSStrings.add(elements.get(1).toString().trim());
        }
    }

    /**
     * @Deprecated
     * @param index An ordinal location of the command you want to retrieve
     * @return String
     */
    @Deprecated
    public String getSQLCommand(int index) {
        return DBMSStrings.get(index);
    }

    String getVendor() {
        return DBMSStrings.get(0);
    }

    String getDriver() {
        return DBMSStrings.get(1);
    }

    String getURL(String hostname) {
        return DBMSStrings.get(2) + hostname;
    }

    String getSelect() {
        return DBMSStrings.get(3) + SPACE;
    }

    String getCreateDatabase() {
        return DBMSStrings.get(4) + SPACE;
    }

    String getUseDatabase() {
        return DBMSStrings.get(5) + SPACE;
    }

    String getCreateTable() {
        return DBMSStrings.get(6) + SPACE;
    }

    String getCreateOpen() {
        return DBMSStrings.get(7) + SPACE;
    }

    String getCreateSyntax() {
        return DBMSStrings.get(8) + SPACE;
    }

    String getSeperator() {
        return DBMSStrings.get(9) + SPACE;
    }

    String getCreateClose() {
        return DBMSStrings.get(10) + SPACE;
    }

    String getPrimaryKey() {
        return DBMSStrings.get(11) + SPACE;
    }

    String getPrimaryKeyUsage() {
        return DBMSStrings.get(12) + SPACE;
    }
}