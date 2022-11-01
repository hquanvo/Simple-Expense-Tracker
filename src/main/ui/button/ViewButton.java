package ui.button;

import ui.menu.TestMenu;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class ViewButton extends Button {

    public ViewButton(TestMenu menu, JComponent area) {
        super(menu, area);
    }

    @Override
    protected void setInteraction() {
        button.setMnemonic(MouseEvent.MOUSE_PRESSED);
    }

    @Override
    protected void createButton(JComponent area) {
        button = new JButton("View");
        button = customizeButton(button);
        addToButtonArea(area);
    }


}
