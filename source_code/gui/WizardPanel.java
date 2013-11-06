package gui;

/**
 * @author: JAVA Alliance
 * @purpose: The wizard panel is a abstract class which contains method signature declarations,
 * and stores the common functionality between the extended classes. The wizard panel extends JPanel, and creates 
 * a default centered panel; which will get updated with the extended type.
 * 
 * The wizard panel contructor gets called from the extended type via the super(title, image), and passes
 * the title, and associated image file to the Wizard Panel. The contructor instantiates the gui components, and addes them
 * onto the JPanel content pane. 
 * 
 * @date: 1st May 2009
 * @filename: WizardPanel.java
 * @version: 1.0
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import system.ViewPlug;
import system.Bol;

abstract class WizardPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private String title; // The title of the wizard panel stored within a instance field, which is later used for the JDialog.
    private JPanel centerPanel; // The centered panel is stored, which gets added to the content pane and later modified by the sub type.

    /**
     * The WizardPanel constructor receives the title string, and the image name, which can now be displayed in another place.
     * @param title
     * @param image
     */
    public WizardPanel(String title, String image) {
        this.title = title; // Sets the title.
        this.setLayout(new BorderLayout());

        // Initialises the WizardPanel gui.
        JPanel northPanel = new JPanel(new BorderLayout());  // make north panel
        northPanel.add(new JLabel(new ImageIcon(image)), BorderLayout.WEST);
        northPanel.add(new JLabel(title), BorderLayout.EAST);

        this.centerPanel = new JPanel();
        this.add(northPanel, BorderLayout.NORTH);
        this.add(this.centerPanel, BorderLayout.CENTER);
    }

    /**
     * This contains the main content of the panel which is be called once the panel 
     * has been activated.
     */
    public abstract void displayPanel();

    /**
     * The closePanel() method is used to update the contents of the wizard to the business
     * object layer once the form has been closed.
     * @return boolean.
     */
    public abstract boolean closePanel();

    /**
     * The error test method is used throughout the wizard panels, to show a message dialog
     * of the caught errors within the business object layer (bol). The boolean received, is the value
     * of the return from the functional methods within the bol. The value of false received prompts the error
     * display message box. 
     * @param b
     * @return
     */
    protected boolean errorTest(boolean b) {
        if (!b) { // if the boolean received is false, it will proceed to display the message dialog with the containing errors.
            String error = ViewPlug.getErrors();
            if (error.length() > 0) {
                JOptionPane.showMessageDialog(this, error);
            }
            return false;
        }
        return true;
    }

    /**
     * The getter for the centered panel, which allows for the contents of the sub type to be added.
     * @return
     */
    public JPanel getCenterPanel() {
        return this.centerPanel;
    }

    /**
     * The clearData method is used to clear the components stored within the centerPanel.
     */
    public void clearData() {
        this.centerPanel.removeAll();
    }

    /**
     * Allows for the title of the WizardPanel to be retreived. This is the title for each wizard
     * page, which would get displayed in the title bar of the dialog or frame.
     * @return
     */
    public String getTitle() {
        return this.title;
    }
}

/**
 * The GUIOpenFile is the file open page of the wizard. 
 * It allows the user to open and naviagate to a specific file on their system, and 
 * provides the facility of selecting the desired conversion file type 
 */
class GUIOpenFile extends WizardPanel implements WizardConstants {

    private static final long serialVersionUID = 1;
    private JTextField jtfLocation;
    private JComboBox jcbOpenType;

    /**
     * The contructor of the GUIOpenFile() instantiates the super(title, image) WizardPanel.
     */
    GUIOpenFile() {
        super(GUI_OPEN_FILE_TEXT[0], GUI_OPEN_FILE_TEXT[1]);
    }

