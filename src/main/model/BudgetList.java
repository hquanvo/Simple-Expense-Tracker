package model;

import java.util.ArrayList;

// This class is for making 1 budget list
public class BudgetList {
    private ArrayList<Entry> budgetList;


    //EFFECTS: Construct an empty budget list with no entry in it
    public void budgetList() {
        BudgetList budgetList = new BudgetList();
    }

    //EFFECTS: add entry into list
    public void addEntry(Entry entry) {
        budgetList.add(entry);
    }

    //REQUIRES: entry is in the list
    //EFFECTS: remove entry from list
    public void removeEntry(Entry entry) {
        budgetList.remove(entry);
    }
}
