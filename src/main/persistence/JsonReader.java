package persistence;


import exceptions.NegativeAmountException;
import model.BudgetList;
import model.Category;
import model.Entry;
import model.Tracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// CITATION: Modeled after JsonReader class in JsonSerializationDemo project provided by CPSC 210 instructors at UBC
// A reader that could read JSON files and load the saved state of the tracker
public class JsonReader {
    private String source;

    // EFFECTS: construct a reader to read the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: read the tracker and return it (loading the tracker)
    public Tracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTracker(jsonObject);
    }

    // EFFECTS: read the source file as a string and return it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parse Tracker from JSON object and return it
    private Tracker parseTracker(JSONObject jsonObject) {
        Tracker tracker = new Tracker();
        addBudgetLists(tracker, jsonObject);
        return tracker;
    }

    // MODIFIES: tracker
    // EFFECTS: parse budget lists from JSON object and adds them to the tracker
    private void addBudgetLists(Tracker tracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tracker");
        for (Object json : jsonArray) {
            JSONObject nextBudgetList = (JSONObject) json;
            addBudgetList(tracker, nextBudgetList);
        }
    }

    // MODIFIES: tracker, budgetlist
    // EFFECTS: parse budget list from JSON object and adds them to the tracker
    private void addBudgetList(Tracker tracker, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray jsonArray = jsonObject.getJSONArray("entries");
        BudgetList budgetList = new BudgetList(name);
        for (Object json : jsonArray) {
            JSONObject nextEntry = (JSONObject) json;
            addEntry(budgetList, nextEntry);
        }
        tracker.add(budgetList);
    }

    // MODIFIES: tracker, budgetList
    // EFFECTS: parse entry from JSON object and adds them to the budget list
    private void addEntry(BudgetList budgetList, JSONObject jsonObject) {
        Double amount = jsonObject.getDouble("amount");
        Category category = Category.valueOf(jsonObject.getString("category"));
        String date = jsonObject.getString("date");
        String description = jsonObject.getString("description");
        try {
            Entry entry = new Entry(amount, date, category, description);
            budgetList.add(entry);
        } catch (NegativeAmountException e) {
            e.printStackTrace();
        }
    }
}
