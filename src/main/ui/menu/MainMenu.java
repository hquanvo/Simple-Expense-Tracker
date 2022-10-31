package ui.menu;

import javax.swing.*;
import java.awt.*;

// TODO: ADD INFORMATION ABOUT THIS CLASS
public class MainMenu {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    private JPanel mainPanel;
    private JLabel appTitle;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JTextField textField1;
    private JTextPane consoleDisplayArea;
    private JTextPane textPane1;
    private JTextPane textPane2;

    // TODO: ADD SPECIFICATIONS
    public MainMenu() {
        JFrame frame = new JFrame("Expense Tracker");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setVisible(true);
    }
}
