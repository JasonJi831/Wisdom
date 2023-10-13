package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    Student testStudent;
    Course testCourse1;
    Course testCourse2;


    @BeforeEach
    void runBefore() {
        testStudent = new Student("Jason", 12345678);
        testCourse1 = new Course("CPSC", 210);
        testCourse2 = new Course("ECON", 345);

    }


    @Test
    void testConstructor() {
        assertEquals("Jason", testStudent.getName());
        assertEquals(12345678, testStudent.getId());
        List<Course> testWorkList = testStudent.getWorkList();
        List<Course> testRegisteredList = testStudent.getRegistereddList();
        assertEquals(0, testWorkList.size());
        assertEquals(0, testRegisteredList.size());
    }

    @Test
    void testAddACourseToWorkListByOnce() {
        testStudent.addACourseToWorkList("CPSC", 210);
        List<Course> testWorkList = testStudent.getWorkList();
        assertEquals(1, testWorkList.size());
        Course testCourse = testWorkList.get(0);
        assertEquals("CPSC", testCourse.getSubject());
        assertEquals(210, testCourse.getCourseNumber());


    }

    @Test
    void testAddACourseToWorkListByMultiTimes() {
        testStudent.addACourseToWorkList("CPSC", 210);
        testStudent.addACourseToWorkList("ECON", 345);
        List<Course> testWorkList = testStudent.getWorkList();
        Course testCourse1 = testWorkList.get(0);
        Course testCourse2 = testWorkList.get(1);
        assertEquals(2, testWorkList.size());
        assertEquals("CPSC", testCourse1.getSubject());
        assertEquals(210, testCourse1.getCourseNumber());
        assertEquals("ECON", testCourse2.getSubject());
        assertEquals(345, testCourse2.getCourseNumber());


    }

    @Test
    void testAddANewSectionToWorkListByOnce() {

        testStudent.addANewSectionToWorkList("CPSC", 210, 101);
        List<Course> testWorkList1 = testStudent.getWorkList();
        List<Integer> testSectionList1 = testWorkList1.get(0).getAllSections();
        assertEquals(0, testSectionList1.size());


        testStudent.addACourseToWorkList("CPSC", 210);
        testStudent.addANewSectionToWorkList("CPSC", 210, 101);
        List<Course> testWorkList = testStudent.getWorkList();
        List<Integer> testSectionList = testWorkList.get(0).getAllSections();
        assertEquals(1, testSectionList.size());
        assertEquals(101, testSectionList.get(0));









    }


    @Test
    void testAddANewSectionToWorkListByMultiTimes() {
        testStudent.addACourseToWorkList("CPSC", 210);
        testStudent.addANewSectionToWorkList("CPSC", 210, 101);
        testStudent.addANewSectionToWorkList("CPSC", 210, 102);
        List<Course> testWorkList = testStudent.getWorkList();
        List<Integer> testSectionList = testWorkList.get(0).getAllSections();
        assertEquals(2, testSectionList.size());
        assertEquals(101, testSectionList.get(0));
        assertEquals(102, testSectionList.get(1));

    }

    @Test
    void testRegisterCourseByOnce() {
        testStudent.registerCourse("CPSC", 210, 101);
        List<Course> testRegisteredList = testStudent.getRegistereddList();
        Course testCourse = testRegisteredList.get(0);
        assertEquals("CPSC", testCourse.getSubject());
        assertEquals(210, testCourse.getCourseNumber());
        List<Integer> testSectionList = testRegisteredList.get(0).getAllSections();
        assertEquals(1, testSectionList.size());
        assertEquals(101, testSectionList.get(0));


    }

    @Test
    void testRegisterCourseByMultiTimes() {
        testStudent.registerCourse("CPSC", 210, 101);
        testStudent.registerCourse("ECON", 345, 101);
        List<Course> testRegisteredList = testStudent.getRegistereddList();
        Course testCourse1 = testRegisteredList.get(0);
        Course testCourse2 = testRegisteredList.get(1);
        assertEquals("CPSC", testCourse1.getSubject());
        assertEquals(210, testCourse1.getCourseNumber());
        assertEquals("ECON", testCourse2.getSubject());
        assertEquals(345, testCourse2.getCourseNumber());
        List<Integer> testSectionList = testRegisteredList.get(0).getAllSections();
        assertEquals(1, testSectionList.size());
        assertEquals(101, testSectionList.get(0));
        List<Integer> testSectionList1 = testRegisteredList.get(1).getAllSections();
        assertEquals(1, testSectionList1.size());
        assertEquals(101, testSectionList1.get(0));
    }


    @Test
    void testShowALlCourseInWorkList() {
        testStudent.addACourseToWorkList("CPSC", 210);
        List<String> testResult = testStudent.showALlCourseInWorkList();
        assertEquals(1, testResult.size());
        assertEquals("CPSC210", testResult.get(0));

        testStudent.addACourseToWorkList("ECON", 345);
        List<String> testResult1 = testStudent.showALlCourseInWorkList();
        assertEquals(2, testResult1.size());
        assertEquals("CPSC210", testResult1.get(0));
        assertEquals("ECON345", testResult1.get(1));
    }


    @Test
    void testShowALlCourseInRegisteredList() {
        testStudent.registerCourse("CPSC", 210, 101);
        List<String> testResult = testStudent.showALlCourseInRegisteredList();
        assertEquals(1, testResult.size());
        assertEquals("CPSC210", testResult.get(0));

        testStudent.registerCourse("ECON", 345, 101);
        List<String> testResult1 = testStudent.showALlCourseInRegisteredList();
        assertEquals(2, testResult1.size());
        assertEquals("CPSC210", testResult1.get(0));
        assertEquals("ECON345", testResult1.get(1));
    }


    @Test
    void testShowAllSectionOfThisCourseInWorkList() {
        testStudent.addACourseToWorkList("CPSC", 210);
        List<Integer> allSections = testStudent.showAllSectionOfThisCourseInWorkList("CPSC",
                210);
        assertEquals(0, allSections.size());

        testStudent.addANewSectionToWorkList("CPSC", 210, 101);
        List<Integer> allSection1 = testStudent.showAllSectionOfThisCourseInWorkList("CPSC",
                210);
        assertEquals(1, allSection1.size());
        assertEquals(101, allSection1.get(0));



    }


    @Test
    void testShowAllSectionOfThisCourseInRegisteredList() {
        testStudent.registerCourse("CPSC", 210, 101);
        List<Integer> allSections = testStudent.showAllSectionOfThisCourseInRegisteredList("CPSC",
                210);
        assertEquals(1, allSections.size());
        assertEquals(101, allSections.get(0));

    }

    @Test
    void testRepetitiveCourseInWorkList() {
        assertFalse(testStudent.repetitiveCourseInWorkList("CPSC", 210));
        testStudent.addACourseToWorkList("CPSC", 210);
        assertTrue(testStudent.repetitiveCourseInWorkList("CPSC", 210));

    }

    @Test
    void testRepetitiveCourseInRegisteredList() {
        assertFalse(testStudent.repetitiveCourseInRegisteredList("CPSC", 210));
        testStudent.registerCourse("CPSC", 210, 101);
        assertTrue(testStudent.repetitiveCourseInRegisteredList("CPSC", 210));


    }

    @Test
    void testDeleteOneCourseSectionByOnce() {
        assertFalse(testStudent.deleteOneCourseSection("CPSC", 210,101));
        testStudent.addACourseToWorkList("CPSC", 210);
        testStudent.addANewSectionToWorkList("CPSC", 210, 101);
        assertTrue(testStudent.deleteOneCourseSection("CPSC", 210, 101));
        List<Integer> allSection1 = testStudent.showAllSectionOfThisCourseInWorkList("CPSC",
                210);
        assertEquals(0, allSection1.size());



    }

    @Test
    void testDeleteOneCourseSectionByTwice() {
        assertFalse(testStudent.deleteOneCourseSection("CPSC", 210,101));
        testStudent.addACourseToWorkList("CPSC", 210);
        testStudent.addANewSectionToWorkList("CPSC", 210, 101);
        testStudent.addANewSectionToWorkList("CPSC", 210, 102);
        assertTrue(testStudent.deleteOneCourseSection("CPSC", 210, 101));
        List<Integer> allSection1 = testStudent.showAllSectionOfThisCourseInWorkList("CPSC",
                210);
        assertEquals(1, allSection1.size());
        assertEquals(102, allSection1.get(0));

        assertTrue(testStudent.deleteOneCourseSection("CPSC", 210, 102));
        List<Integer> allSection2 = testStudent.showAllSectionOfThisCourseInWorkList("CPSC",
                210);
        assertEquals(0, allSection2.size());


    }

    @Test
    void testDropOneCourseSectionByOnce() {
        assertFalse(testStudent.dropOneCourseSection("CPSC", 210,101));
        testStudent.registerCourse("CPSC", 210 ,101);
        assertTrue(testStudent.dropOneCourseSection("CPSC", 210, 101));
        List<Integer> allSection1 = testStudent.showAllSectionOfThisCourseInRegisteredList("CPSC",
                210);
        assertEquals(0, allSection1.size());

    }






}