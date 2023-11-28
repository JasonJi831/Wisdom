package model;

import java.util.*;


// Represents a coordinator having a name and a list of students that this coordinator handle.
public class Coordinator {
    private final String name;
    private List<Student> students;


    // EFFECTS: constructs a coordinator with the given name and an empty of list of students.
    public Coordinator(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a student to the end of the list of students that the course coordinator oversees and returns true.
    // If a given student has already added to the student list,
    // return false only.
    public boolean addStudent(Student student) {
        int id = student.getId();
        for (Student s : students) {
            while (s.getId() == id) {
                return false;
            }
        }
        students.add(student);
        return true;

    }

    // MODIFIES: this
    // EFFECTS: Remove a student from the course coordinator's student list based on their student ID and return true.
    // If there is no student with that ID, return false only.
    public boolean removeStudent(int id) {
        if (!this.students.isEmpty()) {
            for (Student s : students) {
                if (s.getId() == id) {
                    this.students.remove(s);
                    return true;
                }
            }
        }
        return false;

    }

    // EFFECTS: returns the name of the course coordinator
    public String getName() {
        return name;

    }

    // EFFECTS: returns a list contains all students
    public List<Student> getAllStudents() {
        return students;

    }


}