package ui.menu;

import model.BudgetList;
import model.Entry;
import model.Tracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Vector;

// Represents a load menu that can load a different budget list
public class LoadMenu {

    // MODIFIES: menu
    // EFFECTS: Create a load menu that user can choose to load a different budget list
    public LoadMenu(MainMenu menu) {
        Tracker tracker = menu.getTracker();
        ArrayList<BudgetList> budgetLists = tracker.getBudgetLists();
        if (tracker.getTrackerSize() == 0) {
            JOptionPane.showMessageDialog(menu,
                    "There is nothing to load.");
        } else {
            String[] options = new String[tracker.getTrackerSize()];
            for (int i = 0; i < options.length; i++) {
                options[i] = budgetLists.get(i).getName();
            }
            String choice = (String) JOptionPane.showInputDialog(menu,
                    "Select the budget list that you would like to load:",
                    "Load", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (choice == null) {
                menu.showAbortMessage();
            } else {
                for (BudgetList budgetList : budgetLists) {
                    if (choice.equals(budgetList.getName())) {
                        menu.loadBudgetList(choice, budgetLists);
                    }
                }

            }
        }
    }


}
