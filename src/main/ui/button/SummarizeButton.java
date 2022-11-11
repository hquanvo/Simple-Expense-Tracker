package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Represent a summarize button
public class SummarizeButton extends Button {

    public SummarizeButton(MainMenu menu, JComponent area) {
        super(menu, "Summarize", area);
    }

    // EFFECTS: associate button with a new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new SummarizeButtonClickHandler());
    }

    // EFFECTS: Print a summary line, corresponding to the category at i position in the summary list
    private String printSummaryMessage(Double amt, int i) {
        switch (i) {
            case 1:
                return "Rent: " + amt + "\n";
            case 2:
                return "Food: " + amt + "\n";
            case 3:
                return "Supplies: " + amt + "\n";
            case 4:
                return "Bills: " + amt + "\n";
            case 5:
                return "Others: " + amt + "\n";
            case 6:
                return "In total: you spent " + amt + " in this budget list.";
        }
        return "";
    }

    private class SummarizeButtonClickHandler implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Print a summary on the info panel about the budget list
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Double> summary = menu.getData().summarize();
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
