import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KlantTest {
//hier wordt er een klant en bedrijf aangemaakt.
    @Test
    void testKlant() {
        Bedrijf bedrijf = new Bedrijf("Bedrijf");
        Klant klant = new Klant("Magdi", bedrijf);
        assertEquals("Magdi","Magdi");
        assertEquals("Bedrijf","Bedrijf");

        klant = new Klant("Kaan");
        assertEquals("Kaan", "Kaan");
    }

    @Test
    //hier wordt gekeken of de getters en setters werken van de klasse Klant
    void testGetters() {
        Klant klant = new Klant("Mootje");
        klant.setNaam("Mootje");
        assertEquals( klant.getNaam(),"Mootje");

        klant.setKlantentype(new Particulier("Zzper"));
        assertEquals( klant.getKlantentype().getNaam(),"Zzper");
    }

    @Test
    //hier wordt gekeken of er er een nieuwe klantentype aangemaakt kan worden.
    void testKlantentype() {
        Klantentype particulier = new Particulier("Particulier");
        assertEquals(particulier.getNaam(),"Particulier");

        Klantentype bedrijf = new Bedrijf("bedrijf 42");
        assertEquals(bedrijf.getNaam(),"bedrijf 42");

        Klantentype overheid = new Overheid("Overheid 69");
        assertEquals(overheid.getNaam(),"Overheid 69");

        Klantentype nieuw = new NieuwKlantentype("yass", 0.1);
        assertEquals( nieuw.getNaam(),"yass");
    }
}
