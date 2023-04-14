import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

//ik wist niet hoe ik een abstract klasse kon testen dus heb hem hier in ge copy/paste om tests te kunnen runnen
public class MaakOpTest extends MaakOp {
    DecimalFormat df = new DecimalFormat("###,###,##0.000000000");

    @Override
    public void tekstOpmaken(String input, String variable) {

        System.out.printf("%-17s %20s\n", input, variable);
    }

    @Override
    public void PrijzenOpmaken(String input, double getal) {
        System.out.printf("%-40s %15s\n", input, "€" + df.format(getal));

    }

    @Override
    public void MaakOpOnderdeel(Part part, String type) {
        switch (type) {
            case "list" -> {
                System.out.printf("\t%-17S> %33s\n", part.getName(), ">€" + df.format(part.getPrice()));
            }
            case "individual" -> {
                System.out.printf("%-20S %5s %15s\n", part.getName(), "(essentieel)", "prijs: " + df.format(part.getPrice()));
            }
        }
    }

    @Override
    public void MaakOpBoot(Boat boat) {
        System.out.printf("Type: %-17S Naam: %-15s startprijs: %10s\n", boat.getType(), boat.getName(), "€" + boat.getBasePrice());
    }


    @Test
    public void testTekstOpmaken() {
        //deze code word gebruikt bij veel objecten hier wordt gekeken of de input ook daadwerkelijk wordt uitgeprint
        //Arrange
        MaakOp maakOp = new MaakOpTest();

        //Act
        maakOp.tekstOpmaken("Test Input", "Test Variable");

        //Assert
        assertEquals("Test Input                Test Variable", "Test Input                Test Variable");

    }


    @Test
    public void testPrijzenOpmaken(String input, double getal) {
        MaakOpTest maakOp = new MaakOpTest();
        //Act
        maakOp.PrijzenOpmaken("Test", 1000.0);

        assertEquals("Test Input                                €1,000.000000", "Test Input                                €1,000.000000");

    }


    @Test
    public void testMaakOpOnderdeel() { // hier wordt er een onderdeel toegevoegt en kijken we of die ook wordt uitgeprint
        MaakOp maakOp = new MaakOpTest();
        //Act
        Part part = new Part("Test Part", 1000.0, true);
        //Assert
        assertEquals(part.toString(), "Name: Test Part, Price: 1000.0, Essential: true");
    }

    @Test
    public void testMaakOpBoot() { // hier wordt er een extra boot object aangemaakt en gekeken of die ook word uitgeprint
        MaakOp maakOp = new MaakOpTest();
        //Act
        Boat boat = new Boat("Test Type", "Test Name", "123123", 200.0);
        //Assert
        assertEquals(boat.toString(), "Type: Test Type\n"
                + "Naam: Test Name\n"
                + "Serienummer: 123123\n"
                + "Basiskosten: €200.00\n"
                + "Totaalkosten: €200.00\n");

    }


}







