package gui;

/**
 * 
 * @author: JAVA Alliance
 * @date: 4tth April 2009
 * @filename: DataTableModel.java
 * @version:
 */
import javax.swing.table.AbstractTableModel;

import system.ViewPlug;

/**
 * The dataTable model class extends AbstractTableModel, and provides the
 * ability to update the corresponding element when a cell value has been changed.
 * @author Java Alliance
 */
public class DataTableModel extends AbstractTableModel {

    /**
     * Gets the number of columns in the data model
     * @return int - Number of columns
     */
    public int getColumnCount() {
        return (ViewPlug.getColumnNames() == null) ? 0 : ViewPlug.getColumnNames().size();
    }

    /**
     * Gets the number of rows of data in the data model
     * @return int - Number of rows
     */
    public int getRowCount() {
        return (ViewPlug.getData() == null) ? 0 : ViewPlug.getData().size();
    }

    /**
     * Gets the value of the cell at given table co-ordinates
     * @param row
     * @param col
     * @return Object at given table position
     */
    public Object getValueAt(int row, int col) {
        return ViewPlug.getData().get(row).get(col);
    }

    /**
     * Gets the name of the column at position col
     * @param col
     * @return string - column name
     */
    @Override
    public String getColumnName(int col) {
        return (String) ViewPlug.getColumnNames().get(col);
    }

    /**
     * Sets whether the given cell position is editable
     * @param row
     * @param col
     * @return true
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    /**
     * Sets the value of the object at the given table position
     * @param data
     * @param row
     * @param col
     */
    @Override
    public void setValueAt(Object data, int row, int col) {
        ViewPlug.getData().get(row).set(col, data);
        this.fireTableCellUpdated(row, col);
    }

    /**
     * Get the Class of the selected column
     * @param colNumber
     * @return Class
     */
    @Override
    public Class getColumnClass(int colNumber) {
        return (Class) ViewPlug.getDataType().get(colNumber);
    }
}