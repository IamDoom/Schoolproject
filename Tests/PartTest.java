import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartTest {

    @Test
    //hier wordt er een odnerdeel gemaakt en gekeken of de individuelen onderdelen ervan opgevraagt kunnen worden
    public void testPart() {
        Part part = new Part("test part", 10.0, true, "description");
        assertEquals("description", part.getDescription());

        part.setDescription("new test description");

        assertEquals(part.getDescription(),"new test description");

        assertEquals(part.getPrice(),10.0);

        assertEquals( part.getName(),"test part");

        assertEquals(10.0, 10.0);

        assertEquals( part.getEcoDiscount(),0.0);

        assertEquals( part.getEcoDiscount(),0.0);
    }
}
