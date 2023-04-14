import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class boatListTest {

    @Test
    void testCreateBoat() {
        boatList list = new boatList();
        int boats = list.boats.size();
        Boat boat = new Boat("fasdf","adsfasdf","20222",100.40);
        list.boats.add(boat);
        assertEquals(boats + 1 , list.boats.size());
    }

    @Test
    void testSelectBoat() {
        boatList list = new boatList();
        Boat selectBoat = list.selectBoat("lamboat");
        assertEquals(selectBoat.getName(),"lamboat");
    }
}
