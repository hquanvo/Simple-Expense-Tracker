package ui.menu;

import ui.button.*;
import ui.button.Button;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

// Represent the main menu, the first menu that the user will encounter
// CITATION: Various methods in this class
public class MainMenu extends JFrame {
    private static final String FILE_LOCATION = "./data/tracker.json";
    protected static final int WIDTH = 1000;
    protected static final int HEIGHT = 800;


    private List<Button> buttons;

    public MainMenu() {
        super("Expense Tracker");
        initializeGraphics();

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
    // EFFECTS: Creates panels and format them onto the frame
    private void initializeTablePanel() {
        // dummy data for testing, will be deleted later
        String data[][]={ {"2022-09-01","1300","RENT", "Monthly Rent"},
                {"2022-09-01","1300","RENT", "Monthly Rent"},
                {"2022-09-01","1300","RENT", "Monthly Rent"}};
        String column[]={"DATE","AMOUNT","CATEGORY", "DESCRIPTION"};
        JTable listPanel = new JTable(data, column);
        JScrollPane scrollPanel = new JScrollPane(listPanel);
        listPanel.setBackground(Color.gray);
        listPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 2));
        scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPanel, BorderLayout.PAGE_START);
        validate();
    }

    private void initializeInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.lightGray);
        infoPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT * 2 / 5));
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
}
