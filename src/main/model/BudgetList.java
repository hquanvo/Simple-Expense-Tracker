package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;


// BudgetList contains information about the name of the budget list, and a budget list composed of multiple Entries.
public class BudgetList implements Writable {
    private String name;
    private final ArrayList<Entry> budgetList;


    // EFFECTS: Construct an empty budget list with a name and no entry in it
    public BudgetList(String name) {
        this.name = name;
        budgetList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add entry into list
    public void add(Entry entry) {
        budgetList.add(entry);
    }

    // MODIFIES: this
    // EFFECTS: remove entry from list
    public void remove(Entry entry) {
        budgetList.remove(entry);
    }

    // EFFECTS: Return the sum of all money spent in the budget list
    public double sumAll() {
        double sum = 0.0;
        for (Entry entry : budgetList) {
            sum += entry.getAmount();
        }
        return sum;
    }

    // EFFECTS: Return the sum of all money spent on a specific category in the budget list
    public double sumCertainEntry(Category ct) {
        double sum = 0.0;
        for (Entry entry : budgetList) {
            if (entry.getCategory().equals(ct)) {
                sum += entry.getAmount();
            }
        }
        return sum;
    }

    // EFFECTS: Produce an ArrayList containing the money spent in each category in this order: RENT, FOOD, SUPPLIES,
    //         BILLS, OTHERS, and the total amount of money
    public ArrayList<Double> summarize() {
        ArrayList<Double> summaryList = new ArrayList<>();
        for (Category ct : Category.values()) {
            summaryList.add(sumCertainEntry(ct));
        }
        summaryList.add(sumAll());
        return summaryList;
    }

    // MODIFIES: this
    // EFFECTS: Rename the budget list
    public void rename(String newName) {
        name = newName;
    }

    // EFFECTS: Return true if the budget list is empty
    public boolean isEmptyBudgetList() {
        return getBudgetListSize() == 0;
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

    // REQUIRES: entryNumber > 0
    public Entry getEntryInBudgetList(int entryNumber) {
        return budgetList.get(entryNumber - 1);
    }

    // CITATION: toJson and entriesToJson are based on the code found in JsonSerealizationDemo project provided by
    //           CPSC 210 instructors at UBC

    // EFFECTS: Write information about a budget list as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("entries", entriesToJSon());
        return json;
    }

    // EFFECTS: Write all entries in a budget list to be a JSonArray
    private JSONArray entriesToJSon() {
        JSONArray jsonArray = new JSONArray();

        for (Entry entry : budgetList) {
            jsonArray.put(entry.toJson());
        }
        return jsonArray;
    }
}
