package ui.button;

import ui.menu.MainMenu;
import ui.menu.RemoveMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represent a remove button, allowing user to remove an entry or a budget list
public class RemoveButton extends Button {

    // EFFECTS: Creates a remove button and add it onto the JComponent area
    public RemoveButton(MainMenu menu, JComponent area) {
        super(menu,"Remove", area);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new RemoveButtonClickHandler());
    }

    // MODIFIES: this
    // EFFECTS: Set the tooltip of the button when hovering a mouse over it
    @Override
    protected void setTooltip() {
        button.setToolTipText("Remove an entry from this budget list, or remove a budget list from the tracker");
    }

    private class RemoveButtonClickHandler implements ActionListener {

        // EFFECTS: Create the remove menu that many operations can be performed on
        @Override
        public void actionPerformed(ActionEvent e) {
            new RemoveMenu(menu);
        }
    }
}
