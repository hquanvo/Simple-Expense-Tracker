package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;

// Represent an add button
public class AddButton extends Button {

    public AddButton(MainMenu menu, JComponent area) {
        super(menu,"Add", area);
    }

    @Override
    protected void addListener() {

    }
}
