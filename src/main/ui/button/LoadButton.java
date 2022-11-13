package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;

// Represent a load button, allowing user to load a different budget list onto the tracker
public class LoadButton extends Button {

    // EFFECTS: Creates a load button and add it onto the JComponent area
    public LoadButton(MainMenu menu, JComponent area) {
        super(menu,"Load", area);
    }

    @Override
    protected void addListener() {

    }

    // MODIFIES: this
    // EFFECTS: Set the tooltip of the button when hovering a mouse over it
    @Override
    protected void setTooltip() {
        button.setToolTipText("Load a budget list to display");
    }
}
