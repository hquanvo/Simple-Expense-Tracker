package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class EntryTest {
    private Entry entry1;
    private Entry entry2;
    @BeforeEach
    public void setup() {
        entry1 = new Entry();
        entry2 = new Entry();

    }

    @Test
    public void testConstructor() {
        assertEquals(0.0, entry1.getAmount());
        assertEquals(Category.DEFAULT, entry1.getCategory());
        assertEquals("2000-01-01", entry1.getDate());
        assertEquals("", entry1.getDescription());
    }

    @Test
    public void testSetAmount() {
        entry1.setAmount(400.49);
        assertEquals(400.49, entry1.getAmount());
        entry2.setAmount(20.03);
        assertEquals(20.03, entry2.getAmount());
    }

    @Test
    public void testSetCategory() {
        entry1.setCategory("Food");
        entry2.setCategory("");
        assertEquals(Category.FOOD, entry1.getCategory());
        assertEquals(Category.DEFAULT, entry2.getCategory());

        entry1.setCategory("");
        entry2.setCategory("Supplies");
        assertEquals(Category.FOOD, entry1.getCategory());
        assertEquals(Category.SUPPLIES, entry2.getCategory());
    }


    // Test using valid dates
    @Test
    public void testSetDate() {
        assertEquals("2000-01-01", entry1.getDate());
        entry1.setDate("2012-12-11");
        assertEquals("2012-12-11", entry1.getDate());
        entry1.setDate(2014);
        assertEquals("2014-12-11", entry1.getDate());
        entry1.setDate(2015, 10);
        assertEquals("2015-10-11", entry1.getDate());
        entry1.setDate(2020, 12,15);
        assertEquals("2020-12-15", entry1.getDate());
    }


    @Test
    public void testSetDescription() {
        entry1.setDescription("McDonald's");
        entry2.setDescription("Grocery store");
        assertEquals("McDonald's", entry1.getDescription());
        assertEquals("Grocery store", entry2.getDescription());
    }
}
