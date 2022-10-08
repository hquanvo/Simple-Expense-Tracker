package model;

import java.util.ArrayList;

// BudgetList contains information about the name of the budget list, and a budget list composed of multiple Entries.
public class BudgetList {
    private String name;
    private ArrayList<Entry> budgetList;


    //EFFECTS: Construct an empty budget list with no entry in it
    public BudgetList(String name) {
        this.name = name;
        budgetList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add entry into list, can be duplicates
    public void addEntry(Entry entry) {
        budgetList.add(entry);
        System.out.println("Added entry into the budget list");
    }

    //MODIFIES: this
    //EFFECTS: remove entry from list. If there are multiple, remove the first matching entry.
    public void removeEntry(Entry entry) {
        if (budgetList.contains(entry)) {
            budgetList.remove(entry);
            System.out.println("Removed entry from the budget list.");
        }

    }

    //EFFECTS: Return the sum of all money spent in the budget list
    public double sumAllEntry() {
        double sum = 0.0;
        for (Entry entry : budgetList) {
            sum += entry.getAmount();
        }
        return sum;
    }

    //EFFECTS: Return the sum of all money spent on a specific category in the budget list
    public double sumCertainEntry(String ct) {
        double sum = 0.0;
        for (Entry entry : budgetList) {
            if (entry.getCategory().equals(ct)) {
                sum += entry.getAmount();
            }
        }
        return sum;
    }

    //getters
    public String getName() {
        return name;
    }

    public ArrayList<Entry> getBudgetList() {
        return budgetList;
    }
}
