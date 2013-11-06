package gui;

/**
 * @author: JAVA Alliance
 * @purpose: The wizard class is the controller of the desired wizard to be created;
 * it allows for additional panels to be registered, and stores these registered 
 * panels inside a card layout in the WizardManager. This cardlayout contains the registerd panels
 * which extend WizardPanel. The abstract wizard panel provides the additional functionality 
 * for the wizards to be processed, and initialised, once the cardlayout panel has been viewed.
 * @filename: Wizard.java
 * @version: 1.0
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class Wizard extends JDialog {

    private static final String NEXT = "NEXT";
    private static final String FINISH = "FINISH";
    private static final Dimension WIZARD_SIZE = new Dimension(500, 300);
    private WizardManager wizardManager;
    private LinkedList<JButton> buttons;
    private JScrollPane scroll;

    /**
     * The wizard receives the parent JFrame, and instantiates the wizard manager
     * passing in the wizard object for the ability to update the main display with the new panel title. The
     * wizard panel is a JPanel, and is added to the Wizard Dialog within a scrollable area.
     * @param JFrame parent
     */
    public Wizard(JFrame parent) {
        super(parent); // Instantiating the super JDialog.
        // Instantiates the wizard manager and passes in the reference to the Wizard dialog.
        this.wizardManager = new WizardManager(this);
        this.buttons = new LinkedList<JButton>(); // Instantiates a linked list of JButtons for the next and previous buttons.
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.initialiseForm(); // The initialise form method creates the user interface components, and adds them onto the JDialog.
        this.setSize(WIZARD_SIZE); // The wizard size is statically set.
        this.setLocationRelativeTo(parent); // The location of the dialog box is set to a relative position to the parent JFrame.
        this.setVisible(true); // Sets the visibility to true.
    }

    /**
     * The following initialiseForm method creats the button box, iterates through the 
     * ButtonAction enumeration values, and adds them onto the Linked list of buttons.
     * The action performed event is assigned through a anonymous action listener, which forwards the set
     * action command of the ActionEvent (which is the enumeration value) to a doAction method within the instance field object
     * of WizardManager.
     */
    private void initialiseForm() {
        Box buttonBox = new Box(BoxLayout.X_AXIS);
        buttonBox.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));

        JSeparator separator = new JSeparator(); // Creates a separator, for the button box width.
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(separator, BorderLayout.NORTH);
        buttonPanel.add(buttonBox, BorderLayout.EAST); // Places the button box within a buttonPanel to the east region.

        for (Object object : WizardManager.ButtonAction.values()) { // Iterates through the enumeration values.
            this.buttons.add(new JButton(object.toString())); // Instantiates a new button with the enumeration text.
            this.buttons.getLast().setActionCommand(object.toString()); // Sets the button with the ActionCommand of the enumeration value.
            this.buttons.getLast().addActionListener(new ActionListener() { // Creates the annoymous action listener event.

                public void actionPerformed(ActionEvent e) {
                    // forwards the action onto the WizardManager (which processes the previous or next, and triggers the corresponding operation.
                    wizardManager.doAction(WizardManager.ButtonAction.valueOf(e.getActionCommand()));
                }
            });
            buttonBox.add(this.buttons.getLast()); // Adds the button to the buttonBox.
            buttonBox.add(Box.createHorizontalStrut(20)); // Creates a horizontal strut.
        }

        this.wizardManager.setSize(WIZARD_SIZE); // Sets the size of the wizard manager, which is the locking size.
        this.wizardManager.setFrameSize(WIZARD_SIZE); // Sets the size of the Frame, which gets used once the scrollpane has been applied.
        // Instantiates the new JScrollPane, and passes in the wizardManager (which extends JPanel) and applies some settings.
        this.scroll = new JScrollPane(this.wizardManager,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // The preferred size of the wizard is set, in order to lock the contents of the scrollpane (and enable scrolling for any remaining height).
        this.scroll.setPreferredSize(WIZARD_SIZE);

        // The scroll pane is added to the content pane, and positioned to the center region.
        this.getContentPane().add(this.scroll, BorderLayout.CENTER);
        // The button panel is added to the south position of the content pane.
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * The display method shows the next available card in the cardlayout within the wizardPanel JPanel.
     * The setPanel method is never called if the component count of the wizardManager is equal to zero.
     */
    public void display() {
        if (this.wizardManager.getComponentCount() > 0) {
            this.wizardManager.setPanel();
        }
    }

    /**
     * The dialog box is updated with the new title of the selected panel, and the next button
     * is changed to finish if the wizard manager doesn't contain anymore panels.
     * @param title
     */
    public void updateForm(String title) {
        this.setTitle(title); // The title of the JDialog is set to the new panel.
        // The button text is checked and set accordinly.
        this.buttons.get(1).setText((this.wizardManager.haveMorePanels()) ? NEXT : FINISH);
        // The previous button is disabled if the index is not > 0.
        this.buttons.get(0).setEnabled((this.wizardManager.getIndex() > 0));
    }

    /**
     * The close form method is called if the user has pressed the cancel button, 
     * or has finished the wizard. The wizard falls out of scope once the visibility has been set to false.
     */
    public void closeForm() {
        this.setVisible(false);
    }

    /**
     * The register panel method, adds the panel received to the card layout (which extends
     * wizard panel) and has additional functionality; more than the average JPanel object.
     * The identifier is received, which is just a string normally, and so is the WizardPanel.
     * The register panel method of the wizardManager is then called and gets the received parameters.
     * @param identifier: the string identifier of the cardlayout.
     * @param panel: the panel to be added to the cardlayout.
     */
    public void registerPanel(Object identifier, WizardPanel panel) {
        this.wizardManager.registerPanel(identifier, panel);
    }
}

