package ui.menu;

import javax.swing.*;
import java.awt.*;

// Represent the main menu, the first menu that the user will encounter
// CITATION: Various methods in this class
public class TestMenu extends JFrame {
    protected static final int WIDTH = 1000;
    protected static final int HEIGHT = 800;

    //private List<Button> mainMenuButtons;

    // EFFECTS: Construct a main menu, with various button included
    public TestMenu() {
        super("Expense Tracker");
        initializeGraphics();
        initializeListPanel();
    }

    // MODIFIES: this
    // EFFECTS: Create a JFrame window where the ExpenseTracker will operate
    protected void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: Creates a JPanel for listing budget list or entry
    protected void initializeListPanel() {
        JPanel listPanel = new JPanel();
        JLabel label = new JLabel("List");
        listPanel.add(label);
        getContentPane().add(GridBagLayout., listPanel);
    }
}
