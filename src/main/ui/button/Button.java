package ui.button;

import javax.swing.*;
import javax.swing.text.StyleConstants;
import java.awt.*;

public abstract class Button {

    protected JButton button;

    protected Button(String name, JComponent area) {
        button = new JButton(name);
        button = customizeButton(button);
        area.add(button);

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
}
