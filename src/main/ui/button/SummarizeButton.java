package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;

public class SummarizeButton extends Button {

    public SummarizeButton(MainMenu menu, JComponent area) {
        super(menu, "Summarize", area);
    }

    @Override
    protected void addListener() {

    }
}
