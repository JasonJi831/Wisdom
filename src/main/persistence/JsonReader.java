// Reference: Paul Carter, Oct 17.2020, JsonSerializationDemo, java,
// "https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java"

package persistence;

import model.Student;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads the course work list and registered list of a student from JSON data stored in file.
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads the course work list and registered list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Student read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStudent(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses the course work list and registered list of a student from JSON object and returns it
    private Student parseStudent(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int id = jsonObject.getInt("id");
        Student s = new Student(name, id);
        addCourses(s, jsonObject);
        return s;
    }

    // MODIFIES: student s
    // EFFECTS: parses the course work list and registered list from JSON object and adds them to the student
    private void addCourses(Student s, JSONObject jsonObject) {
        JSONArray jsonWorkListArray = jsonObject.getJSONArray("work list");
        for (Object json : jsonWorkListArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourseToWorkList(s, nextCourse);
        }

        JSONArray jsonRegisteredListArray = jsonObject.getJSONArray("registered list");
        for (Object json : jsonRegisteredListArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourseToRegisteredList(s, nextCourse);
        }

    }

    // MODIFIES: student s
    // EFFECTS: parses course from JSON object and adds it to the course work list of a student.
    private void addCourseToWorkList(Student s, JSONObject jsonObject) {
        String subject = jsonObject.getString("subject");
        int courseNumber = jsonObject.getInt("course number");
        s.addACourseToWorkList(subject, courseNumber);
        JSONArray sectionList = jsonObject.getJSONArray("section List");
        for (int i = 0; i < sectionList.length(); i++) {
            s.addANewSectionToWorkList(subject, courseNumber, sectionList.getInt(i));
        }
    }

    // MODIFIES: student s
    // EFFECTS: parses course from JSON object and adds it to the course registered list of a student.
    private void addCourseToRegisteredList(Student s, JSONObject jsonObject) {
        String subject = jsonObject.getString("subject");
        int courseNumber = jsonObject.getInt("course number");
        JSONArray sectionList = jsonObject.getJSONArray("section List");
        for (int i = 0; i < sectionList.length(); i++) {
            s.registerCourse(subject, courseNumber, sectionList.getInt(i));
        }
    }
}