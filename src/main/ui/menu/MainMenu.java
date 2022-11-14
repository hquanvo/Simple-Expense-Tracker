package ui.menu;

import exceptions.NegativeAmountException;
import model.BudgetList;
import model.Entry;
import model.Tracker;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.button.Button;
import ui.button.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

// Represent the main menu, the first menu that the user will encounter
// CITATION: Various methods in this class are inspired from SimpleDrawingPlayer project provided by UBC CPSC 210
//           instructor team
public class MainMenu extends JFrame {
    private static final String FILE_LOCATION = "./data/tracker.json";
    protected static final int WIDTH = 1000;
    protected static final int HEIGHT = 800;
    private static final ImageIcon ICON = new ImageIcon("./src/main/ui/image/welcome.jpg");

    private Tracker tracker; // Tracker
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Vector<Vector<String>> data;
    private Vector<String> columnNames;
    private BudgetList currentList;

    private JTable table;
    private JTextArea textArea;
    private List<Button> buttons;

    public MainMenu() {
        super("Expense Tracker");
        initializeFields();
        makeSplashScreen();
        initializeGraphics();
        loadData();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: Display a splash screen
    private void makeSplashScreen() {
        JWindow splashScreen = new JWindow();
        splashScreen.setLayout(new BorderLayout());
        JLabel message = new JLabel("Now loading......");
        message.setFont(new Font("Helvetica", Font.BOLD, 20));
        JLabel image = new JLabel("", ICON, SwingConstants.CENTER);
        splashScreen.add(message, BorderLayout.NORTH);
        splashScreen.add(image, BorderLayout.CENTER);
        splashScreen.setBounds(WIDTH / 3, HEIGHT / 3, ICON.getIconWidth(),
                ICON.getIconHeight() + message.getHeight());

        splashScreen.setVisible(true);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        splashScreen.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: Initialize the fields
    private void initializeFields() {
        tracker = new Tracker();
        jsonWriter = new JsonWriter(FILE_LOCATION);
        jsonReader = new JsonReader(FILE_LOCATION);

        columnNames = new Vector<>();
        columnNames.addElement("ID");
        columnNames.addElement("DATE");
        columnNames.addElement("AMOUNT");
        columnNames.addElement("CATEGORY");
        columnNames.addElement("DESCRIPTION");

        data = new Vector<>();

        currentList = null;
    }

    // MODIFIES: this
    // EFFECTS: Creates the frame to store everything in
    private void initializeGraphics() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int a = showSaveOption();
                if (a == 0) {
                    saveTracker();
                    System.exit(0);
                } else if (a == 1) {
                    System.exit(0);
                }
                // do nothing if cancel was chosen
            }
        });
        setResizable(false);

        initializeTablePanel();
        initializeInfoPanel();
        initializeButtons();
        setVisible(true);
        revalidate();
        repaint();
    }

    // EFFECT: Show the save options to the user, and return the user's selection
    public int showSaveOption() {
        return JOptionPane.showConfirmDialog(this, "Would you like to save the tracker?");
    }

    // MODIFIES: this
    // EFFECTS: Creates the Table Panel and add them onto the frame
    private void initializeTablePanel() {
        JLabel label = new JLabel("Tracker Table");
        JPanel listPanel = new JPanel(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        customizeTable();

        JScrollPane scrollPanel = new JScrollPane(table);
        scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        listPanel.add(label, BorderLayout.NORTH);
        listPanel.add(scrollPanel);
        add(listPanel, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: customize the look of the table
    private void customizeTable() {
        table.setBackground(Color.white);
        table.setPreferredSize(new Dimension(WIDTH, HEIGHT / 2));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        TableColumnModel columnModel = table.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(WIDTH / 12);
        columnModel.getColumn(1).setPreferredWidth(WIDTH / 6);
        columnModel.getColumn(2).setPreferredWidth(WIDTH / 6);
        columnModel.getColumn(3).setPreferredWidth(WIDTH / 6);
        columnModel.getColumn(4).setPreferredWidth(WIDTH * 5 / 12);
    }

    // MODIFIES: this
    // EFFECTS: Create the Info Panel and add it onto the frame
    private void initializeInfoPanel() {
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.lightGray);
        infoPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT * 2 / 5));


        JLabel label = new JLabel("Information Panel");

        textArea = new JTextArea(HEIGHT, 80);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText("There is no budget list to show here. Press 'Add' to make a new one!");
        infoPanel.add(label, BorderLayout.NORTH);
        infoPanel.add(textArea);

        add(infoPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Create buttons and add them onto buttonPanel;
    private void initializeButtons() {
        Color color = new Color(107, 107, 107);
        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));
        buttonPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
        buttonPanel.setBackground(color);
        buttonPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 10));

        JPanel innerButtonPanel = new JPanel();
        innerButtonPanel.setBackground(color);

        buttons = new ArrayList<>();
        buttons.add(new AddButton(this, innerButtonPanel));
        buttons.add(new RemoveButton(this, innerButtonPanel));
        buttons.add(new SummarizeButton(this, innerButtonPanel));
        buttons.add(new SaveButton(this, innerButtonPanel));
        buttons.add(new LoadButton(this, innerButtonPanel));
        buttons.add(new QuitButton(this, innerButtonPanel));

        buttonPanel.add(innerButtonPanel);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: Loading the data in, default to the first budget list in the save file if save file is not empty.
    public void loadData() {
        loadTracker();
        if (tracker.getTrackerSize() > 0) {
            currentList = tracker.getTrackerBudgetList(1);
            ArrayList<Entry> entries = currentList.getBudgetList();
            for (Entry entry : entries) {
                Vector<String> row = new Vector<>();
                addToTable(entry, row);
            }
            displayStandardTextAreaMessage(currentList);
        }
    }

    // EFFECTS: Display the standard text message when a budget list is currently loaded in
    public void displayStandardTextAreaMessage(BudgetList budgetList) {
        textArea.setText("Currently showing data from " + budgetList.getName() + " budget list."
                + " Press 'Summarize' to receive information about this budget list!");
    }

    // MODIFIES: this, row
    // EFFECTS: Add a row into the budget list table
    public void addToTable(Entry entry, Vector<String> row) {
        row.addElement(data.size() + 1 + "");
        row.addElement(entry.getDate());
        row.addElement("$" + entry.getAmount());
        row.addElement(String.valueOf(entry.getCategory()));
        row.addElement(entry.getDescription());
        data.addElement(row);
    }

    // EFFECTS: Show the message indicating that the process has been stopped when pressing the close button
    public void showAbortMessage() {
        JOptionPane.showMessageDialog(this,
                "Process cancelled.", "Warning", JOptionPane.WARNING_MESSAGE);
    }

    // REQUIRES: chosenName must be a name of an existing budget list in budgetLists
    // MODIFIES: this
    // EFFECTS: Load a budget list onto the menu, changing which entries are displayed in the table
    public void loadBudgetList(String chosenName, ArrayList<BudgetList> budgetLists) {
        for (BudgetList budgetList : budgetLists) {
            if (chosenName.equals(budgetList.getName())) {
                currentList = budgetList;
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                tableModel.getDataVector().removeAllElements();
                ArrayList<Entry> entries = currentList.getBudgetList();
                for (Entry entry : entries) {
                    Vector<String> row = new Vector<>();
                    addToTable(entry, row);
                }
                displayStandardTextAreaMessage(currentList);
                repaint();
            }
        }
    }

    // getters
    public Tracker getTracker() {
        return tracker;
    }

    public Vector<Vector<String>> getData() {
        return data;
    }

    public JTable getTable() {
        return table;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    public BudgetList getCurrentList() {
        return currentList;
    }

    // EFFECTS: Save the tracker to tracker.json
    public void saveTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(tracker);
            jsonWriter.close();
            textArea.setText("All budget lists in the tracker has been successfully saved to " + FILE_LOCATION + ".");
        } catch (FileNotFoundException e) {
            textArea.setText("Saving failed, unable to write to " + FILE_LOCATION);

        }
    }

    // MODIFIES: this
    // EFFECTS: Loads the tracker from tracker.json
    private void loadTracker() {
        try {
            tracker = jsonReader.read();
        } catch (IOException e) {
            textArea.setText("Unable to load budget lists from " + FILE_LOCATION);
        } catch (NegativeAmountException e) {
            textArea.setText("Attempted to read an impossible file, unable to load.");
        }
    }
}
