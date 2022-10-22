package ui;

import exceptions.NegativeAmountException;
import model.BudgetList;
import model.Category;
import model.Entry;
import model.Tracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// CITATION: Code related to saving and loading are modeled after the code found in JsonSerializationDemo project
//           provided by the CPSC 210 instructors at UBC
//Expense Tracker Application
public class ExpenseTrackerApp extends Print {
    private static final String FILE_LOCATION = "./data/tracker.json";
    private Scanner input; // Enter users' input
    private Tracker tracker; // Tracker
    private JsonReader jsonReader; // Reader for loading
    private JsonWriter jsonWriter; // Writer for saving

    // MODIFIES: this
    // EFFECTS: Initialize the tracker
    private void initialize() {
        input = new Scanner(System.in);
        tracker = new Tracker();
        jsonWriter = new JsonWriter(FILE_LOCATION);
        jsonReader = new JsonReader(FILE_LOCATION);
    }

    // CITATION: The following methods are based on the TellerApp class in the TellerApp project provided by
    // UBC CPSC 210 instructor team.

    // EFFECTS: Run the app
    public ExpenseTrackerApp() {
        initialize();
        welcomeMenu();
    }

    // MODIFIES: this
    // EFFECTS: Runs the welcome menu and processes the user input
    private void welcomeMenu() {
        boolean runProgram = true;
        String command;

        while (runProgram) {
            super.displayWelcome(tracker);
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                quitMenu();
                runProgram = false;
            } else if (command.equals("view")) {
                trackerMenu(tracker);
            } else if (command.equals("save")) {
                saveTracker();
            } else if (command.equals("load")) {
                loadTracker();
            } else {
                System.out.println("Invalid command, please try again.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Runs the quit menu
    private void quitMenu() {
        boolean quitMenu = true;
        String command;

        while (quitMenu) {
            System.out.println("Would you like to save your data? Enter 'save' to save or 'quit' to quit "
                    + "without saving.");
            command = input.nextLine();
            command = command.toLowerCase();
            if (command.equals("save")) {
                saveTracker();
                quitMenu = false;
            } else if (command.equals("quit")) {
                quitMenu = false;
            } else {
                System.out.println("Invalid command, please try again.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Runs the tracker menu and processes the user input
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
            printTrackerMenuOptions();
            command = input.nextLine();
            command = command.toLowerCase();
            processTrackerMenuOptions(command);
        }
    }

    // EFFECTS: Print the list of budget lists
    private void printBudgetLists() {
        System.out.println("Here are the current budget lists:");
        int index = 1;
        for (BudgetList budgetList : tracker.getTracker()) {
            System.out.println(index + ". " + budgetList.getName());
            index++;
        }
    }

    // MODIFIES: this
    // EFFECTS: Process the command for the tracker menu
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

    // MODIFIES: this
    // EFFECTS: Add a new budget list into the tracker
    private void addBudgetList() {
        String name;

        System.out.println("Please enter the name of the new budget sheet:");
        name = input.nextLine();
        BudgetList budgetList = new BudgetList(name);
        tracker.add(budgetList);

        //return to prev screen
        trackerMenu(tracker);
    }


    // MODIFIES: this
    // EFFECTS: Remove a budget list from the tracker
    private void removeBudgetList() {
        if (tracker.isEmptyTracker()) {
            System.out.println("There is nothing to remove.");
        } else {
            printBudgetLists();
            System.out.println("Please enter the budget list position number that you would like to remove:");
            try {
                int index = input.nextInt();
                input.nextLine();
                if (invalidBudgetListPosition(index)) {
                    System.out.println("Invalid position, please try again.");
                } else {
                    tracker.remove(tracker.getTrackerBudgetList(index));
                    System.out.println("Removed list at position number " + index);
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                printNotANumberErrorMessage();
            }
        }
        //return to prev screen
        trackerMenu(tracker);
    }

    // MODIFIES: this
    // EFFECTS: rename a budget list from the tracker
    private void renameBudgetList() {
        if (tracker.isEmptyTracker()) {
            System.out.println("There is nothing to rename.");
            trackerMenu(tracker);
        } else {
            printBudgetLists();
            System.out.println("Please enter the budget list position number that you would like to rename:");
            try {
                int index = input.nextInt();
                input.nextLine();
                if (invalidBudgetListPosition(index)) {
                    System.out.println("Invalid position, please try again.");
                    trackerMenu(tracker);
                } else {
                    System.out.println("Please enter the new name of the list:");
                    String newName = input.nextLine();
                    tracker.getTrackerBudgetList(index).rename(newName);
                    System.out.println("Renamed list at position number " + index);
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                printNotANumberErrorMessage();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Compare two budget lists, print out a report
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
                    super.printReport(report, budgetList1, budgetList2);
                    input.nextLine();
                    break;
                }
            }
        }
        trackerMenu(tracker);
    }

    // EFFECTS: Retrieve a budget list to be used in a report
    private BudgetList retrieveBudgetList(String order) {
        printBudgetLists();
        System.out.println("Select the " + order + " budget list by entering its position number.");
        try {
            int index = input.nextInt();
            if (invalidBudgetListPosition(index)) {
                System.out.println("Invalid position, please try again.");
                return null;
            } else {
                return tracker.getTrackerBudgetList(index);
            }
        } catch (InputMismatchException e) {
            input.nextLine();
            printNotANumberErrorMessage();
        }
        return null;
    }

    // EFFECTS: Return true if the index position is invalid
    private boolean invalidBudgetListPosition(int index) {
        return index > tracker.getTrackerSize() || index < 0;
    }

    // MODIFIES: this
    // EFFECTS: View a budget list in more details
    private void viewBudgetList() {
        if (tracker.isEmptyTracker()) {
            System.out.println("There is nothing to view.");
            trackerMenu(tracker);
        } else {
            printBudgetLists();
            System.out.println("Please enter the budget list position number that you would like to view in details");
            try {
                int index = input.nextInt();
                input.nextLine();
                if (invalidBudgetListPosition(index)) {
                    System.out.println("Invalid position, please try again.");
                    viewBudgetList();
                } else {
                    budgetListMenu(tracker.getTrackerBudgetList(index), index - 1);
                }
            } catch (InputMismatchException e) {
                printNotANumberErrorMessage();
                input.nextLine();
                trackerMenu(tracker);
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: Run the budget list menu, processes the user input
    private void budgetListMenu(BudgetList budgetList, int position) {
        String command = "";

        while (!(command.equals("add") || command.equals("remove") || command.equals("edit")
                || command.equals("summarize") || command.equals("return"))) {
            if (budgetList.isEmptyBudgetList()) {
                System.out.println("There are no entries to show.");
            } else {
                super.printEntries(budgetList);
            }
            displayBudgetListMenuOptions();
            command = input.nextLine();
            command = command.toLowerCase();
            processBudgetListMenuOptions(command, budgetList, position);

        }
    }

    // MODIFIES: this
    // EFFECTS: Process the command for the budget list menu
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


    // MODIFIES: this, budgetList
    // EFFECTS: Add an entry into a budget list
    private void addEntry(BudgetList budgetList, int position) {
        try {
            System.out.println("Please enter the amount spent into the new entry:");
            double amt = input.nextDouble();
            input.nextLine();
            printCategoryCreation();
            String category = input.nextLine();
            category = category.toLowerCase();
            System.out.println("Please enter the date of the new entry (must be in yyyy-mm-dd format):");
            String date = input.nextLine();
            System.out.println("(Optional) Please enter a description about the new entry:");
            String description = input.nextLine();

            budgetList.getBudgetList().add(makeEntry(amt, category, date, description));
        } catch (InputMismatchException e) {
            System.out.println("Cannot perform. Please try again.");
            input.nextLine();
        } catch (NegativeAmountException e) {
            System.out.println("Entry amount cannot be negative.");
        } finally {
            //updating the tracker's budget list
            updateAndReturn(budgetList, position);
        }



    }

    // EFFECTS: Make an entry, then return it
    private static Entry makeEntry(double amt, String category, String date, String description)
            throws NegativeAmountException {
        Entry entry = new Entry(amt, date, Category.OTHERS, description);
        entry.setCategory(category);
        return entry;
    }


    // MODIFIES: this, budgetList
    // EFFECTS: Remove an entry from a budget list
    private void removeEntry(BudgetList budgetList, int position) {
        if (budgetList.isEmptyBudgetList()) {
            System.out.println("There is nothing to remove.");
        } else {
            printEntries(budgetList);
            System.out.println("Please enter the entry position number that you would like to remove:");
            try {
                int index = input.nextInt();
                input.nextLine();
                if (invalidEntryPosition(budgetList, index)) {
                    System.out.println("There is no entry at that position number, please try again.");
                } else {
                    budgetList.getBudgetList().remove(budgetList.getEntryInBudgetList(index));
                    System.out.println("Removed entry at position number " + index);
                }
            } catch (InputMismatchException e) {
                printNotANumberErrorMessage();
                input.nextLine();
            }

        }
        //updating the tracker's budget list
        updateAndReturn(budgetList, position);
    }

    // EFFECTS: return true if the entered index position for entry is invalid
    private static boolean invalidEntryPosition(BudgetList budgetList, int index) {
        return index > budgetList.getBudgetListSize() || index < 0;
    }

    // MODIFIES: this
    // EFFECTS: View a summary of the budget list in terms of amount spent on RENT, FOOD, SUPPLIES, BILLS, OTHERS, and
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

    // MODIFIES: this
    // EFFECTS: Update the tracker's list of budget list before returning to the budget list menu.
    private void updateAndReturn(BudgetList budgetList, int position) {
        tracker.getTracker().set(position, budgetList);
        budgetListMenu(budgetList, position);
    }


    // MODIFIES: this, budgetList
    // EFFECTS: Open the edit entry menu
    private void editEntryMenu(BudgetList budgetList, int position) {
        if (budgetList.isEmptyBudgetList()) {
            System.out.println("There is nothing to edit.");
        } else {
            try {
                printEntries(budgetList);
                System.out.println("Please enter the entry position number that you would like to edit:");
                int index = input.nextInt();
                input.nextLine();
                if (invalidEntryPosition(budgetList, index)) {
                    System.out.println("There is no entry at that position number, please try again.");
                } else {
                    Entry entry = budgetList.getEntryInBudgetList(index);
                    printEditMenu();
                    String command = input.nextLine();
                    command = command.toLowerCase();
                    processEditOption(command, entry, index - 1, budgetList);
                }
            } catch (InputMismatchException e) {
                printNotANumberErrorMessage();
                input.nextLine();
            }
        }
        //updating the tracker's budget list
        updateAndReturn(budgetList, position);
    }


    // MODIFIES: this
    // EFFECTS: Process the edit entry command
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
                updateEntryToList(entry, index, budgetList);
                System.out.println("Cancelled editing.");
                break;
            default:
                System.out.println("Invalid command, please try again.");
        }
    }

    // MODIFIES: this, budgetlist
    // EFFECTS: Update the entry inside a budget list
    private void updateEntryToList(Entry entry, int index, BudgetList budgetList) {
        budgetList.getBudgetList().set(index, entry);
    }

    // MODIFIES: this, entry, budgetlist
    // EFFECTS: Change an entry amount
    private void editEntryAmount(Entry entry, int index, BudgetList budgetList) {
        System.out.println("Please enter the new amount of money:");
        try {
            double newAmount = input.nextDouble();
            entry.setAmount(newAmount);
            input.nextLine();
            System.out.println("Finished editing an entry at position number " + (index + 1));
        } catch (InputMismatchException e) {
            input.nextLine();
            printNotANumberErrorMessage();
        } catch (NegativeAmountException e) {
            System.out.println("Entry amount cannot be negative or zero.");
        } finally {
            updateEntryToList(entry, index, budgetList);
        }
    }

    // MODIFIES: this, entry, budgetlist
    // EFFECTS: Change an entry date
    private void editEntryDate(Entry entry, int index, BudgetList budgetList) {
        System.out.println("Please enter the new date (MUST BE IN yyyy-mm-dd FORMAT)");
        String newDate = input.nextLine();
        try {
            entry.setDate(newDate);
            System.out.println("Finished editing an entry at position number " + (index + 1));
        } catch (InputMismatchException e) {
            System.out.println("Cannot perform. Date format invalid.");
        } finally {
            updateEntryToList(entry, index, budgetList);
        }

    }

    // MODIFIES: this, entry, budgetlist
    // EFFECTS: Change an entry category
    private void editEntryCategory(Entry entry, int index, BudgetList budgetList) {
        System.out.println("Please enter one of the following category: 'Rent', 'Food', 'Supplies', "
                + "'Bills', 'Others':");
        String category = input.nextLine();
        category = category.toLowerCase();
        entry.setCategory(category);
        updateEntryToList(entry, index, budgetList);
        System.out.println("Finished editing an entry at position number " + (index + 1));
    }

    // MODIFIES: this, entry, budgetlist
    // EFFECTS: Change an entry description
    private void editEntryDescription(Entry entry, int index, BudgetList budgetList) {
        System.out.println("Please enter the new description:");
        String desc = input.nextLine();
        entry.setDescription(desc);
        updateEntryToList(entry, index, budgetList);
        System.out.println("Finished editing an entry at position number " + (index + 1));
    }

    // EFFECTS: Save the tracker to tracker.json
    private void saveTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(tracker);
            jsonWriter.close();
            System.out.println("All budget lists in the tracker has been successfully saved to " + FILE_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Saving failed, unable to write to " + FILE_LOCATION);

        }
    }

    // MODIFIES: this
    // EFFECTS: Loads the tracker from tracker.json
    private void loadTracker() {
        try {
            tracker = jsonReader.read();
            System.out.println("All budget lists successfully loaded from " + FILE_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to load budget lists from " + FILE_LOCATION);
        } catch (NegativeAmountException e) {
            System.out.println("Attempted to read an impossible file, unable to load.");
        }
    }
}

