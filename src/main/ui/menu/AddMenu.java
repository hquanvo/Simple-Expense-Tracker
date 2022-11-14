package ui.menu;

import exceptions.NegativeAmountException;
import model.BudgetList;
import model.Category;
import model.Entry;
import model.Tracker;

import javax.swing.*;
import java.util.Vector;

// Represents an add menu that can add an entry or add a budget list
public class AddMenu {

    // MODIFIES: menu
    // EFFECTS: Creates prompt for an add selection
    public AddMenu(MainMenu menu) {
        Object[] options = {"Add a new entry", "Add a new budget list", "Cancel"};
        int choice = JOptionPane.showOptionDialog(menu, "Which of the following would you like to add?",
                "Add", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
        switch (choice) {
            case (0):
                if (menu.getTracker().getTrackerSize() == 0) {
                    JOptionPane.showMessageDialog(menu,
                            "There is no budget list to add to, please make one first and try again.");
                } else {
                    addEntryMenu(menu);
                }
                break;

            case (1):
                addBudgetListMenu(menu);
                break;
            case (2):
                break;
        }
    }

    // MODIFIES: menu
    // EFFECTS: Creates the add entry prompt that adds an entry into the current budget list
    private void addEntryMenu(MainMenu menu) {
        try {
            String date = enterDatePrompt(menu);
            double amt = enterAmountPrompt(menu);
            String category = enterCategoryPrompt(menu);
            String desc = enterDescriptionPrompt(menu);

            Entry entry = makeEntry(amt, category, date, desc);
            updateEntryAdditionToTracker(menu, entry);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(menu,
                    "Amount is not a number!", "Error", JOptionPane.WARNING_MESSAGE);
        } catch (NegativeAmountException e) {
            JOptionPane.showMessageDialog(menu, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        } catch (NullPointerException e) {
            menu.showAbortMessage();
        }
    }


    // EFFECTS: Make an entry, then return it
    private static Entry makeEntry(double amt, String category, String date, String description)
            throws NegativeAmountException {
        Entry entry = new Entry(amt, date, Category.OTHERS, description);
        entry.setCategory(category);
        return entry;
    }

    // EFFECTS: Create a date input dialog that user can input the date in
    private String enterDatePrompt(MainMenu menu) {
        String date = JOptionPane.showInputDialog(menu,
                "Please enter the date of the entry (MUST BE IN YYYY-MM-DD FORMAT, "
                        + "ANY INCORRECT FORMAT WILL DEFAULT TO 2000-01-01):", "2000-01-01");
        if (date != null) {
            return date;
        } else {
            return "2000-01-01";
        }
    }

    // EFFECTS: Create an amount input dialog that the user can input the amount in, throws NegativeAmountException if
    //          the input is negative, throws NumberFormatException if the input isn't a number
    private double enterAmountPrompt(MainMenu menu) throws NegativeAmountException {
        double amt;
        String input = JOptionPane.showInputDialog(menu, "Please enter the amount spent on this entry:");
        amt = Double.parseDouble(input);
        if (amt < 0) {
            throw new NegativeAmountException();
        }
        return amt;
    }

    // EFFECTS: Create a category input dialog that the user can select the category the entry is in
    private String enterCategoryPrompt(MainMenu menu) {
        Object[] choices = {"RENT", "FOOD", "SUPPLIES", "BILLS", "OTHERS"};
        return (String) JOptionPane.showInputDialog(menu,
                "Please enter the category that this entry belongs to:",
                "Input", JOptionPane.QUESTION_MESSAGE, null,
                choices, choices[4]);
    }

    // EFFECTS: Create a date input dialog that user can input the date in
    private String enterDescriptionPrompt(MainMenu menu) {
        return JOptionPane.showInputDialog(menu,
                "Please enter the description of the entry (if any):");
    }

    // REQUIRES: The current budget list must not be null
    // MODIFIES: menu
    // EFFECTS: Update the tracker and the menu to display the new entry onto the table
    private void updateEntryAdditionToTracker(MainMenu menu, Entry entry) {
        int position = 0;
        Tracker tracker = menu.getTracker();
        for (BudgetList budgetList : tracker.getBudgetLists()) {

            if (budgetList.getName().equals(menu.getCurrentList().getName())) {

                budgetList.add(entry);
                tracker.getBudgetLists().set(position, budgetList);

                Vector<String> row = new Vector<>();

                menu.addToTable(entry, row);
                menu.repaint();
            } else {
                position++;
            }
        }

    }

    // MODIFIES: menu
    // EFFECTS: Creates the add menu prompt that adds a menu into the current budget list
    private void addBudgetListMenu(MainMenu menu) {
        String name;
        Tracker tracker = menu.getTracker();
        name = JOptionPane.showInputDialog(menu,
                "Please enter the name of the new budget list:");
        if (name == null) {
            menu.showAbortMessage();
            return;
        }
        if (name.length() <= 0) {
            JOptionPane.showMessageDialog(menu,
                    "Name of the new budget list cannot be empty.", "Error", JOptionPane.WARNING_MESSAGE);
        } else {

            tracker.add(new BudgetList(name));
            if (tracker.getBudgetLists().size() == 1) {
                menu.loadBudgetList(name, tracker.getBudgetLists());
            }
        }
    }

}
