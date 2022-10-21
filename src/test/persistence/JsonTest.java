package persistence;

import model.BudgetList;
import model.Category;
import model.Entry;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkBudgetList(BudgetList budgetList, String name, ArrayList<Entry> entries) {
        assertEquals(name, budgetList.getName());
        assertEquals(budgetList.getBudgetListSize(), entries.size());
    }

    protected void checkEntry(Entry entry, Double amount, Category category, String date, String description) {
        assertEquals(amount, entry.getAmount());
        assertEquals(category, entry.getCategory());
        assertEquals(date, entry.getDate());
        assertEquals(description, entry.getDescription());
    }
}
