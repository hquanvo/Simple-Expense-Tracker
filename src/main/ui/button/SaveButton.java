package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a save button
public class SaveButton extends Button {

    public SaveButton(MainMenu menu, JComponent area) {
        super(menu, "Save", area);
    }

    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new SaveButtonClickHandler());
    }

    private class SaveButtonClickHandler implements ActionListener {

        // EFFECTS: Save the data by pressing the button
        @Override
        public void actionPerformed(ActionEvent e) {
            menu.saveTracker();
        }
    }
}
