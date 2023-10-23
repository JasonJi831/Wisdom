package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents a course with its course subject code, course number, and a list of sections,
public class Course implements Writable {

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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("subject", subject);
        json.put("course number", courseNumber);
        json.put("section List", sectionListToJson());
        return json;
    }

    // EFFECTS: returns the section list of a course as a JSON array
    private JSONArray sectionListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int s : sectionList) {
            jsonArray.put(s);
        }
        return jsonArray;
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