package persistence;

import exceptions.NegativeAmountException;
import model.BudgetList;
import model.Entry;
import model.Tracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static model.Category.OTHERS;
import static model.Category.RENT;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/\0illegal:and:fakefile.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTracker() {
        try {
            Tracker tracker = new Tracker();
            JsonWriter writer = new JsonWriter("./data/testEmptyTrackerWrite.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyTrackerWrite.json");

            tracker = reader.read();
            assertEquals(0, tracker.getTrackerSize());
        } catch (IOException e) {
            fail("No exception should've been thrown");
        }
    }

    @Test
    void testWriteGeneralTracker() {
        try {
            Tracker tracker = new Tracker();
            BudgetList testList1 = new BudgetList("testList1"); // without entries
            BudgetList testList2 = new BudgetList("testList2"); // with entries
            Entry testEntry1 = new Entry(1, "", OTHERS, ""); //entry with default date
            Entry testEntry2 = new Entry(2000, "2003-12-15", RENT, "Monthly rent"); //actual entry
            testList2.add(testEntry1);
            testList2.add(testEntry2);
            tracker.add(testList1);
            tracker.add(testList2);
            tracker.add(testList1);

            JsonWriter writer = new JsonWriter("./data/testGeneralTrackerWrite.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testGeneralTrackerWrite.json");
            tracker = reader.read();

            assertEquals(3, tracker.getTrackerSize());
            checkBudgetList(tracker.getTrackerBudgetList(1), "testList1", testList1.getBudgetList());
            checkBudgetList(tracker.getTrackerBudgetList(3), "testList1", testList1.getBudgetList());
            checkBudgetList(tracker.getTrackerBudgetList(2), "testList2", testList2.getBudgetList());

            assertTrue(tracker.getTrackerBudgetList(1).isEmptyBudgetList());
            assertTrue(tracker.getTrackerBudgetList(3).isEmptyBudgetList());

            checkEntry(tracker.getTrackerBudgetList(2).
                    getEntryInBudgetList(1), 1.0, OTHERS, "2000-01-01", "");
            checkEntry(tracker.getTrackerBudgetList(2).
                    getEntryInBudgetList(2),
                    2000.0, RENT, "2003-12-15", "Monthly rent");


        } catch (IOException e) {
            fail("No exception should've been thrown");
        } catch (NegativeAmountException e) {
            fail("Please recheck testEntries amount");
        }
    }
}
