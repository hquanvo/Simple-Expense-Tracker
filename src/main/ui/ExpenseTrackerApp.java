package ui;

import model.BudgetList;
import model.Category;
import model.Entry;
import model.Tracker;

import java.util.ArrayList;
import java.util.Scanner;

//Expense Tracker Application
public class ExpenseTrackerApp {
    private Scanner input; //Track users' input
    private Tracker tracker; // Tracker

    //MODIFIES: this
    //EFFECTS: Initialize the tracker
    private void initialize() {
        input = new Scanner(System.in);
        tracker = new Tracker();
    }

    //The following methods are based on the TellerApp class in the TellerApp project provided by
    // UBC CPSC 210 instructor team.

    //EFFECTS: Run the app
    public ExpenseTrackerApp() {
        initialize();
        welcomeScreen();
    }

    //MODIFIES: this
    //EFFECTS: creates the welcome menu, read user inputs
    private void welcomeScreen() {
        boolean runProgram = true;
        String command;

        while (runProgram) {
            displayWelcome();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("no")) {
                runProgram = false;
            } else if (command.equals("yes")) {
                trackerMenu(tracker);
            } else {
                System.out.println("Invalid command, please try again.");
            }
        }
    }


    //EFFECTS: Show the Welcome screen when opening the application
    private void displayWelcome() {
        if (tracker.isEmptyTracker()) {
            System.out.println("Welcome to the Expense Tracker.");
        } else {
            System.out.println("Welcome back to the Expense Tracker.");
        }
        System.out.println("Would you like to view your budget lists?");
        System.out.println("Enter 'yes' to proceed to the Tracker Menu, 'no' to exit.");
    }

    //MODIFIES: this
    //EFFECTS: Run a tracker menu
    private void trackerMenu(Tracker tracker) {
        this.tracker = tracker;
        String command = "";

        while (!(command.equals("add") || command.equals("remove") || command.equals("view")
                || command.equals("compare") || command.equals("return"))) {
            if (tracker.isEmptyTracker()) {
                System.out.println("There are no budget lists to show.");
            } else {
                printBudgetLists();
            }
            displayTrackerMenuOptions();
            command = input.nextLine();
            command = command.toLowerCase();
            processTrackerMenuOptions(command);
        }
    }


    //EFFECTS: Display the list of budget lists
    private void printBudgetLists() {
        System.out.println("Here are the current budget lists:");
        int index = 1;
        for (BudgetList budgetList : tracker.getTracker()) {
            System.out.println(index + ". " + budgetList.getName());
            index++;
        }
    }

    //EFFECTS: Display options for the tracker menu
    private static void displayTrackerMenuOptions() {
        System.out.println("Please select from the following");
        System.out.println("\n 'add' to add a new budget list");
        System.out.println("\n 'remove' to delete a budget list");
        System.out.println("\n 'rename' to rename a budget list");
        System.out.println("\n 'view' to view a budget list");
        System.out.println("\n 'compare' to compare two budget lists");
        System.out.println("\n 'return' to return to the Welcome screen");

    }

    //MODIFIES: this
    //EFFECTS: Process the command for the tracker menu
    private void processTrackerMenuOptions(String command) {
        switch (command) {
            case "add":
                addBudgetList();
                break;
            case "remove":
                removeBudgetList();
                break;
            case "rename":
                renameBudgetList();
                break;
            case "view":
                viewBudgetList();
                break;
            case "compare":
                compareBudgetList();
                break;
            case "return":
                break;
            default:
                System.out.println("Invalid command, please try again.");
        }
    }

    //MODIFIES: this
    //EFFECTS: add a new budget list into the tracker
    private void addBudgetList() {
        String name;

        System.out.println("Please enter the name of the new budget sheet:");
        name = input.nextLine();
        BudgetList budgetList = new BudgetList(name);
        tracker.add(budgetList);

        //return to prev screen
        trackerMenu(tracker);
    }



    //MODIFIES: this
    //EFFECTS: remove a budget list from the tracker
    private void removeBudgetList() {
        if (tracker.isEmptyTracker()) {
            System.out.println("There is nothing to remove.");
        } else {
            printBudgetLists();
            System.out.println("Please enter the budget list position number that you would like to remove:");
            int index = input.nextInt();
            String newName = input.nextLine();
            if (invalidBudgetListPosition(index)) {
                System.out.println("Invalid position, please try again.");
            } else {
                tracker.remove(tracker.getTrackerBudgetList(index));
                System.out.println("Removed list at position number " + index);
            }
        }
        trackerMenu(tracker);
    }

    //MODIFIES: this
    //EFFECTS: rename a budget list from the tracker
    private void renameBudgetList() {
        if (tracker.isEmptyTracker()) {
            System.out.println("There is nothing to rename.");
            trackerMenu(tracker);
        } else {
            printBudgetLists();
            System.out.println("Please enter the budget list position number that you would like to rename:");
            int index = input.nextInt();
            String newName = input.nextLine();
            if (invalidBudgetListPosition(index)) {
                System.out.println("Invalid position, please try again.");
                trackerMenu(tracker);
            } else {
                System.out.println("Please enter the new name of the list:");
                newName = input.nextLine();
                tracker.getTrackerBudgetList(index).rename(newName);
                System.out.println("Renamed list at position number " + index);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: Compare two budget lists, print out a report
    private void compareBudgetList() {
        if (tracker.getTrackerSize() < 2) {
            System.out.println("Not enough budget lists for comparison, please make sure there are at least "
                    + "2 budget sheets before comparing.");
        } else {
            BudgetList budgetList1 = retrieveBudgetList("first");
            BudgetList budgetList2 = retrieveBudgetList("second");
            input.nextLine();
            if (budgetList1 == null || budgetList2 == null) {
                System.out.println("Budget lists are not valid, please try again.");
            } else {
                ArrayList<Double> report = tracker.compareList(budgetList1, budgetList2);
                boolean runLoop = true;
                while (runLoop) {
                    System.out.println("Report");
                    printReport(report, budgetList1, budgetList2);
                    System.out.println("End of report.");
                    System.out.println("Enter anything to exit.");
                    input.nextLine();
                    break;
                }
            }
        }
        trackerMenu(tracker);
    }

    //EFFECTS: Retrieve a budget list to be used in a report
    private BudgetList retrieveBudgetList(String order) {
        printBudgetLists();
        System.out.println("Select the " + order + " budget list by entering its position number.");
        int index = input.nextInt();
        if (invalidBudgetListPosition(index)) {
            System.out.println("Invalid position, please try again.");
            return null;
        } else {
            return tracker.getTrackerBudgetList(index);
        }
    }

    //EFFECTS: Return true if the index position is invalid
    private boolean invalidBudgetListPosition(int index) {
        return index > tracker.getTrackerSize() || index < 0;
    }

    //EFFECTS: print a report based on the result of comparison
    private void printReport(ArrayList<Double> differenceList, BudgetList budgetList1, BudgetList budgetList2) {
        int i;
        for (i = 1; i <= 6; i++) {
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

    }

    //REQUIRES: comparison must be either "more" or "less"
    //EFFECTS: Print a report when the difference between the two lists is not 0
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

    //REQUIRES: comparison must be either "more" or "less"
    //EFFECTS: Print a line for the report when the difference between the two list is nonzero
    private String reportLine(Double difference, String comparison, BudgetList list1, BudgetList list2) {
        String list1Name = list1.getName();
        String list2Name = list2.getName();

        return "You have spent " + difference + " " + comparison + " in " + list2Name + " compared to "
                + list1Name + ".";
    }

    //EFFECTS: Print out a report line when there's no difference between the two budget list when comparing amt spent
    //          in a category
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

    //MODIFIES: this
    //EFFECTS: View a budget list in more details
    private void viewBudgetList() {
        if (tracker.isEmptyTracker()) {
            System.out.println("There is nothing view.");
            trackerMenu(tracker);
        } else {
            printBudgetLists();
            System.out.println("Please enter the budget list position number that you would like to view in details");
            int index = input.nextInt();
            String command = input.nextLine();
            if (invalidBudgetListPosition(index)) {
                System.out.println("Invalid position, please try again.");
                viewBudgetList();
            } else {
                budgetListMenu(tracker.getTrackerBudgetList(index), index - 1);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: View a budget list with a menu
    private void budgetListMenu(BudgetList budgetList, int position) {
        String command = "";

        while (!(command.equals("add") || command.equals("remove") || command.equals("edit")
                || command.equals("summarize") || command.equals("return"))) {
            if (budgetList.isEmptyBudgetList()) {
                System.out.println("There are no entries to show.");
            } else {
                printEntries(budgetList);
            }
            displayBudgetListMenuOptions();
            command = input.nextLine();
            command = command.toLowerCase();
            processBudgetListMenuOptions(command, budgetList, position);

        }
    }

    //EFFECTS: Print all entries in a budget list
    private void printEntries(BudgetList budgetList) {
        System.out.println("Here are the entries:");
        int index = 1;
        for (Entry entry : budgetList.getBudgetList()) {
            System.out.println(index + ". " + entry.getAmount() + "\t" + entry.getCategory().toString() + "\t"
                    + entry.getDate() + "\t" + entry.getDescription());
            index++;
        }
    }

    //EFFECTS: Display options for the budget list menu
    private static void displayBudgetListMenuOptions() {
        System.out.println("Please select from the following");
        System.out.println("\n 'add' to add a new entry");
        System.out.println("\n 'remove' to delete an entry");
        System.out.println("\n 'summarize' to get overall data about this budget list");
        System.out.println("\n 'edit' to edit an entry");
        System.out.println("\n 'return' to return to the Tracker Menu screen");
    }

    //MODIFIES: this
    //EFFECTS: Process the command for the tracker menu
    private void processBudgetListMenuOptions(String command, BudgetList budgetList, int position) {
        switch (command) {
            case "add":
                addEntry(budgetList, position);
                break;
            case "remove":
                removeEntry(budgetList, position);
                break;
            case "edit":
                editEntryMenu(budgetList, position);
                break;
            case "summarize":
                summarizeBudgetList(budgetList, position);
                break;
            case "return":
                tracker.getTracker().set(position, budgetList);
                trackerMenu(tracker);
                break;
            default:
                System.out.println("Invalid command, please try again.");
        }
    }


    //MODIFIES: this, budgetList
    //EFFECTS: Add an entry into a budget list
    private void addEntry(BudgetList budgetList, int position) {
        double amt;
        String category;
        String date;
        String description;

        try {
            System.out.println("Please enter the amount spent into the new entry:");
            amt = input.nextDouble();
            input.nextLine();
            System.out.println("Please enter one of the following category: 'Rent', 'Food', 'Supplies', "
                    + "'Bills', 'Others' that the new entry belongs to:");
            category = input.nextLine();
            category = category.toLowerCase();
            System.out.println("Please enter the date of the new entry (must be in yyyy-mm-dd format):");
            date = input.nextLine();
            System.out.println("(Optional) Please enter a description about the new entry:");
            description = input.nextLine();

            Entry entry = new Entry(amt, date, Category.OTHERS, description);
            entry.setCategory(category);

            budgetList.getBudgetList().add(entry);

            //updating the tracker's budget list
            updateAndReturn(budgetList, position);

        } catch (Exception e) {
            System.out.println("Cannot perform. Please try again.");
        }

    }

    //MODIFIES: this, budgetList
    //EFFECTS: Remove an entry from a budget list
    private void removeEntry(BudgetList budgetList, int position) {
        if (budgetList.isEmptyBudgetList()) {
            System.out.println("There is nothing to remove.");
        } else {
            printEntries(budgetList);
            System.out.println("Please enter the entry position number that you would like to remove:");
            int index = input.nextInt();
            String command = input.nextLine();
            if (invalidEntryPosition(budgetList, index)) {
                System.out.println("There is no entry at that position number, please try again.");
            } else {
                budgetList.getBudgetList().remove(budgetList.getEntryInBudgetList(index));
                System.out.println("Removed entry at position number " + index);
            }
        }
        //updating the tracker's budget list
        updateAndReturn(budgetList, position);
    }

    //EFFECTS: return true if the entered index position for entry is invalid
    private static boolean invalidEntryPosition(BudgetList budgetList, int index) {
        return index > budgetList.getBudgetListSize() || index < 0;
    }

    //MODIFIES: this
    //EFFECTS: View a summary of the budget list in terms of amt spent on RENT, FOOD, SUPPLIES, BILLS, OTHERS, and
    //          the TOTAL amount;
    private void summarizeBudgetList(BudgetList budgetList, int position) {
        ArrayList<Double> summary = budgetList.summarize();
        boolean runLoop = true;
        while (runLoop) {
            for (int i = 1; i <= 6; i++) {
                Double amt = summary.get(i - 1);
                printSummary(amt, i);
            }
            System.out.println("Enter anything to exit.");
            input.nextLine();
            break;
        }
        updateAndReturn(budgetList, position);
    }

    //MODIFIES: this
    //EFFECTS: Update the tracker's list of budget list with a new instance of budget list, before returning to the
    //          budget list menu.
    private void updateAndReturn(BudgetList budgetList, int position) {
        tracker.getTracker().set(position, budgetList);
        budgetListMenu(budgetList, position);
    }

    //EFFECTS: Print a summary line, corresponding to the category at i position in the summary list
    private void printSummary(Double amt, int i) {
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

    //MODIFIES: this, budgetList
    //EFFECTS: Open the menu to edit an entry
    private void editEntryMenu(BudgetList budgetList, int position) {
        if (budgetList.isEmptyBudgetList()) {
            System.out.println("There is nothing to edit.");
        } else {
            printEntries(budgetList);
            System.out.println("Please enter the entry position number that you would like to edit:");
            int index = input.nextInt();
            String command = input.nextLine();
            if (invalidEntryPosition(budgetList, index)) {
                System.out.println("There is no entry at that position number, please try again.");
            } else {
                Entry entry = budgetList.getEntryInBudgetList(index);
                printEditMenu();
                command = input.nextLine();
                command = command.toLowerCase();
                processEditOption(command, entry, index - 1, budgetList);
            }
        }
        //updating the tracker's budget list
        updateAndReturn(budgetList, position);
    }


    //EFFECTS: Print the edit menu
    private void printEditMenu() {
        System.out.println("Please select from the following to edit");
        System.out.println("\n 'amount' to change the amount of money spent");
        System.out.println("\n 'date' to change the date of the entry");
        System.out.println("\n 'category' to change the category this entry belongs to");
        System.out.println("\n 'description' to change the entry's description");
        System.out.println("\n 'return' to return to the Budget List menu screen");
    }


    //MODIFIES: this
    //EFFECTS: Process the edit command for an entry at the index number in the budget list
    private void processEditOption(String command, Entry entry, int index, BudgetList budgetList) {
        switch (command) {
            case "amount":
                editEntryAmount(entry, index, budgetList);
                break;
            case "date":
                editEntryDate(entry, index, budgetList);
                break;
            case "category":
                editEntryCategory(entry, index, budgetList);
                break;
            case "description":
                editEntryDescription(entry, index, budgetList);
                break;
            case "return":
                updateEntry(entry, index, budgetList);
                System.out.println("Cancelled editing.");
                break;
            default:
                System.out.println("Invalid command, please try again.");
        }
    }

    //MODIFIES: this, entry, budgetlist
    //EFFECTS: Update the entry inside a budget list
    private void updateEntry(Entry entry, int index, BudgetList budgetList) {
        budgetList.getBudgetList().set(index, entry);
    }

    //MODIFIES: this, entry, budgetlist
    //EFFECTS: Change an entry amount
    private void editEntryAmount(Entry entry, int index, BudgetList budgetList) {
        System.out.println("Please enter the new amount of money:");
        double newAmount = input.nextDouble();
        entry.setAmount(newAmount);
        input.nextLine();
        updateEntry(entry, index, budgetList);
        System.out.println("Finished editing an entry at position number " + (index + 1));
    }

    private void editEntryDate(Entry entry, int index, BudgetList budgetList) {
        System.out.println("Please enter the new date (MUST BE IN yyyy-mm-dd FORMAT)");
        String newDate = input.nextLine();
        try {
            entry.setDate(newDate);
            updateEntry(entry, index, budgetList);
            System.out.println("Finished editing an entry at position number " + (index + 1));
        } catch (Exception e) {
            System.out.println("Cannot perform. Date format invalid.");
        }

    }

    private void editEntryCategory(Entry entry, int index, BudgetList budgetList) {
        System.out.println("Please enter one of the following category: 'Rent', 'Food', 'Supplies', "
                + "'Bills', 'Others':");
        String category = input.nextLine();
        category = category.toLowerCase();
        entry.setCategory(category);
        updateEntry(entry, index, budgetList);
        System.out.println("Finished editing an entry at position number " + (index + 1));
    }

    private void editEntryDescription(Entry entry, int index, BudgetList budgetList) {
        System.out.println("Please enter the new description:");
        String desc = input.nextLine();
        entry.setDescription(desc);
        updateEntry(entry, index, budgetList);
        System.out.println("Finished editing an entry at position number " + (index + 1));
    }


}

