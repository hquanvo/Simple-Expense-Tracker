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
        testList2.addEntry(entry1);
        testList2.addEntry(entry2);
        testList2.addEntry(entry3);
    }

    @Test
    void testAddEntry() {
        assertEquals(0, testList1.getBudgetList().size());
        testList1.getBudgetList().add(entry1);
        assertEquals(1, testList1.getBudgetList().size());
        assertTrue(testList1.getBudgetList().contains(entry1));
        testList1.getBudgetList().add(entry2);
        testList1.getBudgetList().add(entry3);
        testList1.getBudgetList().add(entry4);
        assertEquals(4, testList1.getBudgetList().size());
        assertTrue(testList1.getBudgetList().contains(entry2));
        assertTrue(testList1.getBudgetList().contains(entry3));
        assertTrue(testList1.getBudgetList().contains(entry4));
        testList1.getBudgetList().add(entry5);
        assertTrue(testList1.getBudgetList().contains(entry5));
    }

    @Test
    void testAddMultipleSameEntry() {
        testList1.getBudgetList().add(entry1);
        testList1.getBudgetList().add(entry1);
        testList1.getBudgetList().add(entry1);
        assertEquals(3, testList1.getBudgetList().size());
        assertEquals(entry1, testList1.getBudgetList().get(0));
        assertEquals(entry1, testList1.getBudgetList().get(1));
        assertEquals(entry1, testList1.getBudgetList().get(2));
        testList1.getBudgetList().add(entry5);
        testList1.getBudgetList().add(entry1);
        assertEquals(5, testList1.getBudgetList().size());
        assertEquals(entry5, testList1.getBudgetList().get(3));
        assertEquals(entry1, testList1.getBudgetList().get(4));
    }

   // @Test
  //  void testRemoveEntry() {
//
  //  }
}
