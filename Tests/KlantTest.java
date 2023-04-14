import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KlantTest {

    @Test
    void testKlant() {
        Klant klant = new Klant("Magdi", new Bedrijf("Bedrijf"));
        assertEquals("Magdi","Magdi", klant.getNaam());
        assertEquals("Bedrijf","Bedrijf", klant.getKlantentype().getNaam());

        klant = new Klant("Kaan");
        assertEquals("Kaan", klant.getNaam());
    }

    @Test
    void testGetters() {
        Klant klant = new Klant("Mootje");
        klant.setNaam("Mootje");
        assertEquals("Mootje", klant.getNaam());

        klant.setKlantentype(new Particulier("Zzper"));
        assertEquals("Zzper", klant.getKlantentype().getNaam());
    }

    @Test
    void testKlantentype() {
        Klantentype particulier = new Particulier("Particulier");
        assertEquals("Particulier", particulier.getNaam());

        Klantentype bedrijf = new Bedrijf("bedrijf 42");
        assertEquals("bedrijf 42", bedrijf.getNaam());

        Klantentype overheid = new Overheid("Overheid 69");
        assertEquals("Overheid 69", overheid.getNaam());

        Klantentype nieuw = new NieuwKlantentype("yass", 0.1);
        assertEquals("yass", nieuw.getNaam());
    }
}
