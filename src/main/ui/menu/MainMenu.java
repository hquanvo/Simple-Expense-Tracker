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
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

// Represent the main menu, the first menu that the user will encounter
// CITATION: Various methods in this class are inspired from SimpleDrawingPlayer project provided by UBC CPSC 210
//           instructor team or from Java Swing tutorial provided by Oracle
public class MainMenu extends JFrame {
    private static final String FILE_LOCATION = "./data/tracker.json";
    protected static final int WIDTH = 1000;
    protected static final int HEIGHT = 800;

    private Tracker tracker; // Tracker
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private BudgetList data;

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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int a = JOptionPane.showConfirmDialog(table, "Do you want to save?");
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


        JLabel label = new JLabel("Information Panel");

        textArea = new
                JTextArea("Press 'Summarize' to receive information about this budget list!",HEIGHT, 80);
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
    // EFFECTS: Initialize the data by loading the data in, default to the first budget list in the save file
    private void initializeData() {
        loadTracker();
        data = tracker.getTrackerBudgetList(1);
        ArrayList<Entry> entries = data.getBudgetList();
        EntryTableModel model = (EntryTableModel) table.getModel();

        for (Entry entry : entries) {
            model.addEntry(entry);
        }
    }

    // getters
    public Tracker getTracker() {
        return tracker;
    }

    public BudgetList getData() {
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

    public  int getWidth() {
        return WIDTH;
    }


    // EFFECTS: Save the tracker to tracker.json
    public void saveTracker() {
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
