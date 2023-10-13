package model;

import java.util.*;

// Represents a course with its course subject code, course number, and a list of sections,
public class Course {

    private String subject;
    private int courseNumber;
    private List<Integer> sectionList;

    // REQUIRES: course number > 0
    // EFFECTS: constructs a course with its course subject name, number and an
    // empty list of sections.
    public Course(String subject, int courseNumber) {
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.sectionList = new ArrayList<>();

    }

    // REQUIRES: course number > 0
    // EFFECTS: returns true if a given course subject and a given course number are same as this course;
    // false otherwise.
    public boolean sameCourse(String subject, int courseNumber) {
        return this.subject.equals(subject) && this.courseNumber == courseNumber;
    }


    // REQUIRES: section number > 0
    // EFFECTS: add a given section number to the section list.
    public void addSection(int sectionNumber) {
        sectionList.add(sectionNumber);

    }

    // REQUIRES: course number > 0
    // EFFECTS: removes a given section number from the section list and returns true. If there is no such section,
    // returns false only.
    public Boolean removeSection(int sectionNum) {
        for (int i = 0; i < sectionList.size(); i++) {
            if (sectionList.get(i) == sectionNum) {
                sectionList.remove(i);
                return true;
            }
        }
        return false;


    }


    // EFFECTS: return the subject of the course
    public String getSubject() {
        return this.subject;
    }

    // EFFECTS: return the course number of the course
    public int getCourseNumber() {
        return this.courseNumber;
    }


    // EFFECTS: returns all sections of the course
    public List<Integer> getAllSections() {
        return sectionList;


    }


}