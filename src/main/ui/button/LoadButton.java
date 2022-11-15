package ui.button;

import model.BudgetList;
import model.Tracker;
import ui.menu.LoadMenu;
import ui.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represent a load button, allowing user to a different budget list onto the menu
public class LoadButton extends Button {

    // EFFECTS: Creates a load button and add it onto the JComponent area
    public LoadButton(MainMenu menu, JComponent area) {
        super(menu,"Load", area);
    }

    // MODIFIES: this
    // EFFECTS: Associate button with a new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new LoadButtonClickHandler());
    }

    // MODIFIES: this
    // EFFECTS: Set the tooltip of the button when hovering a mouse over it
    @Override
    protected void setTooltip() {
        button.setToolTipText("Load a budget list to display");
    }

    private class LoadButtonClickHandler implements ActionListener {

        // MODIFIES: this
        // EFFECTS: Create a load prompt to load a different budget list into the menu
        @Override
        public void actionPerformed(ActionEvent e) {
            new LoadMenu(menu);

        }
    }
}
