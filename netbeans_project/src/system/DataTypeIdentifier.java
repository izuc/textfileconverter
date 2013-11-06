package system;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows for the data type of the data arraylist to be discovered.
 * Also allows for the validation of various user inputs.
 * @author: JAVA Alliance * 
 * @date:
 * @filename: DataTypeIdentifier.java
 * @version:
 */
public abstract class DataTypeIdentifier {

    //Identifies the java class types for setting column data types.
    /**
     *
     */
    public final static String[] CLASS_NAMES = {"class java.lang.String",
        "class java.lang.Boolean", "class java.lang.Integer",
        "class java.lang.Double", "class java.lang.Date"};
    private final static String regExpressionBooleanTrue = "true";
    private final static String regExpressionBooleanFalse = "false";
    private final static String regExpressionYes = "yes";
    private final static String regExpressionNo = "no";
    private final static String regExpressionNumber = "^[0-9]+$";

    //This will match any floating number as long as it has a digit then a decimal point. It can also have a optional positive or negative integer.
    private final static String regExpressionDouble = "[-+]?[0-9]*\\.?[0-9]+";
    private final static String regExpressionCurrency = "[\\$]?[0-9]*\\.?[0-9]+";

    /**
     * Gets the singles record in the arraylist of records ata certain position.
     * @param dataList the list of records.
     * @param position the position in which the record is to be gotten from.
     * @return the single record.
     */
    private static ArrayList<String> getSingleRecord(List<List<Object>> dataList, int position) {
        ArrayList<String> oneRecord = new ArrayList<String>();

        //Gets the single record at the position needed.
        //Adds the data to the oneRecord if it is not null.
        for (Object data : dataList.get(position)) {
            if (data != null) {
                oneRecord.add(data.toString());
            } else {
                oneRecord.add(null);
            }
        }
        return oneRecord;
    }

    /**
     * Checks the content of the single record and assigns the datatype for
     * each column.
     * @param singleRecord the single record to be checked.
     * @return the array of data type objects assigned to the single record.
     */
    private static ArrayList<Object> checkContent(ArrayList<String> singleRecord) {

        ArrayList<Object> dataTypeTemp = new ArrayList<Object>();

        for (String dataText : singleRecord) {

            String lowerText = dataText.toLowerCase();

            if (dataText.matches(regExpressionNumber)) {
                dataTypeTemp.add(Integer.class);
            } else if (dataText.matches(regExpressionDouble)) {
                dataTypeTemp.add(Double.class);
            } else if (dataText.matches(regExpressionCurrency)) {
                dataTypeTemp.add(String.class);
            } else if (lowerText.matches(regExpressionBooleanTrue)) {
                dataTypeTemp.add(boolean.class);
            } else if (lowerText.matches(regExpressionBooleanFalse)) {
                dataTypeTemp.add(boolean.class);
            } else if (lowerText.matches(regExpressionYes)) {
                dataTypeTemp.add(boolean.class);
            } else if (lowerText.matches(regExpressionNo)) {
                dataTypeTemp.add(boolean.class);
            } else {
                dataTypeTemp.add(String.class);
            }
        }
        return dataTypeTemp;
    }

    /**
     * Checks the array of datatypes against the established data type array.
     * @param temp the temp data type array to be checked.
     * @param dataClass the established data array.
     */
    private static void checkLoop(ArrayList<Object> temp, List<Object> dataClass) {

        int index = 0;

        //Loops through the dataClass and compares each value against the temp value.
        //If it does not match the value is changed to String.
        for (Object obj : dataClass) {
            if (obj != null && !obj.equals(temp.get(index))) {
                temp.set(index, String.class);
            }

            index++;
        }
    }

    /**
     * Checks a single value to see if it matches against the data type.
     * @param dataTypeClasses the list of data types supplied.
     * @param value the value to be checked.
     * @param position the position of the value in the array list.
     * @return whether the value was the correct value.
     */
    public static boolean testDataType(ArrayList<Object> dataTypeClasses,
            String value, int position) {
        boolean classMatch = false;

        ArrayList<Object> dataTypeTemp = new ArrayList<Object>();
        Object classType = dataTypeClasses.get(position);
        ArrayList<String> oneValue = new ArrayList<String>();
        oneValue.add(value);
        dataTypeTemp = checkContent(oneValue);

        //Checks the class type and compares it to the class of the data type.
        //If it is a string then it matches and is true.
        if (classType.getClass().getName().equalsIgnoreCase("java.lang.String")) {
            classMatch = true;
        }

        //If it matches the class type then it is true.
        if (dataTypeTemp.get(0).equals(classType)) {
            classMatch = true;
        }

        return classMatch;
    }

    /**
     * Gets the data type of the data in the arraylist.
     * @param dataList the data to be identfied.
     * @return the arraylist of data classes.
     */
    public static ArrayList<Object> getDataType(List<List<Object>> dataList) {

        ArrayList<Object> dataTypeIdentifiedTemp = checkContent(getSingleRecord(dataList, 0));

        for (int i = 1; i < dataList.size(); i++) {
            checkLoop(dataTypeIdentifiedTemp, checkContent(getSingleRecord(dataList, i)));
        }

        return dataTypeIdentifiedTemp;
    }
}