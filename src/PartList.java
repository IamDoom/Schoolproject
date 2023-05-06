
import java.util.ArrayList;
import java.util.Scanner;

public class PartList extends MaakOp {
    private final ArrayList<Part> parts = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public PartList() {
        Part hull = new Part("hull", 2.0, true);
        parts.add(hull);
        Part hullFrame = new Part("hull frame", 2.0, true);
        parts.add(hullFrame);
        Part deck = new Part("deck", 2.0, true);
        parts.add(deck);
        Part cabin = new Part("cabin", 2.0, true);
        parts.add(cabin);
        Part lifeBuoys = new Part("life buoys", 2.0, false);
        parts.add(lifeBuoys);
        Part radio = new Part("radio", 2.0, false);
        parts.add(radio);
        Part radars = new Part("radars", 2.0, false);
        parts.add(radars);
        Part towerCranes = new Part("tower cranes", 2.0, false);
        parts.add(towerCranes);
        Part flagDecor = new Part("flags decoration", 2.0, false);
        parts.add(flagDecor);

        for (Part part : parts) {
            part.getEcoDiscount();
        }
    }

    public ArrayList<Part> getParts() {
        return parts;
    }

    public void createPart() {
        System.out.print("Onderdeel naam? ");
        String name = scanner.nextLine().strip();
        System.out.print("Prijs? ");
        double price = scanner.nextDouble();
        scanner.nextLine(); //to prevent nextdouble from eating;
        boolean essential = essentialOrNot();
        Part option = new Part(name, price, essential);
        parts.add(option);
    }

    public void deletePart(Part part) {
        parts.remove(part);
        System.out.println(part.getName() + " succesvol vernietigd");
    }

    public void displayParts() {
        System.out.println("essentiële opties:");
        for (Part part : parts)
            if (part.getEssential()) {
                MaakOpOnderdeel(part, "list"); //dit moet
            }
        System.out.println("extra opties:");
        for (Part part : parts) {
            if (!part.getEssential()) {
                MaakOpOnderdeel(part, "list");
            }
        }
    }

    private boolean essentialOrNot() {

        while (true) {
            System.out.print("Is deze optie essentieel? ");
            String input = scanner.nextLine().strip().toLowerCase();
            switch (input) {
                case "ja" -> {
                    return true;
                }
                case "nee" -> {
                    return false;
                }
                default -> System.out.println("foutief, voer 'ja' of 'nee'");
            }
        }
    }

    public Part getPart(String name) {
        for (Part part : parts) {
            if (part.getName().equalsIgnoreCase(name)) {
                return part;
            }
        }
        return null;
    }
}
