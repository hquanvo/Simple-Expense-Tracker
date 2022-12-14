package persistence;

import model.BudgetList;
import model.Entry;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkBudgetList(BudgetList budgetList, String name, ArrayList<Entry> entries) {
        assertEquals(name, budgetList.getName());
        assertEquals(budgetList.getBudgetListSize(), entries.size());
    }

    protected void checkEntry(Entry actualEntry, Entry expectedEntry) {
        assertEquals(expectedEntry.getAmount(), actualEntry.getAmount());
        assertEquals(expectedEntry.getCategory(), actualEntry.getCategory());
        assertEquals(expectedEntry.getDate(), actualEntry.getDate());
        assertEquals(expectedEntry.getDescription(), actualEntry.getDescription());
    }
}
