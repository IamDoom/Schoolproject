import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartTest {

    @Test
    public void testPart() {
        Part part = new Part("test part", 10.0, true, "description");
        assertEquals("description", part.getDescription());

        part.setDescription("new test description");

        assertEquals("new test description", part.getDescription());

        assertEquals(10.0, part.getPrice());

        assertEquals("test part", part.getName());

        assertEquals(10.0, 10.0);

        assertEquals(0.0, part.getEcoDiscount());

        assertEquals(0.0, part.getEcoDiscount());
    }
}
