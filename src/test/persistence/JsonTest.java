package persistence;


import model.Student;
import model.Course;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {

    protected void checkStudent(String name, int id, Student student) {
        assertEquals(name, student.getName());
        assertEquals(id, student.getId());
    }

    protected void checkWorkList(List<Course> workList, Student student) {
        assertTrue(checkEveryCourseInWorkList(workList, student));
    }


    protected Boolean checkEveryCourseInWorkList(List<Course> workList, Student student) {
        if (workList.size() == student.getWorkList().size()) {
            int index = workList.size();
            for (int i = 0; i < index; i++) {
                if (!(checkEveryCourse(workList.get(i), student.getWorkList().get(i)))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    protected Boolean checkEveryCourseInRegisteredList(List<Course> registeredList, Student student) {
        if (registeredList.size() == student.getRegistereddList().size()) {
            int index = registeredList.size();
            for (int i = 0; i < index; i++) {
                if (!(checkEveryCourse(registeredList.get(i), student.getRegistereddList().get(i)))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }



    protected void checkRegisteredList(List<Course> registeredList, Student student) {
        assertTrue(checkEveryCourseInRegisteredList(registeredList, student));
    }

    protected void checkCourse(String subject, int courseNumber, List<Integer> sectionList, Course course) {
        assertEquals(subject, course.getSubject());
        assertEquals(courseNumber, course.getCourseNumber());
        List<Integer> testSection = course.getAllSections();
        assertTrue(checkSectionList(sectionList, course));
    }

    protected Boolean checkEveryCourse(Course course1, Course course2) {
        return course1.getSubject().equals(course2.getSubject())
                && course1.getCourseNumber() == course2.getCourseNumber()
                && checkSectionList(course1.getAllSections(), course2);


    }

    protected Boolean checkSectionList(List<Integer> sectionList, Course course) {
        if (sectionList.size() == course.getAllSections().size()) {
            int index = sectionList.size();
            for (int i = 0; i < index; i++) {
                if (sectionList.get(i) != course.getAllSections().get(i)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

}