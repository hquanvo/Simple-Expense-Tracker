package persistence;

import exceptions.NegativeAmountException;
import model.BudgetList;
import model.Entry;
import model.Tracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static model.Category.*;
import static org.junit.jupiter.api.Assertions.*;

// CITATION: Modeled after JsonReaderTest class in JsonSerializationDemo project provided by CPSC 210 instructors at UBC
public class JsonReaderTest extends JsonTest {

    @Test
    void testReadInvalidFile() {
        try {
            JsonReader reader = new JsonReader("./data/fileDoNotExist.json");
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        } catch (NegativeAmountException e) {
            fail("Wrong exception thrown");
        }
    }

    @Test
    void testReadEmptyTracker() {
        try {
            JsonReader reader = new JsonReader("./data/testEmptyTrackerRead.json");
            Tracker tracker = reader.read();

            assertEquals(0, tracker.getTrackerSize());

        } catch (IOException e) {
            fail("No exception should've been thrown");
        } catch (NegativeAmountException e) {
            fail("Also this exception shouldn't be thrown");
        }
    }

    @Test
    void testReadGeneralTracker() {
        try {
            JsonReader reader = new JsonReader("./data/testGeneralTrackerRead.json");
            Tracker tracker = reader.read();
            BudgetList expectedTestList1 = new BudgetList("testList1");
            BudgetList expectedTestList2 = new BudgetList("testList2");

            Entry expectedEntry1 = new Entry(1, "" ,OTHERS, "");
            Entry expectedEntry2 = new Entry(1300, "2022-12-01" , RENT, "Monthly rent");
            Entry expectedEntry3 = new Entry(12.34, "2022-12-18", FOOD, "Grocery");

            expectedTestList1.add(expectedEntry1);
            expectedTestList1.add(expectedEntry2);
            expectedTestList1.add(expectedEntry3);

            assertEquals(2, tracker.getTrackerSize());

            checkBudgetList(tracker.getTrackerBudgetList(1), "testList1", expectedTestList1.getBudgetList());
            checkBudgetList(tracker.getTrackerBudgetList(2), "testList2", expectedTestList2.getBudgetList());

            checkEntry(tracker.getTrackerBudgetList(1).getEntryInBudgetList(1),
                    expectedEntry1);
            checkEntry(tracker.getTrackerBudgetList(1).getEntryInBudgetList(2),
                    expectedEntry2);
            checkEntry(tracker.getTrackerBudgetList(1).getEntryInBudgetList(3),
                    expectedEntry3);

        } catch (IOException e) {
            fail("No exception should've been thrown");
        } catch (NegativeAmountException e) {
            fail("Error in making test, this shouldn't have been thrown");
        }
    }

    @Test
    void testReadFileWithImpossibleEntry() {
        try {
            JsonReader reader = new JsonReader("./data/testImpossibleTrackerRead.json");
            reader.read();
        } catch (NegativeAmountException e) {
            // pass
        } catch (IOException e) {
            fail("IOException shouldn't have been thrown");
        }
    }

}
