package gui;

/**
 * The single record class is the viewing panel for the data
 * singularly based on each record.The column names and text fields both get
 * created dynamically based on the size of the data structures stored in the
 * business object layer.
 * @author: JAVA Alliance
 * 
 * @date: 19th May 2009
 * @filename: SingleRecord.java
 * @version: 1.1
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;

import system.ViewPlug;

/**
 * The single record class is the viewing panel for the data
 * @author Java Alliance
 */
public class SingleRecord extends JPanel implements GuiConstants, ActionListener {

    private static final long serialVersionUID = 1L;
    private static final DecimalFormat FORMAT = new DecimalFormat("00");
    private ArrayList<JButton> buttons;
    private LinkedList<JPanel> columns;
    private LinkedList<JTextField> textFields;
    private LinkedList<JComboBox> comboBoxFields;
    private int index;
    private JPanel pnlPosition;

    /**
     * JPanel which shows a single record at a time.
     */
    public SingleRecord() {
        this.buttons = new ArrayList<JButton>();
        this.textFields = new LinkedList<JTextField>();
        this.columns = new LinkedList<JPanel>();
        this.comboBoxFields = new LinkedList<JComboBox>();

        // Extends JPanel and Implements Scrollable.
        JXPanel scrollPanel = new JXPanel();

        // Create The scroll pane
        JScrollPane scroll = new JScrollPane(scrollPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //create the center panel
        JPanel pnlCentre = new JPanel(new GridLayout((ViewPlug.getColumnNames().size() + 1), 3, 5, 5));
        scrollPanel.add(pnlCentre);

        //add items tot he center panel
        pnlCentre.add(new JLabel(FIELD_HEADING));
        pnlCentre.add(new JLabel(DATA_HEADING));
        pnlCentre.add(new JLabel(TYPE_HEADING));

        int colNum = 0;
        // for each of the text objects in Single Record
        for (Object text : ViewPlug.getColumnNames()) {
            // add the new Jpanel
            this.columns.add(new JPanel(new CardLayout()));
            // retrieve the previous entry and the label and text field
            this.columns.getLast().add(new JLabel(text.toString()), EMPTY);
            this.columns.getLast().add(new JTextField(text.toString(), 15), EMPTY);
            this.columns.getLast().setToolTipText(COLUMN_NAME_TOOLTIP);

            // action listener for mouse events
            this.columns.getLast().addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {
                    showField(e.getSource());
                }
            });

            // focus listener for columns
            this.columns.getLast().getComponent(1).addFocusListener(new FocusAdapter() {

                public void focusLost(FocusEvent e) {
                    updateHeader(e.getSource());
                }
            });


            this.textFields.add(new JTextField(EMPTY, 15));
            this.textFields.getLast().addFocusListener(new FocusAdapter() {

                // event for loosing focus to updateText in field.
                public void focusLost(FocusEvent e) {
                    updateText(e.getSource());
                }
            });

            pnlCentre.add(this.columns.getLast());
            pnlCentre.add(this.textFields.getLast());

            // declare the datatypes arraylist and full it with class string data
            ArrayList<String> dataTypes = ViewPlug.writeDataTypes(colNum);

            // convert to array of string instead of arraylist
            String[] da = (String[]) dataTypes.toArray(new String[dataTypes.size()]);

            // create JComboBox based on the
            JComboBox dataTypesBox = new JComboBox(da);
            this.comboBoxFields.add(dataTypesBox);
            dataTypesBox.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    updateDataType(e.getSource());
                }
            });
            pnlCentre.add(dataTypesBox);
            colNum++;
        }

        scroll.setPreferredSize(new Dimension(550, 400));
        JPanel pnlSouth = new JPanel(new BorderLayout());
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));

        // Add buttons to the buttons panel
        for (int i = 0; i < buttonNames.length; i++) {
            JButton b = new JButton(buttonNames[i]);
            b.addActionListener(this);
            this.buttons.add(b);
            pnlButtons.add(b);
        }


        pnlSouth.add(pnlButtons, BorderLayout.CENTER);
        pnlSouth.add(this.pnlPosition = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 0, 10)), BorderLayout.EAST);

        // needs 2 labels for the next and previous page numbers
        this.pnlPosition.add(new JLabel());
        this.pnlPosition.add(new JLabel());
        // sets the paenl collection
        this.pnlPosition.setPreferredSize(new Dimension(200, 50));


        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
        this.add(pnlSouth, BorderLayout.SOUTH);
        this.setIndex(0);
    }

    /**
     * This changes the dataType to what the user wants.
     * @param obj
     */
    private void updateDataType(Object obj) {
        int i = this.comboBoxFields.indexOf(obj);
        Object newDataType = this.comboBoxFields.get(i).getSelectedItem();
        ViewPlug.changeDataType(newDataType, i);
        this.comboBoxFields.get(i).setEnabled(false);
    }

    /**
     * The method updates the assoicated value in the data structure with the value from the JTextField.
     * The method validates the data, and also changes the background colour of the JTextField if its invalid.
     * @param object
     */
    private void updateText(Object object) {
        int i = this.textFields.indexOf(object);
        if (ViewPlug.getDataTypeSingle(this.textFields.get(i).getText(), i)) {
            ViewPlug.getData().get(this.index).set(i,
                    (Object) this.textFields.get(i).getText());
            this.textFields.get(i).setBackground(Color.white);
        } else {
            this.textFields.get(i).setText((String) ViewPlug.getData().get(this.index).get(i));
            this.textFields.get(i).setBackground(Color.pink);
            this.textFields.get(i).requestFocus();
        }
    }

    /**
     * The update header method is used to update the changed column name value.
     * It first checks whether the value has actually changed, by comparing the 
     * JTextField with the label.  
     * @param object
     */
    private void updateHeader(Object object) {
        // returns the panel which the header is in.
        JPanel parent = (JPanel) ((Component) object).getParent();
        // Only updates it if the text in the label is not equal to the same in the textbox.
        // if the JTextField and JLabel have different values then the user has updated a column.
        ((CardLayout) parent.getLayout()).first(parent);
        if (!((JLabel) parent.getComponent(0)).getText().
                equals(((JTextField) object).getText())) {
            ((JLabel) parent.getComponent(0)).setText(((JTextField) object).getText());
            ViewPlug.getColumnNames().set(this.columns.indexOf(parent),
                    ((JTextField) object).getText());
        }
    }

    /**
     * This method is used to show the JTextField on the column name cardlayout.
     * It receives the JPanel of the cardlayout, and shows the next card (which
     * has the JTextField in it, and requests focus.
     * @param object - The shown card.
     */
    private void showField(Object object) {
        JPanel parent = (JPanel) object; // Gets the parent of the selected card, 
        //which therefore gets the cardlayout panel.
        ((CardLayout) parent.getLayout()).next(parent); // Shows the next card from the parent.
        parent.getComponent(1).requestFocusInWindow(); // Requests focus.
    }

    /**
     * The setIndex method is used to change the currently selected record; which
     * sets the Fields with the new values. If the index received is greater than the
     * size of the data structure; then it will reset the index back to 0 - therefore
     * creating a cycling effect when navigated through the records.
     * @param index - The desired index in the datastructure which will get viewed.
     */
    private void setIndex(int index) {
        // Resets the index to zero, if the value is greater than the size of the structure.
        this.index = (this.index < ViewPlug.getData().size()) ? index : 0;
        this.setFields(); // Sets the fields with the new record.
    }

    /**
     * Increments the index, and sets the fields.
     */
    private void increment() {
        this.index = (this.index < (ViewPlug.getData().size() - 1)) ? ++this.index : 0;
        this.setFields();
    }

    /**
     * Decrements the index, and sets the fields.
     */
    private void decrement() {
        this.index = (this.index > 0) ? --this.index : (ViewPlug.getData().size() - 1);
        this.setFields();
    }

    /**
     * The method sets the JTextField values with the corresponding element from
     * the selected record based upon the index value.
     */
    private void setFields() {
        ((JLabel) this.pnlPosition.getComponent(0)).setText(RECORD +
                FORMAT.format(this.index + 1));
        ((JLabel) this.pnlPosition.getComponent(1)).setText(OF +
                FORMAT.format(ViewPlug.getData().size()));
        for (JTextField text : this.textFields) {
            text.setText((String) ViewPlug.getData().get(this.index).get(
                    this.textFields.indexOf(text)));
        }
    }

    /**
     * The actionPerformed method is used for the internal navigation of the 
     * single record pane. The method matches the action event source, with the 
     * different JButtons from the internal List of JButton and performs the
     * corresponding action.
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.buttons.get(0)) {
            this.setIndex(0); // First record
        } else if (e.getSource() == this.buttons.get(1)) {
            this.decrement(); // Preview
        } else if (e.getSource() == this.buttons.get(2)) {
            this.increment(); // Next
        } else if (e.getSource() == this.buttons.get(3)) {
            this.setIndex(ViewPlug.getData().size() - 1); // Last Record
        } else if (e.getSource() == this.buttons.get(4)) {
            ViewPlug.insertRow(0); // insert record
        } else if (e.getSource() == this.buttons.get(5)) {
            // Deleting a record
        }
    }

    /**
     * The JXPanel, is required to allow the single record pane to be scrollable.
     * This is required due to the drawbacks from not being able to add a normal
     * JPanel to a JScrollPane.
     */
    private class JXPanel extends JPanel implements Scrollable {

        private static final long serialVersionUID = 1L;

        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, getParent().getWidth(), height);
        }

        public Dimension getPreferredSize() {
            return new Dimension(getWidth(), getPreferredHeight());
        }

        public Dimension getPreferredScrollableViewportSize() {
            return super.getPreferredSize();
        }

        public int getScrollableUnitIncrement(Rectangle visibleRect,
                int orientation, int direction) {
            int hundredth = (orientation == SwingConstants.VERTICAL ? getParent().getHeight() : getParent().getWidth()) / 100;
            return (hundredth == 0 ? 1 : hundredth);
        }

        public int getScrollableBlockIncrement(Rectangle visibleRect,
                int orientation, int direction) {
            return orientation == SwingConstants.VERTICAL ? getParent().getHeight() : getParent().getWidth();
        }

        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        public boolean getScrollableTracksViewportHeight() {
            return false;
        }

        private int getPreferredHeight() {
            int rv = 0;
            for (int k = 0, count = getComponentCount(); k < count; k++) {
                Component comp = getComponent(k);
                Rectangle r = comp.getBounds();
                int height = r.y + r.height;
                if (height > rv) {
                    rv = height;
                }
            }
            rv += ((FlowLayout) getLayout()).getVgap();
            return rv;
        }
    }
}