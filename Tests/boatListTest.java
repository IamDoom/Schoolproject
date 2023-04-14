import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class boatListTest {
// in deze test word er een boot aangemaakt en gekeken of hij wordt toegevoegt aan de array
    @Test
    void testCreateBoat() {
        boatList list = new boatList();
        int boats = list.boats.size();
        Boat boat = new Boat("fasdf","adsfasdf","20222",100.40);
        list.boats.add(boat);
        assertEquals( list.boats.size(),boats + 1);
    }
    //hier wordt er gekeken of er  een boot geselecteerd kan worden uit de array

    @Test
    void testSelectBoat() {
        boatList list = new boatList();
        Boat selectBoat = list.selectBoat("lamboat");
        assertEquals(selectBoat.getName(),"lamboat");
    }
}
