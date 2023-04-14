import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MaakOpTest extends MaakOp {
    DecimalFormat df = new DecimalFormat("###,###,##0.000000000");

    @Override
    public void tekstOpmaken(String input, String variable){

        System.out.printf("%-17s %20s\n",input,variable);
    }
    @Override
    public void PrijzenOpmaken(String input, double getal){
        System.out.printf("%-40s %15s\n",input,"€"+df.format(getal));

    }
    @Override
    public void MaakOpOnderdeel(Part part, String type){
        switch(type) {
            case "list" -> {
                System.out.printf("\t%-17S> %33s\n", part.getName(), ">€" + df.format(part.getPrice()));
            }
            case"individual" ->{
                System.out.printf("%-20S %5s %15s\n",part.getName(),"(essentieel)","prijs: "+df.format(part.getPrice()));
            }
        }
    }
    @Override
    public void MaakOpBoot(Boat boat){
        System.out.printf("Type: %-17S Naam: %-15s startprijs: %10s\n", boat.getType(),boat.getName(),"€"+boat.getBasePrice());
    }


    @Test
    public void testTekstOpmaken() {
        //Arrange
        MaakOp maakOp = new MaakOpTest();

        //Act
        maakOp.tekstOpmaken("Test Input", "Test Variable");

        //Assert
        assertEquals("Test Input                Test Variable","Test Input                Test Variable");

    }



    @Test
    public void testPrijzenOpmaken(String input, double getal) {
        MaakOpTest maakOp = new MaakOpTest();
        //Act
        maakOp.PrijzenOpmaken("Test", 1000.0);

        assertEquals("Test Input                                €1,000.000000","Test Input                                €1,000.000000");

    }



    @Test
    public void testMaakOpOnderdeel() {
        MaakOp maakOp = new MaakOpTest();
        //Act
        Part part = new Part("Test Part", 1000.0,true);
        //Assert
        assertEquals(part, "TEST PART            (essentieel) prijs: 1.000,000000");
    }

    @Test
    public void testMaakOpBoot() {
        MaakOp maakOp = new MaakOpTest();
        //Act
        Boat boat = new Boat("Test Type", "Test Name", "123123",200.0);
        //Assert
        assertEquals(boat,"Type: TEST TYPE         Naam: Test Name       startprijs: €1.000,000000");
    }
}


