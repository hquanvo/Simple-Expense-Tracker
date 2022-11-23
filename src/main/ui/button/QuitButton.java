package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represent a quit button, prompts the user if they want to save before exiting
public class QuitButton extends Button {

    // EFFECTS: Create a quit button and add it onto the JComponent area
    public QuitButton(MainMenu menu, JComponent area) {
        super(menu,"Quit", area);
    }

    // MODIFIES: this
    // EFFECTS: Associate button with new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new QuitButtonClickHandler());
    }

    // MODIFIES: this
    // EFFECTS: Set the tooltip of the button when hovering a mouse over it
    @Override
    protected void setTooltip() {
        button.setToolTipText("Quit the app");
    }

    private class QuitButtonClickHandler implements ActionListener {

        // EFFECTS: Exit the app
        @Override
        public void actionPerformed(ActionEvent e) {
            menu.exitPrompt();
        }
    }
}
