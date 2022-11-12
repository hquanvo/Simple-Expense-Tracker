package ui.menu;

import model.BudgetList;
import model.Tracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Represents a remove menu that can remove a entry or remove a budget list
public class RemoveMenu {

    // EFFECTS: Creates prompt for a remove selection
    public RemoveMenu(MainMenu menu) {
        Object[] options = {"Remove an existing entry", "Remove an existing budget list", "Cancel"};
        int choice = JOptionPane.showOptionDialog(menu, "Which of the following would you like to remove?",
                "Remove", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
        switch (choice) {
            case (0):
                if (menu.getTracker().getTrackerSize() == 0) {
                    JOptionPane.showMessageDialog(menu,
                            "There is no budget list to add to, please make one first.");
                } else {
                    removeEntry(menu);
                }
                break;

            case (1) :
                // removeBudgetList method
                break;
            case (2) :
                break;
        }
    }

    // MODIFIES: menu
    // EFFECTS: remove an entry from the menu
    private void removeEntry(MainMenu menu) {
        BudgetList currList = menu.getCurrentList();
        if (currList.isEmptyBudgetList()) {
            JOptionPane.showMessageDialog(menu,"There is nothing to remove.");
        } else {
            Integer[] options = new Integer[currList.getBudgetListSize()];
            int counter = 1;
            for (int i = 0; i < options.length; i++) {
                options[i] = counter;
                counter++;
            }
            Integer choice = (Integer) JOptionPane.showInputDialog(menu,
                    "Select the ID number of the entry that you would like to remove:",
                    "Remove", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            updateEntryRemovalToTracker(menu, choice - 1);

        }
    }

    // REQUIRES: The current budget list must not be null
    // MODIFIES: menu
    // EFFECTS: Update the tracker and the menu to display the updated table with entry removed
    private void updateEntryRemovalToTracker(MainMenu menu, int index) {
        int position = 0;
        Tracker tracker = menu.getTracker();
        for (BudgetList budgetList : tracker.getBudgetLists()) {

            if (budgetList.getName().equals(menu.getCurrentList().getName())) {

                budgetList.getBudgetList().remove(budgetList.getBudgetList().get(index));
                tracker.getBudgetLists().set(position, budgetList);

                DefaultTableModel model = (DefaultTableModel) menu.getTable().getModel();
                model.removeRow(index);
                menu.repaint();
            } else {
                position++;
            }
        }

    }
}
