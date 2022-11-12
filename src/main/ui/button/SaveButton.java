package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a save button
public class SaveButton extends Button {

    // EFFECTS: Create a save button and add it onto the JComponent area
    public SaveButton(MainMenu menu, JComponent area) {
        super(menu, "Save", area);
    }

    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new SaveButtonClickHandler());
    }

    // MODIFIES: this
    // EFFECTS: set the tooltip of the button when hovering a mouse over it
    @Override
    protected void setTooltip() {
        button.setToolTipText("Save the tracker");
    }

    private class SaveButtonClickHandler implements ActionListener {

        // EFFECTS: Save the data by pressing the button
        @Override
        public void actionPerformed(ActionEvent e) {
            menu.saveTracker();
        }
    }
}
