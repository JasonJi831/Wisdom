package model;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    Course testCourse;


    @BeforeEach
    void runBefore() {
        testCourse = new Course("CPSC", 210);
    }


    @Test
    void testConstructor() {
        assertEquals("CPSC", testCourse.getSubject());
        assertEquals(210, testCourse.getCourseNumber());
        List<Integer> testSectionList = testCourse.getAllSections();
        assertEquals(0, testSectionList.size());
    }

    @Test
    void testSameCourse() {
        assertTrue(testCourse.sameCourse("CPSC", 210));
        assertFalse(testCourse.sameCourse("MATH", 210));
        assertFalse(testCourse.sameCourse("CPSC", 200));
        assertFalse(testCourse.sameCourse("MATH", 200));

    }


    @Test
    void testAddSectionByOnce() {
        testCourse.addSection(101);
        List<Integer> testSectionList = testCourse.getAllSections();
        assertEquals(1, testSectionList.size());
        assertEquals(101, testSectionList.get(0));

    }



    @Test
    void testAddSectionByMultiTimes() {
        testCourse.addSection(101);
        testCourse.addSection(102);
        List<Integer> testSectionList = testCourse.getAllSections();
        assertEquals(2, testSectionList.size());
        assertEquals(101, testSectionList.get(0));
        assertEquals(102, testSectionList.get(1));

    }

    @Test
    void testRemoveSectionEmptyCase() {
        assertFalse(testCourse.removeSection(101));

    }

    @Test
    void testRemoveSectionByOnce() {
        assertFalse(testCourse.removeSection(101));
        testCourse.addSection(101);
        assertFalse(testCourse.removeSection(102));
        testCourse.addSection(102);
        assertTrue(testCourse.removeSection(102));
        List<Integer> testSectionList = testCourse.getAllSections();
        assertEquals(1, testSectionList.size());
        assertEquals(101, testSectionList.get(0));





    }

    @Test
    void testRemoveSectionByOMultiTimes() {
        testCourse.addSection(101);
        testCourse.addSection(102);
        assertTrue(testCourse.removeSection(101));
        List<Integer> testSectionList1 = testCourse.getAllSections();
        assertEquals(1, testSectionList1.size());
        assertEquals(102, testSectionList1.get(0));

        assertTrue(testCourse.removeSection(102));
        List<Integer> testSectionList2 = testCourse.getAllSections();
        assertEquals(0, testSectionList2.size());


    }



}