package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;

// Represent a load button
public class LoadButton extends Button {

    public LoadButton(MainMenu menu, JComponent area) {
        super(menu,"Load", area);
    }

    @Override
    protected void addListener() {

    }
}
