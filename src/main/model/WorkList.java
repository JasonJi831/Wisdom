package model;

import java.util.*;

// Represents a student course work list with its name and a list of course.

public class WorkList {
    private String name;
    private List<Course> courses;


    // EFFECTS: constructs a student course work list with the given and an empty list of course.
    public WorkList(String name) {
        this.name = name;
        courses = new ArrayList<>();

    }

    // MODIFIES: this
    // EFFECTS: adds a course to the end of the work list and return true. If a given course has already added to
    // the student list, return false only.
    public Boolean addCourse(Course course) {
        if (courses.contains(course)) {
            return false;
        } else {
            courses.add(course);
            return true;
        }

    }


    // EFFECTS: returns the name of the work list/
    public String getName() {
        return name;
    }

    // EFFECTS: returns all courses in a course work list.
    public List<Course> getAllCourses() {
        return courses;

    }


}