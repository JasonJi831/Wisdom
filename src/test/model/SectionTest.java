package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SectionTest {
    Section testSection1;
    Section testSection2;



    @BeforeEach
    void runBefore() {
        testSection1 = new Section(101);
        testSection2 = new Section(201);



    }

    @Test
    void testConstructor() {
        assertEquals(101, testSection1.getSectionNumber());
        assertEquals(201, testSection2.getSectionNumber());
        assertEquals(0, testSection1.getTotalSeats());
        assertEquals(0, testSection2.getTotalSeats());
        assertEquals(0, testSection1.getRemainingSeats());
        assertEquals(0, testSection2.getRemainingSeats());

    }


    @Test
    void testRemainingSeatsAdjustBeOnce() {
        testSection1.setTotalSeats(100);
        testSection1.setSeatsRemaining();
        testSection1.remainingSeatsAdjust();
        assertEquals(99, testSection1.getRemainingSeats());
    }

    @Test
    void testRemainingSeatsAdjustBeMultiTimes() {
        testSection1.setTotalSeats(200);
        testSection1.setSeatsRemaining();
        testSection1.remainingSeatsAdjust();
        testSection1.remainingSeatsAdjust();
        assertEquals(198, testSection1.getRemainingSeats());
    }

    @Test
    void testIfSeatAvailable() {
        testSection2.setTotalSeats(3);
        testSection2.setSeatsRemaining();
        assertTrue(testSection2.ifSeatAvailable());
        testSection2.remainingSeatsAdjust();
        testSection2.remainingSeatsAdjust();
        assertTrue(testSection2.ifSeatAvailable());
        testSection2.remainingSeatsAdjust();
        assertFalse(testSection2.ifSeatAvailable());

    }


}