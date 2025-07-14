package service;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import model.Student;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentServiceTest {
    private static final StudentService service = new StudentService();
    private static int insertedId;

    @Test
    @Order(1)
    public void testAddStudent() {
        Student s = new Student();
        s.setFirstName("John");
        s.setLastName("Doe");
        s.setGender("Male");
        s.setDob(LocalDate.of(2000, 1, 1));
        s.setUserId(1);

        service.addStudent(s);
        List<Student> all = service.getAllStudents();
        // Use Stream API for assertion
        assertTrue(all.stream().anyMatch(stu -> stu.getFirstName().equals("John")));
        insertedId = all.get(all.size() - 1).getId();
    }

    @Test
    @Order(2)
    public void testUpdateStudent() {
        Student student = service.findById(insertedId);
        student.setLastName("Smith");
        service.updateStudent(student);
        Student updated = service.findById(insertedId);
        assertEquals("Smith", updated.getLastName());
    }

    @Test
    @Order(3)
    public void testDeleteStudent() {
        service.deleteStudent(insertedId);
        Student deleted = service.findById(insertedId);
        assertNull(deleted);
    }
}
