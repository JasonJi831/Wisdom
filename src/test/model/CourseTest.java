package model;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    Course testCourse;
    ClassTime classTime1;
    ClassTime classTime2;
    Section section1;
    Section section2;


    @BeforeEach
    void runBefore() {
        testCourse = new Course("CPSC", 210, "BSc");
        classTime1 = new ClassTime("Mon", 1100, 1300);
        classTime2 = new ClassTime("Tue", 1500, 1700);
        section1 = new Section(101);
        section2 = new Section(102);


    }

    @Test
    void testConstructor() {
        assertEquals("CPSC", testCourse.getSubject());
        assertEquals(210, testCourse.getCourseNumber());
        assertEquals("BSc", testCourse.getFaculty());
        List<Section> testSectionList = testCourse.getAllSections();
        assertEquals(0, testSectionList.size());
    }



    @Test
    void testAddSectionByOnce() {
        assertTrue(testCourse.addSection(section1));
        assertFalse(testCourse.addSection(section1));
        List<Section> testSectionList = testCourse.getAllSections();
        assertEquals(1, testSectionList.size());
        assertEquals(section1, testSectionList.get(0));

    }

    @Test
    void testAddSectionByMultiTimes() {
        assertTrue(testCourse.addSection(section1));
        assertTrue(testCourse.addSection(section2));
        assertFalse(testCourse.addSection(section1));
        assertFalse(testCourse.addSection(section2));
        List<Section> testSectionList = testCourse.getAllSections();
        assertEquals(2, testSectionList.size());
        assertEquals(section1, testSectionList.get(0));
        assertEquals(section2, testSectionList.get(1));

    }


    @Test
    void testWhetherContainsSectionEmptyCase() {
        assertFalse(testCourse.whetherContainsSection(101));


    }


    @Test
    void testWhetherContainsSection() {
        testCourse.addSection(section1);
        assertTrue(testCourse.whetherContainsSection(101));
        assertFalse(testCourse.whetherContainsSection(102));
        testCourse.addSection(section2);
        assertTrue(testCourse.whetherContainsSection(102));

    }


    @Test
    void testRemoveSectionEmptyCase() {
        assertFalse(testCourse.removeSection(101));

    }


    @Test
    void testRemoveSectionByOnce() {
        testCourse.addSection(section1);
        assertFalse(testCourse.removeSection(102));
        assertTrue(testCourse.removeSection(101));
        List<Section> testSectionList = testCourse.getAllSections();
        assertEquals(0, testSectionList.size());


    }


    @Test
    void testRemoveSectionByMultiTimes() {
        testCourse.addSection(section1);
        testCourse.addSection(section2);
        assertTrue(testCourse.removeSection(101));
        List<Section> testSectionList1 = testCourse.getAllSections();
        assertEquals(1, testSectionList1.size());
        assertEquals(section2, testSectionList1.get(0));
        assertTrue(testCourse.removeSection(102));
        List<Section> testSectionList2 = testCourse.getAllSections();
        assertEquals(0, testSectionList2.size());


    }



}