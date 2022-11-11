package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;

public class RemoveButton extends Button {

    public RemoveButton(MainMenu menu, JComponent area) {
        super(menu,"Remove", area);
    }

    @Override
    protected void addListener() {

    }
}
