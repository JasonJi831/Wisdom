package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


import java.util.*;
import java.lang.String;

// Represents a student with a name, an eight-digit student ID, a course work list and a registered course list.

public class Student implements Writable {

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


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("id", id);
        json.put("work list", workListToJson());
        json.put("registered list", registeredListToJson());
        return json;
    }

    // EFFECTS: returns the course work list as a JSON array
    private JSONArray workListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course c : workList) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns the course registration list as a JSON array
    private JSONArray registeredListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course c : registeredList) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }


    // REQUIRES: course number > 0
    // MODIFIES: this
    // EFFECTS: adds a course to the course work List
    public void addACourseToWorkList(String courseSubject, int courseNumber) {
        Course addedCcourse = new Course(courseSubject, courseNumber);
        workList.add(addedCcourse);

    }

    // REQUIRES: course number > 0.
    // MODIFIES: this
    // EFFECTS: returns true if there is course in the work list that has a same name of subject and course number;
    // false otherwise
    public boolean addANewCourseToWorkListCheckSameCourse(String courseSubject, int courseNumber) {
        for (Course c : workList) {
            if (c.sameCourse(courseSubject, courseNumber)) {
                return true;
            }
        }
        return false;
    }


    // REQUIRES: course number > 0, and section number > 0.
    // MODIFIES: this
    // EFFECTS: returns false If there is no such course with the same course subject and courseNumber
    // in the work list, true otherwise.
    public boolean workListContainSameCourse(String courseSubject, int courseNumber) {
        for (Course c : workList) {
            if (c.sameCourse(courseSubject, courseNumber)) {
                return true;
            }
        }
        return false;
    }


    // REQUIRES: course number > 0, and section number > 0.
    // MODIFIES: this
    // EFFECTS: adds a course section to the course work list with corresponding course subject,
    // course number and section number and returns true. If there is no such course in the work list,
    // returns false only.
    public boolean addANewSectionToWorkListCheckSameCourse(String courseSubject, int courseNumber, int sectionNum) {
        for (Course c : workList) {
            if (c.sameCourse(courseSubject, courseNumber)) {
                c.addSection(sectionNum);
                return true;
            }
        }
        return false;
    }

    // REQUIRES: workList already has a course with the same subject and courseNumber.
    // EFFECT: returns true if a sectionNumber has been added to a course with the same subject and courseNumber, false
    // otherwise.
    public boolean addANewSectionToWorkListCheckSameSection(String subject, int courseNumber, int sectionNumber) {
        for (Course c : workList) {
            if (c.sameCourse(subject, courseNumber)) {
                return c.getAllSections().contains(sectionNumber);
            }
        }
        return false;
    }

    // REQUIRES: course number > 0, and section number > 0.
    // MODIFIES: this
    // EFFECTS: adds a course section to the course work list with corresponding course subject,
    // course number and section number and returns true. If either a given section number has been added to
    // this course or there is no any course in the work list, returns false only.
    public boolean addANewSectionToWorkList(String courseSubject, int courseNumber, int sectionNum) {
        for (Course c : workList) {
            if (c.sameCourse(courseSubject, courseNumber) && !c.getAllSections().contains(sectionNum)) {
                c.addSection(sectionNum);
                return true;
            }
        }
        return false;
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
            while (c.sameCourse(courseSubject, courseNumber)) {
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
            while (c.sameCourse(courseSubject, courseNumber)) {
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


    // REQUIRES: course number > 0, section > 0, registered List contains such a course with same course subject and
    // numbers as given.
    // MODIFIES: this
    // EFFECTS: returns true if the course in the registeredList with the same course subject and number contains such
    // section, false otherwise.
    public Boolean whetherContainSuchSectionInRegList(String courseSubject, int courseNumber, int section) {
        for (Course c : registeredList) {
            while (c.sameCourse(courseSubject, courseNumber)) {
                return c.getAllSections().contains(section);
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
                    c.removeSection(section);
                    return true;
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
                if (c.getSubject().equals(subjectName) && c.getCourseNumber() == courseNumber) {
                    c.removeSection(section);
                    return true;
                }
            }
        }
        return false;

    }


    // REQUIRES: course number > 0, section > 0
    // MODIFIES: this
    // EFFECTS:  removes all sections from the course registered list based on the provided course name,
    // course number, and section, and then, remove this course from the course registered List.
    public void removeAllSections(String subjectName, int courseNumber) {
        if (!this.registeredList.isEmpty()) {
            Iterator<Course> iterator = this.registeredList.iterator();
            while (iterator.hasNext()) {
                Course c = iterator.next();
                if (c.sameCourse(subjectName, courseNumber)) {
                    c.getAllSections().clear(); // Assuming this is a method that returns a list of sections
                    iterator.remove(); // Use the iterator's remove method
                }
            }
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