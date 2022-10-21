package ui;

import model.BudgetList;
import model.Entry;
import model.Tracker;

import java.util.ArrayList;

// Contains most print methods so no one have to subject to 200 lines of print code in the tracker app
public class Print {

    // EFFECTS: Show the Welcome menu when opening the application
    protected void displayWelcome(Tracker tracker) {
        if (tracker.isEmptyTracker()) {
            System.out.println("Welcome to the Expense Tracker.");
        } else {
            System.out.println("Welcome back to the Expense Tracker.");
        }
        System.out.println("Would you like to view your budget lists?");
        System.out.println("Enter 'yes' to proceed to the Tracker Menu, 'no' to exit.");
    }

    // EFFECTS: Print the available options for the tracker menu
    protected static void printTrackerMenuOptions() {
        System.out.println("Please select from the following");
        System.out.println("\n 'add' to add a new budget list");
        System.out.println("\n 'remove' to delete a budget list");
        System.out.println("\n 'rename' to rename a budget list");
        System.out.println("\n 'view' to view a budget list");
        System.out.println("\n 'compare' to compare two budget lists");
        System.out.println("\n 'return' to return to the Welcome screen");

    }

    // EFFECTS: Print a report based on the result of comparison
    protected void printReport(ArrayList<Double> differenceList, BudgetList budgetList1, BudgetList budgetList2) {
        System.out.println("Report:");
        for (int i = 1; i <= 6; i++) {
            double difference = differenceList.get(i - 1);
            if (difference > 0) {
                printReportNonZero(i, difference, "more", budgetList1, budgetList2);
            } else if (difference < 0) {
                difference = Math.abs(difference);
                printReportNonZero(i, difference, "less", budgetList1, budgetList2);
            } else {
                printReportZero(i);
            }
        }
        System.out.println("End of report.");
        System.out.println("Enter anything to exit.");
    }

    // REQUIRES: comparison must be either "more" or "less"
    // EFFECTS: Print a report when the difference in a category between the two lists is nonzero
    private void printReportNonZero(int i, Double difference, String comparison, BudgetList list1, BudgetList list2) {
        switch (i) {
            case 1:
                System.out.println("Rent: " + reportLine(difference, comparison, list1, list2));
                break;
            case 2:
                System.out.println("Food: " + reportLine(difference, comparison, list1, list2));
                break;
            case 3:
                System.out.println("Supplies: " + reportLine(difference, comparison, list1, list2));
                break;
            case 4:
                System.out.println("Bills: " + reportLine(difference, comparison, list1, list2));
                break;
            case 5:
                System.out.println("Others: " + reportLine(difference, comparison, list1, list2));
                break;
            case 6:
                System.out.println("In total: " + reportLine(difference, comparison, list1, list2));
                break;
        }
    }

    // REQUIRES: comparison must be either "more" or "less"
    // EFFECTS: Print a line for the report when the difference in a category between the two list is nonzero
    private String reportLine(Double difference, String comparison, BudgetList list1, BudgetList list2) {
        String list1Name = list1.getName();
        String list2Name = list2.getName();

        return "You have spent " + difference + " " + comparison + " in " + list2Name + " compared to "
                + list1Name + ".";
    }

    // EFFECTS: Print a line for the report when the difference in a category between the two list is zero
    private void printReportZero(int i) {
        switch (i) {
            case 1:
                System.out.println("Rent: There's no difference between these two budget lists");
                break;
            case 2:
                System.out.println("Food: There's no difference between these two budget lists");
                break;
            case 3:
                System.out.println("Supplies: There's no difference between these two budget lists");
                break;
            case 4:
                System.out.println("Bills: There's no difference between these two budget lists");
                break;
            case 5:
                System.out.println("Others: There's no difference between these two budget lists");
                break;
            case 6:
                System.out.println("In general, there's no difference between these two budget lists in terms "
                        + "of amount spent");
                break;
        }
    }

    // EFFECTS: Print all entries in a budget list
    protected void printEntries(BudgetList budgetList) {
        System.out.println("Here are the entries:");
        int index = 1;
        for (Entry entry : budgetList.getBudgetList()) {
            System.out.println(index + ". " + entry.getAmount() + "\t" + entry.getCategory().toString() + "\t"
                    + entry.getDate() + "\t" + entry.getDescription());
            index++;
        }
    }

    // EFFECTS: Display options for the budget list menu
    protected static void displayBudgetListMenuOptions() {
        System.out.println("Please select from the following");
        System.out.println("\n 'add' to add a new entry");
        System.out.println("\n 'remove' to delete an entry");
        System.out.println("\n 'summarize' to get overall data about this budget list");
        System.out.println("\n 'edit' to edit an entry");
        System.out.println("\n 'return' to return to the Tracker Menu screen");
    }

    // EFFECTS: Print a summary line, corresponding to the category at i position in the summary list
    protected void printSummary(Double amt, int i) {
        System.out.println("In summary:");
        switch (i) {
            case 1:
                System.out.println("Rent: " + amt);
                break;
            case 2:
                System.out.println("Food: " + amt);
                break;
            case 3:
                System.out.println("Supplies: " + amt);
                break;
            case 4:
                System.out.println("Bills: " + amt);
                break;
            case 5:
                System.out.println("Others: " + amt);
                break;
            case 6:
                System.out.println("In total: you spent " + amt + " in this budget list.");
                break;
        }
    }

    // EFFECTS: Print the edit menu
    protected void printEditMenu() {
        System.out.println("Please select from the following to edit");
        System.out.println("\n 'amount' to change the amount of money spent");
        System.out.println("\n 'date' to change the date of the entry");
        System.out.println("\n 'category' to change the category this entry belongs to");
        System.out.println("\n 'description' to change the entry's description");
        System.out.println("\n 'return' to return to the Budget List menu screen");
    }

    // EFFECTS: Print an error message when the input is not an integer
    protected static void printNotANumberErrorMessage() {
        System.out.println("Input invalid, not a number. Please try again.");
    }

    protected static void printCategoryCreation() {
        System.out.println("Please enter one of the following category: 'Rent', 'Food', 'Supplies', "
                + "'Bills', 'Others' that the new entry belongs to:");
    }
}
