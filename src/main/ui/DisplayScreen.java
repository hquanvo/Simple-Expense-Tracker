package ui;

import java.util.ArrayList;

public abstract class DisplayScreen {
    protected ArrayList<Class> list;

    //Abstraction for the add function in a display screen
    protected abstract void add();

    //Abstraction for the remove function in a display screen;
    protected abstract void remove();
}
