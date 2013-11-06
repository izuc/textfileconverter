package gui;

/**
 * 
 * Purpose: l. The class
 * enables support for the sorting of the rows by column ascending and descending.
 * 
 * The class contains a method for refreshing the column names within the JTable;
 * which allows for the column names to be updated once the data in the column names structure
 * has been updated.
 * @author: JAVA Alliance
 * @date: 
 * @filename: DataPanel.java
 * @version:
 */
import java.awt.FlowLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;

import system.*;

/**
 * Contains the JTable linked with the structure
 * stored within the business object layer through the DataTableModel
 * @author Java Alliance
 */
public class DataPanel extends JPanel implements GuiConstants {

    private static final long serialVersionUID = 311L;
    private JTable tblData;

    /**
     * The constructor for the data panel doesn't receive any parameters, and any 
     * data required in this class is communicated through the static ViewPlug methods, 
     * which refers to the bol to get/ modify the data structures.
     */
    public DataPanel() {
        this.tblData = new JTable(new DataTableModel()); // Instantiates the DataTableModel, and passes it into the JTable constructor as a parameter.
        tblData.setRowSorter(new TableRowSorter<TableModel>(tblData.getModel())); // Sets the Row sorter to a new instance of the table row sorter (passing the model)
        tblData.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Changes the size of the JTable, so it resizes all columns to a average width.
        tblData.setPreferredScrollableViewportSize(new Dimension(850, 500)); // Sets the fix size of the JTable, until the scrollable gets used.
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        this.add(new JScrollPane(tblData));
    }

    /**
     * The refresh table columns method is used update the JTable with the latest 
     * column name data stored in the main column name structure. 
     */
    public void refreshTableColumns() {
        for (Object object : ViewPlug.getColumnNames()) { // Iterates through the column names
            this.tblData.getColumnModel().getColumn(ViewPlug.getColumnNames().
                    indexOf(object)).setHeaderValue(object.toString()); // Updates JTable header value
        }
    }
}
