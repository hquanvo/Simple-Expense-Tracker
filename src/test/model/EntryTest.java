package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EntryTest {
    private Entry entry1;
    private Entry entry2;
    private Date defaultDate;
    @BeforeEach
    public void setup() {
        entry1 = new Entry();
        entry2 = new Entry();

    }

    @Test
    public void testConstructor(){
        assertEquals(0.0, entry1.getAmount());
        assertEquals(Category.DEFAULT, entry1.getCategory());
        //assertEquals(Date, entry1.getDate().toString());
        assertEquals("", entry1.getDescription());
    }
}
