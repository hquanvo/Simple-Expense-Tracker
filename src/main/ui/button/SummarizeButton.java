package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Represent a summarize button and its functionalities
public class SummarizeButton extends Button {

    // EFFECTS: Create a summarize button and add it onto the JComponent area
    public SummarizeButton(MainMenu menu, JComponent area) {
        super(menu, "Summarize", area);
    }

    // MODIFIES: this
    // EFFECTS: associate button with a new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new SummarizeButtonClickHandler());
    }

    // MODIFIES: this
    // EFFECTS: set the tooltip of the button when hovering a mouse over it
    @Override
    protected void setTooltip() {
        button.setToolTipText("Generate and display a quick summary of the budget list onto the information panel");
    }

    // EFFECTS: Print a summary line, corresponding to the category at i position in the summary list
    private String printSummaryMessage(Double amt, int i) {
        switch (i) {
            case 1:
                return "Rent: " + amt + "$ \n";
            case 2:
                return "Food: " + amt + "$ \n";
            case 3:
                return "Supplies: " + amt + "$ \n";
            case 4:
                return "Bills: " + amt + "$ \n";
            case 5:
                return "Others: " + amt + "$ \n";
            case 6:
                return "In total: you spent " + amt + "$ in this budget list.";
        }
        return "";
    }

    private class SummarizeButtonClickHandler implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Print a summary on the info panel about the budget list
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Double> summary = menu.getCurrentList().summarize();
            JTextArea textArea = menu.getTextArea();
            StringBuilder msg = new StringBuilder("In summary: \n");
            for (int i = 1; i <= 6; i++) {
                Double amt = summary.get(i - 1);
                msg.append(printSummaryMessage(amt, i));
            }
            String message = msg.toString();
            textArea.setText(message);
        }
    }
}
