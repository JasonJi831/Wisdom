package persistence;


import model.Student;
import model.Course;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Student s = new Student("testStudent", 11111111);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterStudentWithTwoEmptyList() {
        try {
            Student s = new Student("testStudent", 11111111);
            JsonWriter writer = new JsonWriter("./data/testWriterStudentWithTwoEmptyList.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterStudentWithTwoEmptyList.json");
            s = reader.read();
            assertEquals("testStudent", s.getName());
            assertEquals(11111111, s.getId());
            assertEquals(0, s.getWorkList().size());
            assertEquals(0, s.getRegistereddList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStudentWithNoSectionInWorkList() {
        try {
            Student s = new Student("testStudent", 11111111);
            s.addACourseToWorkList("CPSC", 210);
            s.registerCourse("CPSC",110, 101);
            List<Course> testWorkList = s.getWorkList();
            List<Course> testRegisteredList = s.getRegistereddList();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStudent.json");
            writer.open();
            writer.write(s);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralStudent.json");
            s = reader.read();
            checkStudent("testStudent", 11111111, s);
            assertEquals(1, testWorkList.size());
            assertEquals(1, testRegisteredList.size());
            checkWorkList(testWorkList, s);
            checkRegisteredList(testRegisteredList, s);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralStudentWithSectionInWorkList() {
        try {
            Student s = new Student("testStudent", 11111111);
            s.addACourseToWorkList("CPSC", 210);
            s.addANewSectionToWorkList("CPSC", 210, 101);
            s.registerCourse("CPSC",110, 101);
            List<Course> testWorkList = s.getWorkList();
            List<Course> testRegisteredList = s.getRegistereddList();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStudent.json");
            writer.open();
            writer.write(s);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralStudent.json");
            s = reader.read();
            checkStudent("testStudent", 11111111, s);
            assertEquals(1, testWorkList.size());
            assertEquals(1, testRegisteredList.size());
            checkWorkList(testWorkList, s);
            checkRegisteredList(testRegisteredList, s);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }




}
