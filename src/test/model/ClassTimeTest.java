package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ClassTimeTest {
    private ClassTime testClassTime1;
    private ClassTime testClassTime2;

    @BeforeEach
    void runBefore() {
        testClassTime1 = new ClassTime("Mon", 1100, 1300);
        testClassTime2 = new ClassTime("Tue", 1100, 1300);

    }

    @Test
    void testConstructor() {
        assertEquals("Mon", testClassTime1.getDay());
        assertEquals(1100, testClassTime1.getStartingTime());
        assertEquals(1300, testClassTime1.getEndingTime());

        assertEquals("Tue", testClassTime2.getDay());
        assertEquals(1100, testClassTime2.getStartingTime());
        assertEquals(1300, testClassTime2.getEndingTime());

    }


    @Test
    void testIfConflict() {
        assertFalse(testClassTime1.ifConflict(testClassTime2));  // test for different day --> false
        assertTrue(testClassTime1.ifConflict(testClassTime1));   // test for a same day and a same time --> true
        testClassTime2.setDay("Mon");
        testClassTime2.setTime(1300, 1500);
        assertFalse(testClassTime1.ifConflict(testClassTime2));  // test for two consecutive courses --> false
        testClassTime2.setTime(1301, 1500);
        assertFalse(testClassTime1.ifConflict(testClassTime2));  // test for two discrete courses --> false
        testClassTime2.setTime(1259, 1559);
        assertTrue(testClassTime1.ifConflict(testClassTime2));   // test for overlapping only one minute --> true
        testClassTime2.setTime(1130, 1300);
        assertTrue(testClassTime1.ifConflict(testClassTime2));   // test for overlapping 30 minutes --> true



    }


}