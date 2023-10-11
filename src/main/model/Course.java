package model;

import java.util.*;

// Represents a course with its four-letter course subject code, three-digit course number, a list of sections,
// and a corresponding list of times.
// Note: Both lists should have the same length.
public class Course {

    private String subject;
    private int courseNumber;
    private String faculty;
    private List<Section> sectionList;


    // EFFECTS: constructs a course with its course name, number, associated faculty and an
    // empty list of sections and an empty list of times.
    public Course(String subject, int courseNumber, String faculty) {
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.faculty = faculty;
        this.sectionList = new ArrayList<>();

    }


    // MODIFIES: this
    // EFFECTS: adds a section to the list of sections and returns true; if it has added to the list, return false only.
    public Boolean addSection(Section section) {
        if (this.sectionList.contains(section)) {
            return false;
        } else {
            sectionList.add(section);
            return true;
        }

    }

    // EFFECTS: returns true if the section list contains a given section number; false otherwise.
    public Boolean whetherContainsSection(int section) {
        if (sectionList.isEmpty() == false) {
            for (Section s : sectionList) {
                if (s.getSectionNumber() == section) {
                    return true;
                }
            }
            return false;


        } else {
            return false;
        }
    }


    // MODIFIES: this
    // EFFECTS: removes a section with the given section number and returns true; if there is no such section number,
    // returns false only.
    public Boolean removeSection(int sectionNumber) {
        if (this.sectionList.isEmpty() == false) {
            for (Section s : sectionList) {
                if (s.getSectionNumber() == sectionNumber) {
                    this.sectionList.remove(s);
                    return true;
                }
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

    // EFFECTS: return the faculty of the course
    public String getFaculty() {
        return this.faculty;
    }


    // EFFECTS: returns all sections of the course
    public List<Section> getAllSections() {
        return sectionList;


    }


}