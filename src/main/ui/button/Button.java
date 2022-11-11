package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;
import java.awt.*;

// CITATION: Button its subclasses' codes are modifies from SimpleDrawingPlayer project provided by UBC CPSC 210
//           instructor team
public abstract class Button {

    protected JButton button;
    protected MainMenu menu;

    protected Button(MainMenu menu, String name, JComponent area) {
        button = new JButton(name);
        button = customizeButton(button);
        area.add(button);
        this.menu = menu;
        addListener();

    }

    // MODIFIES: this, button
    // EFFECTS: Customize the button
    private JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        button.setPreferredSize(new Dimension(150, 60));
        button.setBackground(Color.getHSBColor(200, 95, 89));
        return button;
    }

    // EFFECTS: add a listener to the button
    protected abstract void addListener();
}
