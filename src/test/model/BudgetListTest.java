package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void setup() {
        testList1 = new BudgetList("Eddie");
        testList2 = new BudgetList("September");
        entry1 = new Entry(20, "2022-09-19", OTHERS, "My anime figurine");
        entry2 = new Entry(400, "2022-09-12", BILLS, "Water, heat and wifi");
        entry3 = new Entry(40, "2022-09-14", FOOD, "My crippling McDonald's addiction");
        entry4 = new Entry(500, "2022-09-16", SUPPLIES, "Drills and mallets to smash deez");
        entry5 = new Entry(1000, "2022-09-01", RENT, "I hate Vancouver housing");
        testList2.add(entry1);
        testList2.add(entry2);
        testList2.add(entry3);
        testList2.add(entry4);
    }

    @Test
    void testAddEntry() {
        assertEquals(0, testList1.getBudgetListSize());
        testList1.getBudgetList().add(entry1);
        assertEquals(1, testList1.getBudgetListSize());
        testContainsEntry(testList1, entry1);
        testList1.getBudgetList().add(entry2);
        testList1.getBudgetList().add(entry3);
        testList1.getBudgetList().add(entry4);
        assertEquals(4, testList1.getBudgetListSize());
        testContainsEntry(testList1, entry2);
        testContainsEntry(testList1, entry3);
        testContainsEntry(testList1, entry4);
        testList1.getBudgetList().add(entry5);
        testContainsEntry(testList1, entry5);
    }


    @Test
    void testAddMultipleSameEntry() {
        testList1.add(entry1);
        testList1.add(entry1);
        testList1.add(entry1);
        assertEquals(3, testList1.getBudgetListSize());
        testEntryPos(entry1, testList1, 1);
        testEntryPos(entry1, testList1, 2);
        testEntryPos(entry1, testList1, 3);
        testList1.add(entry5);
        testList1.add(entry1);
        assertEquals(5, testList1.getBudgetListSize());
        testEntryPos(entry5, testList1, 4);
        testEntryPos(entry1, testList1, 5);
    }

    @Test
    void testRemoveEntry() {
        testList2.remove(entry1);
        assertEquals(3, testList2.getBudgetListSize());
        testList2.remove(entry3);
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
    void testSumCertainEntry() {
        assertEquals(500, testList2.sumCertainEntry(SUPPLIES));
        testList2.add(entry4);
        assertEquals(1000, testList2.sumCertainEntry(SUPPLIES));
        testList2.add(new Entry(12.34, "2022-09-12", SUPPLIES, ""));
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
    }

    //0 is RENT, 1 is FOOD, 2 is SUPPLIES, 3 is BILLS, 4 is OTHERS, 5 is TOTAL
    private void testExpectedAmountInCategory(int expectedAmount, int categoryIndex) {
        assertEquals(expectedAmount, testList2.summarize().get(categoryIndex));
    }

    private void testContainsEntry(BudgetList budgetList,Entry entry) {
        assertTrue(budgetList.getBudgetList().contains(entry));
    }

    private void testEntryPos(Entry entry, BudgetList budgetList, int entryNumber) {
        assertEquals(entry, budgetList.getEntryInBudgetList(entryNumber));
    }
}