    /**
     * The displayPanel() method initialises the components on the panel once the 
     * panel has been triggered via the wizard manager.
     */
    public void displayPanel() {
        JPanel center = new JPanel(); // creates the center JPanel, which adds the other components into.
        center.setLayout(new GridLayout(4, 2, 3, 3)); // The layout of the form is created in a grid of 2 columns and 4 rows down.

        this.jtfLocation = new JTextField();
        this.jcbOpenType = new JComboBox(Bol.IOType.values()); // The values of the enumerated IOType are passed into the JComboxBox

        JButton jbBrowse = new JButton(BROWSE); // The browse button gets instantiated, and a action listener is added.
        jbBrowse.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                openFileChooser(); // The browse button triggers the openFileChooser() method, which displays a File selection dialog.
            }
        });


        JLabel importType = new JLabel("File type:"); // The JLabel is added next to the combo box. 
        importType.setFont(new Font("", Font.BOLD + Font.ITALIC, 13)); // The font is changed.

        center.add(this.jtfLocation); // The JTextField for the file location is added on the left side of the browse button.
        center.add(jbBrowse);
        center.add(importType);
        center.add(jcbOpenType);
        this.getCenterPanel().add(center); // Adds the center panel to the content pane.
    }

    /**
     * The openFileChooser() method creates a new JFileChooser, and prompts the Dialog with some customization.
     */
    private void openFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(DOT));
        chooser.setDialogTitle(FILE_CHOOSER_TITLE);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.jtfLocation.setText(chooser.getSelectedFile().toString()); // Sets the path of the selected file to the location text field.
        }
    }

    /**
     * The closePanel() method does the following:
     *  1. Clears the data structure once the import process has been cancelled.
     *  2. Calls the data population method within the business object layer, passing the file location and type.
     *  3. Prompts the user with a error message, if the value of the population returns false.
     */
    public boolean closePanel() {
        ViewPlug.clearData();
        return errorTest(ViewPlug.populate(this.jtfLocation.getText(),
                (Bol.IOType) this.jcbOpenType.getSelectedItem()));
    }
}

/**
 * The GUISaveFile extends the WizardPanel, and provides the user with a panel for the saving 
 * of a file. It prompt for file selection, and also the desired file save type.
 */
class GUISaveFile extends WizardPanel implements WizardConstants {

    private JTextField jtfLocation = new JTextField();
    private JComboBox jcbSaveType;

    /**
     * The title and the image file path are passed to the super type instantiation.
     */
    GUISaveFile() {
        super(GUI_SAVE_FILE_TEXT[0], GUI_SAVE_FILE_TEXT[1]);
    }

    /**
     * The displayPanel() method initialises the panel.
     */
    public void displayPanel() {
        this.jcbSaveType = new JComboBox(Bol.IOType.values());

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(4, 2, 3, 3));
        this.jtfLocation = new JTextField();

        JButton jbBrowse = new JButton(BROWSE);
        jbBrowse.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                openFileChooser();
            }
        });
        center.add(this.jtfLocation);
        center.add(jbBrowse);
        JLabel saveType = new JLabel("Save Type:");
        saveType.setFont(new Font("", Font.BOLD + Font.ITALIC, 13));
        center.add(saveType);
        center.add(jcbSaveType);
        this.getCenterPanel().add(center, BorderLayout.CENTER);
    }

    /**
     * The openFileChooser() method creates a dialog file chooser for the selection of a new file.
     */
    private void openFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(DOT));
        chooser.setDialogTitle(GUI_SAVE_FILE_TEXT[0]);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setApproveButtonText(GUI_SAVE_FILE_TEXT[0]);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.jtfLocation.setText(chooser.getSelectedFile().toString());
        }
    }

    /**
     * The closePanel() method calls the saveData method of the business object layer,
     * and passes the file save type, and also the file location text. It returns a boolean
     * value representing whether it was sucessfull. It checks for any errors, and displays them
     * if needed.
     * @return boolean
     */
    public boolean closePanel() {
        return errorTest(ViewPlug.saveData((Bol.IOType) this.jcbSaveType.getSelectedItem(), this.jtfLocation.getText()));
    }
}

/**
 * The GUIColumnNames is the wizard panel used for the selection, and modification 
 * of the column name values. This panel has the functionality of browsing through the records,
 * change the header row values as desired, or select a already existing "header row"; which will
 * remove the same record selected from the structure. The CUIColumnNames panel, also has the function
 * of creating default column names if there is no value entered.
 */
class GUIColumnNames extends WizardPanel implements WizardConstants {

    private static final long serialVersionUID = 1;
    private static final String HEADER_NAME = "Header Name: ";
    private static final String EXAMPLE_DATA = "Example Data: ";
    private int index;
    private ArrayList<JLabel> labelFieldNames = new ArrayList<JLabel>();
    private ArrayList<JTextField> columnNames = new ArrayList<JTextField>();

    /**
     * The contructor doesn't receive any parameters, but it passes some constant values
     * to the super type for the title, and the image file.
     */
    GUIColumnNames() {
        super(GUI_COLUMN_NAMES_TEXT[0], GUI_COLUMN_NAMES_TEXT[1]);
    }

