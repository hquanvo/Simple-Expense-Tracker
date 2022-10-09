package model;

import java.util.ArrayList;

//The first thing the user will access. Contains multiple budget lists and represent the Expense Tracker App in general.
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
    //EFFECTS: Remove a budget list from tracker.
    public void remove(BudgetList budgetList) {
        tracker.remove(budgetList);
    }

    //REQUIRES: tracker size >= 2
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


    //getters

    //EFFECTS: return the size of the tracker
    public int getTrackerSize() {
        return tracker.size();
    }

    //REQUIRES: i > 0
    //EFFECTS: return the numbered budget list in the tracker
    public BudgetList getTrackerBudgetList(int i) {
        return tracker.get(i - 1);
    }
}
