package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinatorTest {
    private Coordinator testCoordinator;
    private Student testStudent1;
    private Student testStudent2;
    private Student testStudent3;
    private Student testStudent4;

    @BeforeEach
    void runBefore() {
        testCoordinator = new Coordinator("Carter");
        testStudent1 = new Student("Jason", 12345678);

        testStudent2 = new Student("Paul", 87654321);
        testStudent3 = new Student("Justin", 66666666);
        testStudent4 = new Student("Justin", 12345678);
    }

    @Test
    void testConstructor() {
        assertEquals("Carter", testCoordinator.getName());
        List<Student> testResult = testCoordinator.getAllStudents();
        assertEquals(0, testResult.size());

    }

    @Test
    void testAddStudentByOnce() {
        assertTrue(testCoordinator.addStudent(testStudent1));
        List<Student> listOfStudent1 = testCoordinator.getAllStudents();
        assertEquals(1, listOfStudent1.size());
        assertEquals(testStudent1, listOfStudent1.get(0));

        assertFalse(testCoordinator.addStudent(testStudent4));
        List<Student> listOfStudent2 = testCoordinator.getAllStudents();
        assertEquals(1, listOfStudent2.size());
        assertEquals(testStudent1, listOfStudent2.get(0));

    }

    @Test
    void testAddStudentMultiTimes() {
        assertTrue(testCoordinator.addStudent(testStudent1));
        assertTrue(testCoordinator.addStudent(testStudent2));
        assertFalse(testCoordinator.addStudent(testStudent1));
        List<Student> listOfStudent = testCoordinator.getAllStudents();
        assertEquals(2, listOfStudent.size());
        assertEquals(testStudent1, listOfStudent.get(0));
        assertEquals(testStudent2, listOfStudent.get(1));

    }


    @Test
    void testRemoveStudentEmptyCase() {
        assertFalse(testCoordinator.removeStudent(12345678));

    }

    @Test
    void testRemoveStudentByOnce() {
        testCoordinator.addStudent(testStudent1);
        int testId1 = testStudent1.getId();
        assertTrue(testCoordinator.removeStudent(testId1));
        List<Student> listOfStudent1 = testCoordinator.getAllStudents();
        assertEquals(0, listOfStudent1.size());
        assertFalse(testCoordinator.removeStudent(testId1));

        testCoordinator.addStudent(testStudent2);
        assertFalse(testCoordinator.removeStudent(77777777));
        List<Student> listOfStudent2 = testCoordinator.getAllStudents();
        assertEquals(1, listOfStudent2.size());
        assertEquals(testStudent2, listOfStudent2.get(0));

    }


    @Test
    void testRemoveStudentByMultiTimes() {
        testCoordinator.addStudent(testStudent1);
        testCoordinator.addStudent(testStudent3);
        int testId1 = testStudent1.getId();
        int testId3 = testStudent3.getId();
        assertTrue(testCoordinator.removeStudent(testId1));
        assertTrue(testCoordinator.removeStudent(testId3));
        List<Student> listOfStudent1 = testCoordinator.getAllStudents();
        assertEquals(0, listOfStudent1.size());


        testCoordinator.addStudent(testStudent2);
        testCoordinator.addStudent(testStudent3);
        assertFalse(testCoordinator.removeStudent(77777777));
        assertFalse(testCoordinator.removeStudent(99999999));
        List<Student> listOfStudent2 = testCoordinator.getAllStudents();
        assertEquals(2, listOfStudent2.size());
        assertEquals(testStudent2, listOfStudent2.get(0));
        assertEquals(testStudent3, listOfStudent2.get(1));
    }


}