    /**
     * The displayPanel() method initialises the panel with the gui components.
     * It handles the actions for the more button: which incremements the current index value, and
     * also the header row checkbox. 
     */
    public void displayPanel() {
        this.getCenterPanel().setLayout(new BorderLayout());
        JPanel top = new JPanel(new BorderLayout());
        JCheckBox jcbHeaderRow = new JCheckBox(GUI_COLUMN_NAMES_TEXT[3]);
        JButton jbMoreData = new JButton(GUI_COLUMN_NAMES_TEXT[4]);
        top.add(jbMoreData, BorderLayout.EAST);
        top.add(jcbHeaderRow, BorderLayout.WEST);
        jcbHeaderRow.addActionListener(new ActionListener() { // Creating a new ActionListener for the HeaderRow checkbox.

            public void actionPerformed(ActionEvent e) {
                if (((JCheckBox) e.getSource()).isSelected()) { // If the checkbox value has been selected
                    increment(); // It increments the index
                    getHeaderRow(); // Grabs the Column Row.
                } else {
                    index = 0; // Otherwise it changes the index value to 0.
                    refreshExample(); // Refreshes the contents of the example with the element at that specific index
                }
            }
        });
        jbMoreData.addActionListener(new ActionListener() { // Creates a ActionListener for the More button.

            public void actionPerformed(ActionEvent e) {
                increment(); // Increments the index value once pressed.
                refreshExample(); // Refreshes the example displayed.
            }
        });

        // Creates a JPanel with a grid view of the data structure.
        JPanel listData = new JPanel(new GridLayout((ViewPlug.getData().get(0).size() + 1), 2, 5, 5));
        // Creates the label headers for the JPanel.
        listData.add(new JLabel(HEADER_NAME));
        listData.add(new JLabel(EXAMPLE_DATA));
        // Iterates through the data stored in the business object layer, and adds a JTextField and Label for each
        // item stored.
        for (int i = 0; i < ViewPlug.getData().get(0).size(); i++) {
            this.columnNames.add(new JTextField(10)); // Creates and adds a JTextField
            this.labelFieldNames.add(new JLabel());
            listData.add(this.columnNames.get(i));
            listData.add(this.labelFieldNames.get(i));
        }

        // Creates the JPanel object, and sets a border.
        JPanel border = new JPanel();
        border.setSize(listData.getWidth() + 50, listData.getHeight() + 50);
        border.add(listData);

        // Adds the border with the JPanel of the data displayed into the center region of the content pane.
        this.getCenterPanel().add(border, BorderLayout.CENTER);
        // Adds the top panel; containing the more and header buttons, to the north region.
        this.getCenterPanel().add(top, BorderLayout.NORTH);
        // Refreshes the example being displayed.
        this.refreshExample();
    }

    /**
     * The refreshExample() method gets the list from the List< Of List > data structure at that specific index.
     * Therefore providing a List of Object for each element contained at that row.
     */
    private void refreshExample() {
        List<Object> list = ViewPlug.getData().get(this.index); // Grabs the list of elements.
        for (Object text : list) { // Iterates the list.
            if (list.indexOf(text) < this.labelFieldNames.size()) { // checks whether the index element is of valid range.
                this.labelFieldNames.get(list.indexOf(text)).
                        setText((text.toString().length() > 15) ? text.toString().substring(0, 14) : text.toString());
            }
        }
    }

    /**
     * Sets the index value with the next increment. Contains a condition test to check whether it is in range.
     * It loops back to 0 if the index has been incremented higher than size of the data structure.
     */
    private void increment() {
        this.index = (this.index < (ViewPlug.getData().size() - 1)) ? ++this.index : 0;
    }

    /**
     * The getHeaderRow() method grabs the List from the data structure at that specific index.
     * It iterates through the elements contained in that received list, and checks whether the data received
     * does not exceed the columnNames size.
     */
    private void getHeaderRow() {
        // Grabs the list from the data structure.
        List<Object> list = ViewPlug.getData().get(((this.index > 0) ? (this.index - 1) : 0));
        for (Object text : list) { // Iterates for each element contained
            if (list.indexOf(text) < this.columnNames.size()) { // Checks whether the index value of that element is less than the column names size.
                // The column name JTextField is grabbed at the index value of the element.
                this.columnNames.get(list.indexOf(text)).
                        setText((text.toString().length() > 15) ? text.toString().substring(0, 14) : text.toString()); // Sets the column text, and substrings it to only contain 15 characters.
            }
        }
        ViewPlug.deleteRow(this.index-1); // Deletes the row grabbed into the header.
        this.refreshExample(); // Refreshes the displayed example.
    }

