package model;

import java.util.*;
import java.lang.String;

// Represents a student with a name, an eight-digit student ID, a course work list and a registered course list.

public class Student {

    private String name;
    private int id;
    private List<Course> workList;
    private List<Course> registeredList;

    // REQUIRES: id is an 8-digit positive integer.
    // EFFECTS: constructs a student with the given name, student id, an empty course work list and an empty registered
    // course list.
    public Student(String name, int id) {
        this.name = name;
        this.id = id;
        this.workList = new ArrayList<>();
        this.registeredList = new ArrayList<>();
    }


    // REQUIRES: course number > 0
    // MODIFIES: this
    // EFFECTS: adds a course to the course work List
    public void addACourseToWorkList(String courseSubject, int courseNumber) {
        Course addedCcourse = new Course(courseSubject, courseNumber);
        workList.add(addedCcourse);

    }


    // REQUIRES: course number > 0, and section number > 0.
    // MODIFIES: this
    // EFFECTS: adds a course section to the course work list with corresponding course subject, course number and
    // section number.
    public void addANewSectionToWorkList(String courseSubject, int courseNumber, int sectionNum) {
        for (Course c : workList) {
            if (c.sameCourse(courseSubject, courseNumber)) {
                c.addSection(sectionNum);
                return;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a course to the registered course list with corresponding course subject, course number and
    // section number.
    public void registerCourse(String courseSubject, int courseNumber, int sectionNumber) {
        Course addedCcourse = new Course(courseSubject, courseNumber);
        addedCcourse.addSection(sectionNumber);
        registeredList.add(addedCcourse);
    }

    // EFFECTS: returns a list of all courses in the course work list, including their course subject and number, a
    // s strings (ex: "CPSC210").
    public List<String> showALlCourseInWorkList() {
        List<String> result = new ArrayList<>();
        for (Course c : workList) {
            result.add(c.getSubject() + String.valueOf(c.getCourseNumber()));
        }
        return result;
    }

    // EFFECTS: returns a list of all courses in the course registered list, including their course subject and number,
    // as strings (ex: "CPSC210").
    public List<String> showALlCourseInRegisteredList() {
        List<String> result = new ArrayList<>();
        for (Course c : registeredList) {
            result.add(c.getSubject() + String.valueOf(c.getCourseNumber()));
        }
        return result;
    }


    // REQUIRES: course number > 0
    // EFFECTS: returns a list of all sections for a course in the course work list that matches the provided course
    // subject and course number.
    public List<Integer> showAllSectionOfThisCourseInWorkList(String courseSubject, int courseNumber) {
        for (Course c : workList) {
            if (c.sameCourse(courseSubject, courseNumber)) {
                return c.getAllSections();
            }

        }
        return null;
    }

    // REQUIRES: course number > 0
    // EFFECTS: returns a list of all sections for a course in the course registered list that matches the
    // provided course subject and course number.
    public List<Integer> showAllSectionOfThisCourseInRegisteredList(String courseSubject, int courseNumber) {
        for (Course c : registeredList) {
            if (c.sameCourse(courseSubject, courseNumber)) {
                return c.getAllSections();
            }

        }
        return null;
    }


    // REQUIRES: course number > 0
    // EFFECTS: returns true if a given course, identified by its course subject, course number, and faculty,
    // matches one of the courses in the course work list; otherwise, returns false.
    public boolean repetitiveCourseInWorkList(String courseSubject, int courseNumber) {
        for (Course c : workList) {
            if (c.sameCourse(courseSubject, courseNumber)) {
                return true;
            }
        }
        return false;
    }


    // REQUIRES: course number > 0
    // EFFECTS: returns true if a given course, identified by its course subject, course number, and faculty,
    // matches one of the courses in the registered course list; otherwise, returns false.
    public Boolean repetitiveCourseInRegisteredList(String courseSubject, int courseNumber) {
        for (Course c : registeredList) {
            while (c.sameCourse(courseSubject, courseNumber)) {
                return true;
            }

        }
        return false;
    }


    // REQUIRES: course number > 0, section > 0
    // MODIFIES: this
    // EFFECTS: removes a specific course section from the course work list based on the provided course name,
    // course number, and section. If the specified course section is not found, it returns false.
    public Boolean deleteOneCourseSection(String subjectName, int courseNumber, int section) {
        if (this.workList.isEmpty() == false) {
            for (Course c : this.workList) {
                if (c.getSubject().equals(subjectName) && c.getCourseNumber() == courseNumber) {
                    return c.removeSection(section);
                }
            }


        }
        return false;
    }

    // REQUIRES: course number > 0, section > 0
    // MODIFIES: this
    // EFFECTS:  removes a specific course section from the course registered list based on the provided course name,
    // course number, and section. If the specified course section is not found, it returns false.
    public Boolean dropOneCourseSection(String subjectName, int courseNumber, int section) {
        if (this.registeredList.isEmpty() == false) {
            for (Course c : this.registeredList) {
                while (c.getSubject().equals(subjectName) && c.getCourseNumber() == courseNumber) {
                    c.removeSection(section);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }


    // EFFECTS: returns the student's name
    public String getName() {
        return this.name;

    }

    // EFFECTS: returns the student's eight-digit student ID
    public int getId() {
        return this.id;

    }


    // EFFECTS: returns the student's name
    public List<Course> getWorkList() {
        return this.workList;

    }


    // EFFECTS: returns the student's registered course list
    public List<Course> getRegistereddList() {
        return this.registeredList;

    }

}