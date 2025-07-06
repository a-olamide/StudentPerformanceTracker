package service;

import model.Subject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubjectServiceTest {
    private static final SubjectService service = new SubjectService();
    private static int insertedId;

    @Test
    @Order(1)
    public void testAddSubject() {
        Subject s = new Subject();
        s.setName("Philosophy");
        service.addSubject(s);

        List<Subject> all = service.getAllSubjects();
        assertTrue(all.stream().anyMatch(sub -> sub.getName().equals("Philosophy")));
        insertedId = all.get(all.size() - 1).getId();
    }

    @Test
    @Order(2)
    public void testDeleteSubject() {
        service.deleteSubject(insertedId);
        Subject deleted = service.findById(insertedId);
        assertNull(deleted);
    }
}