    /**
     * The setColumnNames() method iterates through the JTextField columnNames list, and adds the values
     * into the stored columnNames structure within the business object layer.
     */
    private void setColumnNames() {
        // Iterates through the JTextFields.
        for (int i = 0; i < (this.columnNames.size() - 1); i++) {
            ViewPlug.getColumnNames().add((this.columnNames.get(i).getText().
                    equals(EMPTY)) ? (COLUMN + i) : this.columnNames.get(i).
                    getText().replaceAll("[^a-zA-Z0-9]", EMPTY));
        }
    }

    /**
     * The closePanel() method is a required method declared in the WizardPanel.
     * The function of this method is to comit the update for the columns once the panel has been closed. 
     */
    public boolean closePanel() {
        this.setColumnNames();
        ViewPlug.update();
        return true;
    }

    /**
     * The clear data method is called from the wizard manager once the previous action has
     * been triggered. The clear data method allows the panel, and any associated data to be wiped when
     * the user has made a mistake within the wizard process.
     */
    public void clearData() {
        super.clearData();
        this.columnNames.clear();
        this.labelFieldNames.clear();
    }
}

/**
 * The GUI Export to database wizard provides the dbms package with a gui frontend
 * for exporting the internally stored structures to a chosen database format; which has
 * options for mysql, mssql, and postgresql. The database management systems in the combobox
 * are populated from the driver files supported in the dbms package.
 */
class GUIExportDatabase extends WizardPanel implements WizardConstants {

    private JTextField fldHostName,  fldUserName;
    private JPasswordField txfPassword;
    private JComboBox cboDatabase;

    @SuppressWarnings("empty-statement")
    GUIExportDatabase() {
        super(GUI_EXPORT_DATABSE_TEXT[0], GUI_EXPORT_DATABSE_TEXT[1]);
    }

    /**
     * The displayPanel method is a required method forced in by the abstract WizardPanel
     * class. The panel initialises the components for the selection of a dbms type, and for the entering of the 
     * hostname, username, and password. The method initialises the gui components; which includes the ComboBox, the fields,
     * and adds them onto the panel itself.
     */
    public void displayPanel() {
        JPanel center = new JPanel();
        Object[] dbList = ViewPlug.getVendors();
        if (dbList == null) {
            JOptionPane.showMessageDialog(this, ViewPlug.getErrors());
        }

        this.cboDatabase = new JComboBox(dbList);
        this.fldHostName = new JTextField();
        this.fldUserName = new JTextField();
        this.txfPassword = new JPasswordField();

        center.setLayout(new GridLayout(4, 2, 10, 5));
        center.add(new JLabel(GUI_EXPORT_DATABSE_TEXT[3]));
        center.add(this.cboDatabase);
        center.add(new JLabel(GUI_EXPORT_DATABSE_TEXT[4]));
        center.add(this.fldHostName);
        center.add(new JLabel(GUI_EXPORT_DATABSE_TEXT[5]));
        center.add(this.fldUserName);
        center.add(new JLabel(GUI_EXPORT_DATABSE_TEXT[6]));
        center.add(this.txfPassword);
        this.getCenterPanel().add(center, BorderLayout.CENTER);
    }

    /**
     * The closePanel method is called once the panel has been closed; which is once the next button has
     * been triggered via the wizard manager. The method calls the method in the ViewPlug, which calls another associated method
     * which is located elsewhere in the DBMS package. The connection for the database is held if it is processed successfully, otherwise
     * it will display a error message; notifying the user of the unsuccessful attempt, which provides them the 
     * for re-entering the details.
     */
    public boolean closePanel() {
        return errorTest(ViewPlug.getConnection(this.fldHostName.getText(),
                this.cboDatabase.getSelectedIndex(),
                this.fldUserName.getText(),
                this.txfPassword.getText()));//<<Matt 24/06>>
    }
}

/**
 * The GUI Database setup wizard, is the wizard panel used for the selection of the database from the 
 * connected DBMS; which gets the values automatically populated, and provides the option of entering their own.
 * The tables are also populated via the associated methods in the ViewPlug (which calls the other required methods, located elsewhere).
 */
class GUIDatabaseSetup extends WizardPanel implements WizardConstants {

