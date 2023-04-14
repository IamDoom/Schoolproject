import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShellRunTest {
    @Test
    public void testRun() {
        shell shell = new shell();
        shell.run();
        String expectedOutput = ("<SETUP>    'voor het maken van een offerte'\n" +
                "<PRINT>    'print de offerte zoals die er op het moment uit ziet'\n" +
                "<CREATE>   'voor het aanmaken van een onderdeel'\n" +
                "<ADD>      'voegt een bestaand onderdeel toe'\n" +
                "<LIST>     'toont een lijst van beschikbare onderdelen'\n" +
                "<FINALIZE> 'slaat de offerte op en sluit het programma af'\n" +
                "<EXIT>     'sluit het programma af'\n" +
                "<DISCOUNT> 'voegt korting toe aan een onderdeel\n" +
                "<DESTROY>  'vernietigd een beschikbaar onderdeel'\n" +
                "<SELECT>   'selecteerd een specifiek onderdeel'\n" +
                "please use a valid input use 'help' for help\n");
        assertEquals(expectedOutput, expectedOutput);

    }
}
