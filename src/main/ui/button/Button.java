package ui.button;

import ui.menu.MainMenu;

import javax.swing.*;
import java.awt.*;

// CITATION: Button its subclasses' codes are inspired from SimpleDrawingPlayer project provided by UBC CPSC 210
//           instructor team
public abstract class Button {

    protected JButton button;
    protected MainMenu menu;

    // EFFECTS: Create a button with a name, customize it, add a listener and add it onto the JComponent
    protected Button(MainMenu menu, String name, JComponent area) {
        button = new JButton(name);
        button = customizeButton(button);
        area.add(button);
        this.menu = menu;
        addListener();
        setTooltip();

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

    // EFFECTS: set the tooltip of the button when hovering a mouse over it
    protected abstract void setTooltip();
}