    private JComboBox cboDatabase,  cboTables;

    /**
     * The initial constructor passes the some constants, which will get used for the creation of the WizardPanel.
     * @param ViewPlug
     */
    GUIDatabaseSetup() {
        super(GUI_DATABASE_SETUP_TEXT[0], GUI_DATABASE_SETUP_TEXT[1]);
    }

    /**
     * The displayError method is used throughout this panel for the checking as to whether the 
     * list has a element. If it doesn't have a element, it will display a error message alerting the user.
     * The method used for the database, and tables combobox. 
     * @param list
     */
    private void displayError(List<String> list) {
        String error = ViewPlug.getErrors();
        if ((list.size() == 0) && (error.length() > 0)) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    /**
     * The displayPanel method once again is a required method declared by the abstract wizard panel.
     * The method is used to initialise the components on the panel once it has been displayed.
     * The panel components which get initialised are the database combobox, and the table combobox. 
     * A action is also assigned to the database combobox, which will trigger the population of the table combobox with
     * tables retrieved from the selected database.
     */
    public void displayPanel() {
        JPanel center = new JPanel();
        ViewPlug.getColumnNames();
        this.cboDatabase = new JComboBox(); // Instantiates the gui components
        this.cboTables = new JComboBox();
        this.cboDatabase.setEditable(true); // Allows the user to enter their own values
        this.cboTables.setEditable(true);
        center.setLayout(new GridLayout(2, 4, 3, 3));
        center.add(new JLabel(DATABASE_NAME));
        center.add(cboDatabase);
        center.add(new JLabel(TABLE_NAME));
        center.add(cboTables);
        this.getCenterPanel().add(center, BorderLayout.CENTER);
        this.cboDatabase.addActionListener(new ActionListener() {
            // Creates a action listener for the 
            //population of the tables once a database has been selected.

            public void actionPerformed(ActionEvent e) {
                updateTables();
            }
        });
        this.updateCatalogs();
    }

    /**
     * The updateCatalogs method updates the database combobox with the available databases
     * from the connection.
     */
    private void updateCatalogs() {
        this.cboDatabase.removeAllItems();
        List<String> databases = ViewPlug.getCatalogs();
        for (String database : databases) {
            this.cboDatabase.addItem(database);
        }
        displayError(databases);
    }

    /**
     * The updateTables method populates the table combobox with the available tables from the 
     * selected database.
     */
    private void updateTables() {
        List<String> tables = ViewPlug.getTables((String) this.cboDatabase.getSelectedItem());

        this.cboTables.removeAllItems(); // Clears the table combobox before it gets populated.
        for (String table : tables) {
            this.cboTables.addItem(table);
        }
        displayError(tables); // Displays a error message if the structure doesn't contain any elements.
    }

    /**
     * The close panel method will finialise the wizard panel by sending the data to the selected database table.
     */
    public boolean closePanel() {

        return errorTest((ViewPlug.sendDataToDBMS((String) this.cboDatabase.getSelectedItem(),
                (String) this.cboTables.getSelectedItem())));

    }
}

/**
 * The interface contains some constants which will get used throughout the WizardPanels.
 */
interface WizardConstants {

    static final String[] GUI_SAVE_FILE_TEXT = {"Save File", "write.png",};
    static final String[] GUI_OPEN_FILE_TEXT = {"Open File", "write.png", "Import Page 1"};
    static final String[] GUI_COLUMN_NAMES_TEXT = {"Column Names", "write.png", "Import Page 2", "Make example header", "More Data"};
    static final String[] GUI_EXPORT_DATABSE_TEXT = {"Export to Database", "write.png", "Export Page 1",
        "Database:", "Host Name:", "User Name:", "Password:"};
    static final String[] GUI_DATABASE_SETUP_TEXT = {"Setup Database", "write.png", "Export Page 2"};
    static final String[] GUI_PRIMARY_KEY_TEXT = {"Primary Key", "write.png",
        "Export Page 3"};
    static final String BROWSE = "Browse";
    static final String DATA_SEPARATOR = "Data Separator";
    static final String FILE_CHOOSER_TITLE = "Please select your CSV File";
    static final String DOT = ".";
    static final String COLUMN = "Column";
    static final String DATABASE_NAME = "Database Name";
    static final String TABLE_NAME = "Table Name";
    static final String LOADING_ERROR = "File Load Error";
    static final String SPACE = " ";
    static final String COLON = ":" + SPACE;
    static final String EMPTY = "";
}