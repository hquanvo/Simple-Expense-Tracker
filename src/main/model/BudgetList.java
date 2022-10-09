package model;

import java.util.ArrayList;

// BudgetList contains information about the name of the budget list, and a budget list composed of multiple Entries.
public class BudgetList extends Tracker {
    private String name;
    private ArrayList<Entry> budgetList;


    //EFFECTS: Construct an empty budget list with a name and no entry in it
    public BudgetList(String name) {
        this.name = name;
        budgetList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add entry into list, can be duplicates
    public void add(Entry entry) {
        budgetList.add(entry);
    }

    //MODIFIES: this
    //EFFECTS: remove entry from list. If there are multiple, remove the first matching entry.
    public void remove(Entry entry) {
        budgetList.remove(entry);
    }

    //EFFECTS: Return the sum of all money spent in the budget list
    public double sumAll() {
        double sum = 0.0;
        for (Entry entry : budgetList) {
            sum += entry.getAmount();
        }
        return sum;
    }

    //EFFECTS: Return the sum of all money spent on a specific category in the budget list
    public double sumCertainEntry(Category ct) {
        double sum = 0.0;
        for (Entry entry : budgetList) {
            if (entry.getCategory().equals(ct)) {
                sum += entry.getAmount();
            }
        }
        return sum;
    }

    //EFFECTS: Produce an ArrayList containing the money spent in each category in this order: RENT, FOOD, SUPPLIES,
    //         BILLS, OTHERS, and the total amount of money
    public ArrayList<Double> summarize() {
        ArrayList<Double> summaryList = new ArrayList<>();
        for (Category ct : Category.values()) {
            summaryList.add(sumCertainEntry(ct));
        }
        summaryList.add(sumAll());
        return summaryList;
    }

    //MODIFIES: this
    //EFFECTS: Rename the budget list
    public void rename(String newName) {
        name = newName;
    }

    //getters
    public String getName() {
        return name;
    }

    public ArrayList<Entry> getBudgetList() {
        return budgetList;
    }

    public int getBudgetListSize() {
        return budgetList.size();
    }

    //REQUIRES: entryNumber > 0
    public Entry getBudgetListEntry(int entryNumber) {
        return budgetList.get(entryNumber - 1);
    }
}
