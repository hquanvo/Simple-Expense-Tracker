package model;

import java.util.ArrayList;

// This class is for making 1 budget list, with a name
public class BudgetList {
    private String name;
    private ArrayList<Entry> budgetList;


    //EFFECTS: Construct an empty budget list with no entry in it
    public void budgetList(String name) {
        this.name = name;
        BudgetList budgetList = new BudgetList();
    }

    //EFFECTS: add entry into list
    public void addEntry(Entry entry) {
        budgetList.add(entry);
    }

    //EFFECTS: remove entry from list
    public void removeEntry(Entry entry) {
        if (budgetList.contains(entry)) {
            budgetList.remove(entry);
            System.out.println("Removed entry from the budget list.");
        }

    }
}
