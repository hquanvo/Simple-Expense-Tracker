package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//The first thing the user will access. Contains multiple budget lists and represent the Expense Tracker App in general.
public class Tracker {
    private ArrayList<BudgetList> tracker;


    // EFFECTS: Construct a tracker with 0 budget list
    public Tracker() {
        tracker = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Add a budget list into the tracker
    public void addBudgetList(BudgetList budgetList) {
        tracker.add(budgetList);
        EventLog.getInstance().logEvent(new Event("Added budget list " + budgetList.getName() + " to tracker"));
    }

    // MODIFIES: this
    // EFFECTS: Remove a budget list from tracker
    public void removeBudgetList(BudgetList budgetList) {
        tracker.remove(budgetList);
        EventLog.getInstance().logEvent(new Event("Removed budget list " + budgetList.getName() + " from tracker"));
    }

    // REQUIRES: tracker size >= 2
    // EFFECTS: Return an array list that shows the amount difference between list1 and list2, in order of RENT, FOOD
    //          SUPPLIES, BILLS, OTHERS and TOTAL
    public ArrayList<Double> compareList(BudgetList list1, BudgetList list2) {
        int i;
        ArrayList<Double> summary1 = list1.summarize();
        ArrayList<Double> summary2 = list2.summarize();
        ArrayList<Double> report = new ArrayList<>();
        for (i = 0; i < 6; i++) {
            report.add(summary2.get(i) - summary1.get(i));
        }
        EventLog.getInstance().logEvent(new Event("Generated a comparison of two budget lists"));
        return report;
    }

    // EFFECTS: Return true if the tracker is empty
    public boolean isEmptyTracker() {
        return getTrackerSize() == 0;
    }


    //getters

    public ArrayList<BudgetList> getBudgetLists() {
        return tracker;
    }

    // EFFECTS: return the size of the tracker
    public int getTrackerSize() {
        return tracker.size();
    }

    // REQUIRES: i > 0
    // EFFECTS: return the numbered budget list in the tracker
    public BudgetList getTrackerBudgetList(int i) {
        return tracker.get(i - 1);
    }


    // EFFECTS: Write information about a tracker as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tracker", budgetListToJson());

        return json;
    }

    // EFFECTS: Return all budget list in the tracker as a JSONArray
    private JSONArray budgetListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (BudgetList budgetList : tracker) {
            jsonArray.put(budgetList.toJson());
        }

        return jsonArray;
    }
}
