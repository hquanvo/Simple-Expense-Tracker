package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitButton extends Button {

    public QuitButton(MainMenu menu, JComponent area) {
        super(menu,"Quit", area);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new QuitButtonClickHandler());
    }

    private class QuitButtonClickHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