/**
 * The WizardManager extends JPanel, and acts as a controller for the containing cardlayout.
 * The cardlayout contains WizardPanels which are JPanels (which support additional functionality for the
 * clearing of data once the previous panel is navigated upon, the initialisation of the panel once it has 
 * first been navigated on, and the sending of the completed processed data to the main business object layer
 * once the form has been correctly filled out.
 */
class WizardManager extends JPanel {

    // The ButtonAction commands allow the panel to be navigated forward (viewing the next wizard)
    // The navigation of viewing the previous panel; which clears/ resets the existing data.
    // The cancelling of the wizard.
    public enum ButtonAction {

        PREVIOUS, NEXT, CANCEL
    };
    // Contains a referrence to the Parent wizard object, to allow for updating the panel title on the dialog box.
    private Wizard wizard;
    private int index; // The current index of the panel being browsed within the cardlayout.
    private Dimension size; // The size of the wizardmanager.

    /**
     * The wizard manager constructor receives the parent wizard, and assigns it to a referrence
     * to a instance field. The referrence allows the parent JDialog to be updated with the new dialog title,
     * and also allows for the buttons to be disabled.
     * @param wizard
     */
    WizardManager(Wizard wizard) {
        this.wizard = wizard; // The referrence is assigned.
        this.setLayout(new CardLayout()); // The layout of itself (JPanel) is set to a CardLayout.
        this.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10))); // The border is applied, to give a indented feel.
        this.index = 0; // The index is set to 0. (which represents the first Card in the WizardManager.
    }

    /**
     * The WizardPanel is added to the cardlayout, and casted back to a JPanel.
     * The object can be recasted back to a WizardPanel (to allow for the use of the additional
     * functionality defined int the WizardPanel) once the object has been retreived back at a later stage. 
     * @param identifier
     * @param wizardPanel
     */
    void registerPanel(Object identifier, WizardPanel wizardPanel) {
        this.add((JPanel) wizardPanel, identifier);
    }

    /**
     * The method returns a boolean value representing whether they have more panels.
     * @return - boolean representation of more panels.
     */
    boolean haveMorePanels() {
        return (this.index < (this.getComponentCount() - 1));
    }

    /**
     * The method gets the index.
     * @return - int index value of the current card.
     */
    int getIndex() {
        return this.index;
    }

    /**
     * The do action method receives a ButtonAction Enumeration type. The method is called from
     * the annoymous action listner class added to the button (which passes in the set action command). 
     * The method does a switch test to determine what enumeration type has been selected.
     * @param action - The ButtonAction enumeration type.
     */
    void doAction(ButtonAction action) {
        switch (action) {
            case NEXT:
                this.next(); // Performs the next method increments the cardlayout index, and shows the next card.
                break;
            case PREVIOUS:
                this.previous(); // Decrements index, and shows the previous card. 
                break;
            case CANCEL:
                this.wizard.closeForm(); // Closes the JDialog.
                break;
        }
    }

    /**
     * Once the next method is called, it will grab the component out of itself
     * from the index value and casted to a WizardPanel. It validates whether the 
     * card can be showned, and if it can it cycles to the next card, and sets the panel.
     * Otherwise it has determined that the card is out of range, and closes the form.
     */
    private void next() {
        if (((WizardPanel) this.getComponent(this.index)).closePanel()) {
            if ((this.index + 1) < (this.getComponentCount())) {
                this.index++;
                // This will therefore initiate the panels components before 
                // the panel is displayed.
                this.setPanel();
                // Goes to the next panel.
                ((CardLayout) this.getLayout()).next(this);
            } else {
                this.wizard.closeForm();
            }
        }
    }

    /**
     * The previous method decrements the index, and sets the panel with the 
     * previous card. The setPanel method performs the added functionality to the 
     * WizardPanel; allowing the data to be cleared, and for the components to be
     * reinitialised onto the wizard panel. 
     */
    private void previous() {
        if ((this.index - 1) >= 0) {
            this.index--;
            this.setPanel();
            ((CardLayout) this.getLayout()).previous(this);
        }
    }

    /**
     * The setPanel() method gets the current component from the JPanel, and casts it
     * back to a WizardPanel; then performs the additional functionality, such as, clearing
     * the wizard panel data structures being used, and displays the panel by reinitialising the 
     * gui components. 
     */
    void setPanel() {
        // Removes all the previous components added in the panel.
        ((WizardPanel) this.getComponent(this.index)).clearData();
        // Launches the initiation of the panel
        ((WizardPanel) this.getComponent(this.index)).displayPanel();
        this.setFrameSize(((WizardPanel) this.getComponent(this.index)).getCenterPanel().getPreferredSize());
        // Forwards the title to the main JDialog
        this.wizard.updateForm(((WizardPanel) this.getComponent(this.index)).getTitle());
    }

    /**
     * The size of the WizardManager is contained within a instance dimension field, which
     * allows for the size of the wizard manager to be changed to the size of the selected
     * WizardPanel; allowing for the scrollpane to function correctly.
     * @param size
     */
    public void setFrameSize(Dimension size) {
        this.size = new Dimension(size.width, (size.height + 50)); // Adds a extra 50 pixels, to allow for a bit of additional space.
    }

    /**
     * The getPrefferedSize() method is overrided, and instead of returning the preferred size
     * of the wizard manager panel; it will return the instance size dimension.
     */
    @Override
    public Dimension getPreferredSize() {
        return this.size;
    }
}