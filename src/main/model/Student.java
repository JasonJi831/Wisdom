package model;

import java.util.*;

// Represents a student with a name, an eight-digit student ID, a course work list,
// a registered course list, and a course wait-list.

public class Student {

    private String name;
    private int id;
    private List<WorkList> workLists;
    private List<Course> registeredList;
    private List<Course> waitList;

    // REQUIRES: id is an 8-digit positive integer.
    // EFFECTS: constructs a student with the given name, student id and three empty course lists.
    public Student(String name, int id) {
        this.name = name;
        this.id = id;
        workLists = new ArrayList<>();
        registeredList = new ArrayList<>();
        waitList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a work list to the course work list and returns true. If a workList has already added to this list,
    // returns false only.
    public Boolean addCourseToWorkList(WorkList workList) {
        if (this.workLists.contains(workList)) {
            return false;
        } else {
            this.workLists.add(workList);
            return true;
        }

    }


    // MODIFIES: this
    // EFFECTS: adds a course to the registered course list and returns true. If a course has already added to this
    // list, returns false only.
    public Boolean registerCourse(Course course) {
        if (this.registeredList.contains(course)) {
            return false;
        } else {
            this.registeredList.add(course);
            return true;
        }

    }

    // MODIFIES: this
    // EFFECTS: adds a course to the course wait list and returns true. If a course has already added to this list,
    // returns false only.
    public Boolean addCourseToWaitList(Course course) {
        if (this.waitList.contains(course)) {
            return false;
        } else {
            this.waitList.add(course);
            return true;
        }

    }


    // MODIFIES: this
    // EFFECTS: removes a work list from the course work list based on the given name of the course work list
    // and return true. If there is no such work list, returns false only.
    public Boolean deleteOneWorkList(String name) {
        if (this.workLists.isEmpty() == false) {
            for (WorkList wl : this.workLists) {
                if (wl.getName() == name) {
                    this.workLists.remove(wl);
                    return true;
                }
            }
        }
        return false;

    }

    // MODIFIES: this
    // EFFECTS: removes a course section of a course from the registered list based on the given course name,
    // course number and section. If there is no such course section, returns false only.
    public Boolean dropOneCourseSection(String subjectName, int courseNumber, int section) {
        if (this.registeredList.isEmpty() == false) {
            for (Course c : this.registeredList) {
                while (c.getSubject() == subjectName && c.getCourseNumber() == courseNumber
                        && c.whetherContainsSection(section)) {
                    c.removeSection(section);
                    return true;
                }
            }
            return false;

        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a course section of a course from the course wait list based on the given course name,
    // course number and section. If there is no such course section, returns false only.
    public Boolean removeCourseSectionFromWaitList(String subjectName, int courseNumber, int section) {
        if (this.waitList.isEmpty() == false) {
            for (Course c : this.waitList) {
                while (c.getSubject() == subjectName && c.getCourseNumber() == courseNumber
                        && c.whetherContainsSection(section)) {
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
        return name;

    }


    // EFFECTS: returns the student's eight-digit student ID
    public int getId() {
        return id;

    }

    // EFFECTS: returns the student's course work List
    public List<WorkList> getWorkList() {
        return workLists;

    }

    // EFFECTS: returns the student's registered course list
    public List<Course> getRegistereddList() {
        return registeredList;

    }

    // EFFECTS: returns the student's course wait-list.
    public List<Course> getWaitList() {
        return waitList;

    }

}