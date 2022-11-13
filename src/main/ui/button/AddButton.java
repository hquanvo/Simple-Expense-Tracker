package ui.button;

import ui.menu.AddMenu;
import ui.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represent an add button, allowing user to add a new entry or budget list
public class AddButton extends Button {

    // EFFECTS: Creates an add button and add it onto the JComponent area
    public AddButton(MainMenu menu, JComponent area) {
        super(menu,"Add", area);
    }

    // MODIFIES: this
    // EFFECTS: Associate button with new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new AddButtonClickHandler());
    }

    // MODIFIES: this
    // EFFECTS: Set the tooltip of the button when hovering a mouse over it
    @Override
    protected void setTooltip() {
        button.setToolTipText("Add an entry to this budget list, or make a new budget list to the tracker");
    }

    private class AddButtonClickHandler implements ActionListener {

        // EFFECTS: Create the add menu that many operations can be performed on
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddMenu(menu);
        }
    }
}
