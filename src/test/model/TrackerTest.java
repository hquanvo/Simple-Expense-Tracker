package model;

import exceptions.NegativeAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static model.Category.*;
import static org.junit.jupiter.api.Assertions.*;

public class TrackerTest {
    private Tracker tracker;
    private BudgetList testList1;
    private Entry entry1;
    private Entry entry2;
    private Entry entry3;
    private Entry entry4;
    private Entry entry5;

    private BudgetList testList2;
    private Entry entry6;
    private Entry entry7;
    private Entry entry8;
    private Entry entry9;
    private Entry entry10;

    @BeforeEach
    void setup() throws NegativeAmountException {
        tracker = new Tracker();
        testList1 = new BudgetList("January");
        testList2 = new BudgetList("February");

        entry1 = new Entry(1000, "2022-01-01", RENT, "Rent");
        entry2 = new Entry(40, "2022-01-03", FOOD, "Grocery store");
        entry3 = new Entry(20, "2022-01-14", SUPPLIES, "Various tools");
        entry4 = new Entry(100, "2022-01-01", BILLS, "Water, heat and internet");
        entry5 = new Entry(1500, "2022-01-01", OTHERS, "PC funding");

        entry6 = new Entry(1100, "2022-02-01", RENT, "Rent increase");
        entry7 = new Entry(29.13, "2022-02-12", FOOD, "Grocery store");
        entry8 = new Entry(56.86, "2022-02-22", FOOD, "Grocery store");
        entry9 = new Entry(300, "2022-02-01", BILLS, "Water, heat and internet");
        entry10 = new Entry(31.212, "2022-02-03", OTHERS, "Gaming controller");

        testList1.add(entry1);
        testList1.add(entry2);
        testList1.add(entry3);
        testList1.add(entry4);
        testList1.add(entry5);

        testList2.add(entry6);
        testList2.add(entry7);
        testList2.add(entry8);
        testList2.add(entry9);
        testList2.add(entry10);

        tracker.add(testList1);
        tracker.add(testList2);
    }

    @Test
    void testCompareList() {
        ArrayList<Double> result = tracker.compareList(testList1, testList2);
        assertEquals(100, result.get(0));
        assertEquals(29.13 + 56.86 - 40, result.get(1));
        assertEquals(-20, result.get(2));
        assertEquals(200, result.get(3));
        assertEquals(-1468.788, result.get(4));
        assertEquals(testList2.sumAll()- testList1.sumAll(), result.get(5));

        result = tracker.compareList(testList2, testList1);
        assertEquals(-100, result.get(0));
        assertEquals(40 - (29.13 + 56.86), result.get(1));
        assertEquals(20, result.get(2));
        assertEquals(-200, result.get(3));
        assertEquals(1468.788, result.get(4));
        assertEquals(testList1.sumAll()- testList2.sumAll(), result.get(5));

        testList1.remove(entry2);
        testList2.remove(entry9);
        result = tracker.compareList(testList1,testList2);
        assertEquals(100, result.get(0));
        assertEquals(85.99, result.get(1));
        assertEquals(-20, result.get(2));
        assertEquals(-100, result.get(3));
        assertEquals(-1468.788, result.get(4));
        assertEquals(testList2.sumAll()- testList1.sumAll(), result.get(5));
    }

    @Test
    void testAddAndRemove() {
        ArrayList<BudgetList> otherList = new ArrayList<>();
        otherList.add(testList1);
        otherList.add(testList2);

        assertEquals(otherList, tracker.getBudgetLists());
        assertEquals(2, tracker.getTrackerSize());
        tracker.remove(testList1);
        assertEquals(1, tracker.getTrackerSize());
        assertEquals(testList2,tracker.getTrackerBudgetList(1));
        tracker.remove(testList2);
        tracker.remove(testList1);
        assertEquals(0, tracker.getTrackerSize());
        tracker.add(testList1);
        tracker.add(testList1);
        tracker.add(testList2);
        tracker.add(testList1);
        tracker.add(testList1);
        assertEquals(5, tracker.getTrackerSize());
        assertEquals(testList2,tracker.getTrackerBudgetList(3));
    }

    @Test
    void testEmptyTracker() {
        assertFalse(tracker.isEmptyTracker());
        tracker.remove(testList1);
        tracker.remove(testList2);
        assertTrue(tracker.isEmptyTracker());

    }

}
