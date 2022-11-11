package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represent a quit button, prompts the user if they want to save before exiting
public class QuitButton extends Button {

    public QuitButton(MainMenu menu, JComponent area) {
        super(menu,"Quit", area);
    }

    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new QuitButtonClickHandler());
    }

    private class QuitButtonClickHandler implements ActionListener {

        // EFFECTS: Exit the app by pressing the button
        @Override
        public void actionPerformed(ActionEvent e) {
            int a = JOptionPane.showConfirmDialog(menu, "Do you want to save?");
            if (a == 0) {
                menu.saveTracker();
                System.exit(0);
            } else if (a == 1) {
                System.exit(0);
            }
            // do nothing if cancel was chosen
        }
    }
}
