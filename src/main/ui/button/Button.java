package ui.button;

import ui.menu.TestMenu;

import javax.swing.*;

public abstract class Button {

    protected JButton button;
    protected TestMenu menu;

    // TODO: ADD CITATION
    public Button(TestMenu menu, JComponent area) {
        this.menu = menu;
        createButton(area);
        addToButtonArea(area);
        setInteraction();
    }

    // EFFECTS: creates button
    protected abstract void createButton(JComponent parent);

    // EFFECTS: adds a listener for this button
    protected abstract void setInteraction();

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToButtonArea(JComponent area) {
        area.add(button);
    }

    // MODIFIES: this
    // EFFECTS:  customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }
}
