package ui.menu;

import exceptions.NegativeAmountException;
import model.BudgetList;
import model.Entry;
import model.Tracker;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.button.*;
import ui.button.Button;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

// Represent the main menu, the first menu that the user will encounter
// CITATION: Various methods in this class
public class MainMenu extends JFrame {
    private static final String FILE_LOCATION = "./data/tracker.json";
    protected static final int WIDTH = 1000;
    protected static final int HEIGHT = 800;

    private Tracker tracker; // Tracker
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JTable table;
    private JTextArea textArea;
    private List<Button> buttons;

    public MainMenu() {
        super("Expense Tracker");
        initializeTracker();
        initializeGraphics();
        initializeData();

    }

    // MODIFIES: this
    // EFFECTS: Initialize the tracker
    private void initializeTracker() {
        tracker = new Tracker();
        jsonWriter = new JsonWriter(FILE_LOCATION);
        jsonReader = new JsonReader(FILE_LOCATION);
    }

    // MODIFIES: this
    // EFFECTS: Creates the frame to store everything in
    private void initializeGraphics() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initializeTablePanel();
        initializeInfoPanel();
        initializeButtons();
        pack();
        setVisible(true);
        revalidate();

    }

    // MODIFIES: this
    // EFFECTS: Creates the Table Panel and add them onto the frame
    private void initializeTablePanel() {
        JLabel label = new JLabel("Tracker Table");
        JPanel listPanel = new JPanel(new BorderLayout());

        table = new JTable(new EntryTableModel());
        table.setBackground(Color.white);
        table.setPreferredSize(new Dimension(WIDTH, HEIGHT / 2));

        JScrollPane scrollPanel = new JScrollPane(table);
        scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        listPanel.add(label, BorderLayout.NORTH);
        listPanel.add(scrollPanel);
        add(listPanel, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: Create the Info Panel and add it onto the frame
    private void initializeInfoPanel() {
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.lightGray);
        infoPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT * 2 / 5));


        JLabel label = new JLabel("Information panel");

        textArea = new JTextArea("INFORMATION ABOUT THE BUDGET LIST WILL DISPLAY HERE",HEIGHT, 80);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        infoPanel.add(label, BorderLayout.NORTH);
        infoPanel.add(textArea);

        add(infoPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Create buttons and add them onto buttonPanel;
    private void initializeButtons() {
        Color color = Color.CYAN;
        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));
        buttonPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
        buttonPanel.setBackground(color);
        buttonPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 10));

        JPanel innerButtonPanel = new JPanel();
        innerButtonPanel.setBackground(color);

        buttons = new ArrayList<>();
        buttons.add(new AddButton(innerButtonPanel));
        buttons.add(new RemoveButton(innerButtonPanel));
        buttons.add(new CompareButton(innerButtonPanel));
        buttons.add(new SaveButton(innerButtonPanel));
        buttons.add(new LoadButton(innerButtonPanel));
        buttons.add(new QuitButton(innerButtonPanel));

        buttonPanel.add(innerButtonPanel);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: Initialize the data by loading the data in, default to the first budget list in the save file
    private void initializeData() {
        loadTracker();
        BudgetList budgetList = tracker.getTrackerBudgetList(1);
        ArrayList<Entry> entries = budgetList.getBudgetList();
        EntryTableModel model = (EntryTableModel) table.getModel();

        for (Entry entry : entries) {
            model.addEntry(entry);
        }
    }

    // EFFECTS: Save the tracker to tracker.json
    private void saveTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(tracker);
            jsonWriter.close();
            System.out.println("All budget lists in the tracker has been successfully saved to " + FILE_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Saving failed, unable to write to " + FILE_LOCATION);

        }
    }

    // MODIFIES: this
    // EFFECTS: Loads the tracker from tracker.json
    private void loadTracker() {
        try {
            tracker = jsonReader.read();
            System.out.println("All budget lists successfully loaded from " + FILE_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to load budget lists from " + FILE_LOCATION);
        } catch (NegativeAmountException e) {
            System.out.println("Attempted to read an impossible file, unable to load.");
        }
    }




}
