package persistence;

import model.Student;
import model.Course;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Student s = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyStudent() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStudent.json");
        try {
            Student s = reader.read();
            assertEquals("testStudent", s.getName());
            assertEquals(0, s.getWorkList().size());
            assertEquals(0, s.getRegistereddList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralStudent() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStudent.json");
        try {
            Student s = reader.read();
            List<Course> testWorkList = s.getWorkList();
            List<Course> testRegisteredList = s.getRegistereddList();
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