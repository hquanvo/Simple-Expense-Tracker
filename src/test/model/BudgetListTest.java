package model;

import exceptions.NegativeAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Category.*;
import static org.junit.jupiter.api.Assertions.*;

public class BudgetListTest {
    private BudgetList testList1;
    private BudgetList testList2;
    private Entry entry1;
    private Entry entry2;
    private Entry entry3;
    private Entry entry4;
    private Entry entry5;

    @BeforeEach
    void setup() throws NegativeAmountException {
        testList1 = new BudgetList("Eddie");
        testList2 = new BudgetList("September");
        entry1 = new Entry(20, "2022-09-19", "Others", "A figurine");
        entry2 = new Entry(400, "2022-09-12", "Bills", "Water, heat and wifi");
        entry3 = new Entry(40, "2022-09-14", "FOOD", "McDonald's");
        entry4 = new Entry(500, "2022-09-16", "SUPPLIES", "Drills and mallets");
        entry5 = new Entry(1000, "2022-09-01", "RENT", "");
        testList2.addEntry(entry1);
        testList2.addEntry(entry2);
        testList2.addEntry(entry3);
        testList2.addEntry(entry4);
    }


    @Test
    void testAddEntry() {
        assertEquals(0, testList1.getBudgetListSize());
        testList1.addEntry(entry1);
        assertEquals(1, testList1.getBudgetListSize());
        testContainsEntry(testList1, entry1);
        testList1.addEntry(entry2);
        testList1.addEntry(entry3);
        testList1.addEntry(entry4);
        assertEquals(4, testList1.getBudgetListSize());
        testContainsEntry(testList1, entry2);
        testContainsEntry(testList1, entry3);
        testContainsEntry(testList1, entry4);
        testList1.addEntry(entry5);
        testContainsEntry(testList1, entry5);
    }


    @Test
    void testAddMultipleSameEntry() {
        testList1.addEntry(entry1);
        testList1.addEntry(entry1);
        testList1.addEntry(entry1);
        assertEquals(3, testList1.getBudgetListSize());
        testEntryPos(entry1, testList1, 1);
        testEntryPos(entry1, testList1, 2);
        testEntryPos(entry1, testList1, 3);
        testList1.addEntry(entry5);
        testList1.addEntry(entry1);
        assertEquals(5, testList1.getBudgetListSize());
        testEntryPos(entry5, testList1, 4);
        testEntryPos(entry1, testList1, 5);
    }

    @Test
    void testRemoveEntry() {
        testList2.removeEntry(entry1);
        assertEquals(3, testList2.getBudgetListSize());
        testList2.removeEntry(entry3);
        assertEquals(2, testList2.getBudgetListSize());
        testEntryPos(entry2, testList2, 1);
        testEntryPos(entry4, testList2, 2);
    }

    @Test
    void testRename() {
        testList2.rename("October");
        assertEquals("October", testList2.getName());
        testList2.rename("");
        assertEquals("", testList2.getName());
    }

    @Test
    void testSumAllEntry() {
        assertEquals(0, testList1.sumAll());
        assertEquals(960, testList2.sumAll());
    }

    @Test
    void testSumCertainEntry() throws NegativeAmountException {
        assertEquals(500, testList2.sumCertainEntry(SUPPLIES));
        testList2.addEntry(entry4);
        assertEquals(1000, testList2.sumCertainEntry(SUPPLIES));
        testList2.addEntry(new Entry(12.34, "2022-09-12", "SUPPLIES", ""));
        assertEquals(1012.34, testList2.sumCertainEntry(SUPPLIES));
        assertEquals(0, testList2.sumCertainEntry(RENT));
    }

    @Test
    void testSummarize() {
        assertEquals(6, testList2.summarize().size());
        testExpectedAmountInCategory(0, 0);
        testExpectedAmountInCategory(40, 1);
        testExpectedAmountInCategory(500, 2);
        testExpectedAmountInCategory(400, 3);
        testExpectedAmountInCategory(20, 4);
        testExpectedAmountInCategory(960, 5);
    }

    @Test
    void testIsEmptyBudgetList() {
        assertTrue(testList1.isEmptyBudgetList());
        assertFalse(testList2.isEmptyBudgetList());
    }

    @Test
    void testEventLogAdd() {
        List<String> l = new ArrayList<>();
        EventLog el = EventLog.getInstance();
        el.clear();
        testLog();
        for (Event next : el) {
            String desc = next.getDescription();
            if (desc.equals("Added an entry to budget list September")) {
                l.add(desc);
            }
        }
        assertFalse(l.contains("Removed an entry from budget list September"));
        assertTrue(l.contains("Added an entry to budget list September"));
        assertFalse(l.contains("Generated a summary of the budget list September"));
        assertEquals(2, l.size());
    }



    @Test
    void testEventLogRemove() {
        List<String> l = new ArrayList<>();

        EventLog el = EventLog.getInstance();
        el.clear();
        testLog();
        for (Event next : el) {
            String desc = next.getDescription();
            if (desc.equals("Removed an entry from budget list September")) {
                l.add(desc);
            }
        }
        assertTrue(l.contains("Removed an entry from budget list September"));
        assertFalse(l.contains("Added an entry to budget list September"));
        assertFalse(l.contains("Generated a summary of the budget list September"));
        assertEquals(2, l.size());
    }

    @Test
    void testEventLogSummarize() {
        List<String> l = new ArrayList<>();

        EventLog el = EventLog.getInstance();
        el.clear();
        testLog();
        for (Event next : el) {
            String desc = next.getDescription();
            if (desc.equals("Generated a summary of the budget list September")) {
                l.add(desc);
            }
        }
        assertFalse(l.contains("Removed an entry from budget list September"));
        assertFalse(l.contains("Added an entry to budget list September"));
        assertTrue(l.contains("Generated a summary of the budget list September"));
        assertEquals(1, l.size());
    }


    //0 is RENT, 1 is FOOD, 2 is SUPPLIES, 3 is BILLS, 4 is OTHERS, 5 is TOTAL
    private void testExpectedAmountInCategory(int expectedAmount, int categoryIndex) {
        assertEquals(expectedAmount, testList2.summarize().get(categoryIndex));
    }

    private void testLog() {
        testList2.removeEntry(entry1);
        testList2.removeEntry(entry2);
        testList2.addEntry(entry1);
        testList2.addEntry(entry2);
        testList2.summarize();
    }

    private void testContainsEntry(BudgetList budgetList,Entry entry) {
        assertTrue(budgetList.getBudgetList().contains(entry));
    }

    private void testEntryPos(Entry entry, BudgetList budgetList, int entryNumber) {
        assertEquals(entry, budgetList.getEntryInBudgetList(entryNumber));
    }
}
