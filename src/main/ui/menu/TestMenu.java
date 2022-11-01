package ui.menu;

import exceptions.NegativeAmountException;
import model.Tracker;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.button.Button;
import ui.button.ViewButton;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Represent the main menu, the first menu that the user will encounter
// CITATION: Various methods in this class
public class TestMenu extends JFrame {
    private static final String FILE_LOCATION = "./data/tracker.json";
    protected static final int WIDTH = 1000;
    protected static final int HEIGHT = 800;

    private JSplitPane splitPane;
    private JScrollPane listPane;
    private JEditorPane messagePane;

    private List<Button> buttons;

    private Scanner input; // Enter users' input
    private Tracker tracker; // Tracker
    private JsonReader jsonReader; // Reader for loading
    private JsonWriter jsonWriter; // Writer for saving

    // MODIFIES: this
    // EFFECTS: Initialize the tracker
    private void initializeApp() {
        input = new Scanner(System.in);
        tracker = new Tracker();
        jsonWriter = new JsonWriter(FILE_LOCATION);
        jsonReader = new JsonReader(FILE_LOCATION);
        loadTracker();
    }

    // EFFECTS: Construct a main menu, with various button included
    public TestMenu() {
        super("Expense Tracker");
        initializeApp();
        initializeGraphics();

    }

    // MODIFIES: this
    // EFFECTS: Create a JFrame window where the ExpenseTracker will operate
    protected void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        initializeSplitPane();
        getContentPane().add(splitPane);

        initializeButtons();

    }

    // MODIFIES: this
    // EFFECTS: Create a JSplitPane component that nests a JScrollPane and a JPanel
    private void initializeSplitPane() {
        listPane = new JScrollPane(new JList(tracker.getTracker().toArray()));
        messagePane = new JEditorPane();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPane, messagePane);

        listPane.setMinimumSize(new Dimension(WIDTH * 6 / 10, HEIGHT * 6 / 10));
        messagePane.setMinimumSize(new Dimension(WIDTH * 4 / 10, HEIGHT * 6 / 10));
        splitPane.setMinimumSize(new Dimension(WIDTH, HEIGHT * 6 / 10));
    }

    // MODIFIES: this
    // EFFECTS: Create a list of buttons component to be added into the frame
    private void initializeButtons() {
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new GridLayout(0, 1));
        buttonArea.setSize(new Dimension(0, 0));
        add(buttonArea, BorderLayout.SOUTH);

        buttons.add(new ViewButton(this, buttonArea));
        buttons.add(new ViewButton(this, buttonArea));
        buttons.add(new ViewButton(this, buttonArea));

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
