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

    // EFFECTS: Creates prompt for an add selection
    public AddMenu(MainMenu menu) {
        Object[] options = {"Add a new entry", "Add a new budget list", "Cancel"};
        int choice = JOptionPane.showOptionDialog(menu, "Which of the following would you like to add?",
                "Add", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
        switch (choice) {
            case (0):
                if (menu.getTracker().getTrackerSize() == 0) {
                    JOptionPane.showMessageDialog(menu,
                            "There is no budget list to add to, please make one first.");
                } else {
                    addEntry(menu);
                }
                break;

            case (1) :
                // AddBudgetList method
                break;
            case (2) :
                break;
        }
    }

    // MODIFIES: menu
    // EFFECTS: add an entry into the menu
    private void addEntry(MainMenu menu) {
        String date = enterDatePrompt(menu);
        double amt = 0.0;
        try {
            amt = enterAmountPrompt(menu);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(menu,
                    "Amount is not a number!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        } catch (NegativeAmountException e) {
            JOptionPane.showMessageDialog(menu, e.getMessage(), "Error",  JOptionPane.WARNING_MESSAGE);
            return;
        }

        String category = enterCategoryPrompt(menu);
        String desc = enterDescriptionPrompt(menu);


        try {
            Entry entry = makeEntry(amt, category, date, desc);
            updateEntryAdditionToTracker(menu, entry);
        } catch (NegativeAmountException e) {
            System.out.println("This shouldn't be happening.");
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

        return date;
    }

    // EFFECTS: Create an amount input dialog that the user can input the amount in, throws NegativeAmountException if
    //          the input is negative, throws NumberFormatException if the input isn't a number
    private double enterAmountPrompt(MainMenu menu) throws NegativeAmountException {
        double amt = 0.0;
        amt = Double.parseDouble(
                JOptionPane.showInputDialog(menu, "Please enter the amount spent on this entry:"));
        if (amt < 0) {
            throw new NegativeAmountException();
        }
        return amt;
    }

    // EFFECTS: Create a category input dialog that the user can select the category the entry is in
    private String enterCategoryPrompt(MainMenu menu) {
        Object[] choices = {"RENT", "FOOD", "SUPPLIES", "BILLS", "OTHERS" };
        return (String) JOptionPane.showInputDialog(menu,
                "Please enter the category that this entry belongs to:",
                "Input", JOptionPane.QUESTION_MESSAGE, null,
                choices, choices[4]);
    }

    // EFFECTS: Create a date input dialog that user can input the date in
    private String enterDescriptionPrompt(MainMenu menu) {
        String desc = JOptionPane.showInputDialog(menu,
                "Please enter the description of the entry (if any):");

        return desc;
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

}
