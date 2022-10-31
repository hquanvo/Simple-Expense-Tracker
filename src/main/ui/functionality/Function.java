package ui.functionality;

import javax.swing.*;

public abstract class Function {

    protected JButton button;

    public Function(String name) {
        button = new JButton(name);
    }
}
