package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;



public class WorkListTest {

    private WorkList testWorkList;
    private Course testCourse1;
    private Course testCourse2;
    private Course testCourse3;


    @BeforeEach
    void runBefore() {
        testWorkList = new WorkList("myWorkList1");
        testCourse1 = new Course("CPSC",210, "BSc");
        testCourse2 = new Course("CPSC",110, "BSc");
        testCourse3 = new Course("ECON",101, "BA");


    }

    @Test
    void testConstructor() {
        assertEquals("myWorkList1", testWorkList.getName());
        List<Course> listOfCourse = testWorkList.getAllCourses();
        assertEquals(0, listOfCourse.size());
    }


    @Test
    void testAddCourseByOnce() {
        assertTrue(testWorkList.addCourse(testCourse1));
        List<Course> listOfCourse1 = testWorkList.getAllCourses();
        assertEquals(1, listOfCourse1.size());
        assertEquals(testCourse1, listOfCourse1.get(0));

        assertFalse(testWorkList.addCourse(testCourse1));
        List<Course> listOfCourse2 = testWorkList.getAllCourses();
        assertEquals(1, listOfCourse2.size());
        assertEquals(testCourse1, listOfCourse2.get(0));
    }


    @Test
    void testAddCourseByMultiTimes() {
        testWorkList.addCourse(testCourse1);
        testWorkList.addCourse(testCourse3);
        assertFalse(testWorkList.addCourse(testCourse1));
        assertFalse(testWorkList.addCourse(testCourse3));
        List<Course> listOfCourse = testWorkList.getAllCourses();
        assertEquals(2, listOfCourse.size());
        assertEquals(testCourse1, listOfCourse.get(0));
        assertEquals(testCourse3, listOfCourse.get(1));

    }




}
