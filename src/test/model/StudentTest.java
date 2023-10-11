package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    Student testStudent;
    Course testCourse1;
    Course testCourse2;
    WorkList myWorkList1;
    WorkList myWorkList2;
    Section section1;
    Section section2;


    @BeforeEach
    void runBefore() {
        testStudent = new Student("Jason", 12345678);
        testCourse1 = new Course("CPSC", 210, "BSc");
        testCourse2 = new Course("ECON", 345, "BA");
        myWorkList1 = new WorkList("myWorkList1");
        myWorkList2 = new WorkList("myWorkList2");
        section1 = new Section(201);
        section2 = new Section(203);

    }


    @Test
    void testConstructor() {
        assertEquals("Jason", testStudent.getName());
        assertEquals(12345678, testStudent.getId());
        List<WorkList> testWorkList = testStudent.getWorkList();
        List<Course> testRegistedList = testStudent.getRegistereddList();
        List<Course> testWaitList = testStudent.getWaitList();
        assertEquals(0, testWorkList.size());
        assertEquals(0, testRegistedList.size());
        assertEquals(0, testWaitList.size());

    }


    @Test
    void testAddCourseToWorkListByOnce() {
        assertTrue(testStudent.addCourseToWorkList(myWorkList1));
        assertFalse(testStudent.addCourseToWorkList(myWorkList1));
        List<WorkList> testWorkList = testStudent.getWorkList();
        assertEquals(1, testWorkList.size());
        assertEquals(myWorkList1, testWorkList.get(0));

    }


    @Test
    void testAddCourseToWorkListByMultiTimes() {
        assertTrue(testStudent.addCourseToWorkList(myWorkList1));
        assertTrue(testStudent.addCourseToWorkList(myWorkList2));
        List<WorkList> testWorkList = testStudent.getWorkList();
        assertEquals(2, testWorkList.size());
        assertEquals(myWorkList1, testWorkList.get(0));
        assertEquals(myWorkList2, testWorkList.get(1));

    }

    @Test
    void testRegisterCourseByOnce() {
        assertTrue(testStudent.registerCourse(testCourse1));
        assertFalse(testStudent.registerCourse(testCourse1));
        List<Course> testRegisteredList = testStudent.getRegistereddList();
        assertEquals(1, testRegisteredList.size());
        assertEquals(testCourse1, testRegisteredList.get(0));


    }


    @Test
    void testRegisterCourseByMultiTimes() {
        assertTrue(testStudent.registerCourse(testCourse1));
        assertTrue(testStudent.registerCourse(testCourse2));
        List<Course> testRegisteredList = testStudent.getRegistereddList();
        assertEquals(2, testRegisteredList.size());
        assertEquals(testCourse1, testRegisteredList.get(0));
        assertEquals(testCourse2, testRegisteredList.get(1));

    }

    @Test
    void testAddCourseToWaitListByOnce() {
        assertTrue(testStudent.addCourseToWaitList(testCourse1));
        assertFalse(testStudent.addCourseToWaitList(testCourse1));
        List<Course> testWaitList = testStudent.getWaitList();
        assertEquals(1, testWaitList.size());
        assertEquals(testCourse1, testWaitList.get(0));

    }

    @Test
    void testAddCourseToWaitListMultiTimes() {
        assertTrue(testStudent.addCourseToWaitList(testCourse1));
        assertTrue(testStudent.addCourseToWaitList(testCourse2));
        List<Course> testWaitList = testStudent.getWaitList();
        assertEquals(2, testWaitList.size());
        assertEquals(testCourse1, testWaitList.get(0));
        assertEquals(testCourse2, testWaitList.get(1));

    }

    @Test
    void testDeleteOneWorkListEmptyCase() {
        assertFalse(testStudent.deleteOneWorkList(myWorkList1.getName()));

    }

    @Test
    void testDeleteOneWorkList() {
        testStudent.addCourseToWorkList(myWorkList1);
        assertTrue(testStudent.deleteOneWorkList(myWorkList1.getName()));
        List<WorkList> testWorkList1 = testStudent.getWorkList();
        assertEquals(0, testWorkList1.size());


        testStudent.addCourseToWorkList(myWorkList2);
        testStudent.addCourseToWorkList(myWorkList1);
        assertTrue(testStudent.deleteOneWorkList(myWorkList1.getName()));
        assertFalse(testStudent.deleteOneWorkList("abc"));

        List<WorkList> testWorkList2 = testStudent.getWorkList();
        assertEquals(1, testWorkList2.size());
        assertEquals(myWorkList2, testWorkList2.get(0));

    }

    @Test
    void testDropOneCourseSectionEmptyCase() {
        assertFalse(testStudent.dropOneCourseSection("CPSC", 210, 201));

    }

    @Test
    void testDropOneCourseSection() {

        testStudent.registerCourse(testCourse1);
        testCourse1.addSection(section1);
        assertFalse(testStudent.dropOneCourseSection("MATH",210,201));
        assertFalse(testStudent.dropOneCourseSection("CPSC",121,201));
        assertFalse(testStudent.dropOneCourseSection("CPSC",210,203));
        assertTrue(testStudent.dropOneCourseSection("CPSC", 210, 201));
        assertFalse(testStudent.dropOneCourseSection("CPSC", 210, 201));
        List<Course> testRegisteredList = testStudent.getRegistereddList();
        Course course = testRegisteredList.get(0);
        List<Section> testSectionList = course.getAllSections();
        assertEquals(0, testSectionList.size());


    }


    @Test
    void testRemoveCourseFromWaitListEmptyCase() {
        assertFalse(testStudent.removeCourseSectionFromWaitList("ECON", 345, 201));

    }

    @Test
    void testRemoveCourseFromWaitList() {
        testStudent.addCourseToWaitList(testCourse2);
        assertFalse(testStudent.removeCourseSectionFromWaitList("MATH",345,201));
        assertFalse(testStudent.removeCourseSectionFromWaitList("ECON",101,201));
        assertFalse(testStudent.removeCourseSectionFromWaitList("ECON",345,202));
        testCourse2.addSection(section1);
        testCourse2.addSection(section2);

        assertTrue(testStudent.removeCourseSectionFromWaitList("ECON", 345, 201));
        assertFalse(testStudent.removeCourseSectionFromWaitList("ECON", 345, 201));
        List<Course> testWaitList = testStudent.getWaitList();
        Course course1 = testWaitList.get(0);
        List<Section> testSectionList1 = course1.getAllSections();
        assertEquals(1, testSectionList1.size());
        assertEquals(section2, testSectionList1.get(0));

        assertTrue(testStudent.removeCourseSectionFromWaitList("ECON", 345, 203));
        Course course2 = testWaitList.get(0);
        List<Section> testSectionList2 = course2.getAllSections();
        assertEquals(0, testSectionList2.size());



    }


}