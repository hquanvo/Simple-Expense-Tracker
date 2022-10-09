package model;

import java.util.ArrayList;

//The first thing the user will access. Contains multiple budget lists.
public class Tracker {
    private ArrayList<BudgetList> tracker;


    //EFFECTS: Construct a tracker with 0 budget list
    public Tracker() {
        tracker = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: Add a budget list into the tracker, can be duplicates
    public void add(BudgetList budgetList) {
        tracker.add(budgetList);
    }

    //MODIFIES: this
    //EFFECTS: Remove entry from list. If there are multiple, remove the first matching entry.
    public void remove(BudgetList budgetList) {
        tracker.remove(budgetList);
    }

    //EFFECTS: Return an array list that shows the amount difference between list1 and list2, in order of RENT, FOOD
    //         SUPPLIES, BILLS, OTHERS and TOTAL
    public ArrayList<Double> compareList(BudgetList list1, BudgetList list2) {
        int i;
        ArrayList<Double> summary1 = list1.summarize();
        ArrayList<Double> summary2 = list2.summarize();
        ArrayList<Double> report = new ArrayList<>();
        for (i = 0; i < 6; i++) {
            report.add(summary2.get(i) - summary1.get(i));
        }
        return report;
    }

}
