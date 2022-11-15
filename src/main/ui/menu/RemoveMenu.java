package ui.menu;

import model.BudgetList;
import model.Tracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

// Represents a remove menu that can remove an entry or remove a budget list
public class RemoveMenu {

    // MODIFIES: menu
    // EFFECTS: Creates prompt for a remove selection
    public RemoveMenu(MainMenu menu) {
        if (menu.getTracker().getTrackerSize() == 0) {
            JOptionPane.showMessageDialog(menu,
                    "There is nothing to remove.");
            return;
        }
        Object[] options = {"Remove an existing entry", "Remove an existing budget list", "Cancel"};
        int choice = JOptionPane.showOptionDialog(menu, "Which of the following would you like to remove?",
                "Remove", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
        switch (choice) {
            case (0):
                if (menu.getCurrentList().isEmptyBudgetList()) {
                    JOptionPane.showMessageDialog(menu,
                            "There is no entry to remove in this budget list.");
                } else {
                    removeEntryMenu(menu);
                }
                break;

            case (1):
                removeBudgetListMenu(menu);
                break;
            case (2):
                break;
        }
    }

    // MODIFIES: menu
    // EFFECTS: Creates a remove entry prompt that remove an entry from the current budget list
    private void removeEntryMenu(MainMenu menu) {
        BudgetList currList = menu.getCurrentList();
        Integer[] options = new Integer[currList.getBudgetListSize()];
        int counter = 1;
        for (int i = 0; i < options.length; i++) {
            options[i] = counter;
            counter++;
        }
        Integer choice = (Integer) JOptionPane.showInputDialog(menu,
                "Select the ID number of the entry that you would like to remove:",
                "Remove", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == null) {
            menu.showAbortMessage();
            return;
        }
        updateEntryRemovalToTracker(menu, choice - 1);


    }

    // REQUIRES: The current budget list must not be null
    // MODIFIES: menu
    // EFFECTS: Update the tracker and the menu to display the updated table with entry removed
    private void updateEntryRemovalToTracker(MainMenu menu, int index) {
        int position = 0;
        Tracker tracker = menu.getTracker();
        int confirm = JOptionPane.showConfirmDialog(menu, "Are you sure you want to remove this entry?",
                "Confirm", JOptionPane.YES_NO_OPTION);
        ArrayList<BudgetList> budgetLists = tracker.getBudgetLists();
        for (BudgetList budgetList : budgetLists) {
            String currentListName = budgetList.getName();
            if (confirm == 0) {
                if (currentListName.equals(menu.getCurrentList().getName())) {
                    budgetList.getBudgetList().remove(budgetList.getBudgetList().get(index));
                    budgetLists.set(position, budgetList);

                    DefaultTableModel model = (DefaultTableModel) menu.getTable().getModel();
                    model.removeRow(index);
                    menu.loadBudgetList(currentListName, budgetLists);
                    menu.repaint();
                } else {
                    position++;
                }
            }
        }
    }

    // MODIFIES: menu
    // EFFECTS: Creates a remove budget list prompt that remove a budget list from the current budget list
    private void removeBudgetListMenu(MainMenu menu) {
        Tracker tracker = menu.getTracker();
        ArrayList<BudgetList> budgetLists = tracker.getBudgetLists();
        String[] options = new String[tracker.getTrackerSize()];

        for (int i = 0; i < options.length; i++) {
            options[i] = budgetLists.get(i).getName();
        }

        String choice = (String) JOptionPane.showInputDialog(menu, "Select the budget list that you would like "
                        + "to remove:", "Remove", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == null) {
            menu.showAbortMessage();
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(menu, "Are you sure you want to remove this budget list?",
                "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == 0) {
            budgetLists.removeIf(budgetList -> choice.equals(budgetList.getName()));
            if (menu.getCurrentList().getName().equals(choice)) {
                loadDifferentListPostRemoval(menu, budgetLists);

            }
        }
    }

    // MODIFIES: menu
    // EFFECTS: Load in the next budget list if the current displaying list was removed by the user
    private void loadDifferentListPostRemoval(MainMenu menu, ArrayList<BudgetList> budgetLists) {
        if (budgetLists.size() >= 1) {
            menu.loadBudgetList(budgetLists.get(0).getName(), budgetLists);
        } else {
            menu.getTextArea().setText("There is no budget list to show here. Press 'Add' to make a new one!");
        }
    }


}
