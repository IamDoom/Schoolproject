import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;

class Part {
    private final String name;
    private final double price;
    private final boolean essential;
    private String Description;
    private double EcoDiscount;

    Part(String name, double price, boolean essential, String description) {
        this.name = name;
        this.price = price;
        this.essential = essential;
        this.Description = description;
        this.EcoDiscount = 0.0;
    }
    Part(String name, double price, boolean essential) {
        this(name, price, essential, null);
    }
    public void setDescription(String description) {
        this.Description = description;
    }

    public String getDescription(){
        return this.Description;
    }
    public void setEcoDiscount(double Ecodiscount) {
        this.EcoDiscount = Ecodiscount;
    }
    public double getPrice() {
        return this.price ;
    }
    public String getName() {
        return this.name;
    }

    public double getEcoDiscount(){return EcoDiscount;}

    public boolean getEssential() {
        return this.essential;
    }
    public String toString() {
        return "Name: " + this.name + ", Price: " + this.price + ", Essential: " + this.essential;
    }

}

class shell{
    Scanner scanner = new Scanner(System.in);
    PartList PartList = new PartList();
    quote quote = new quote();


    public void run(){
        boolean shell = true;
        while(shell) {
            String input = scanner.nextLine().strip();
            switch (input.toLowerCase()) {
                case "offerte" ->{
                    quote = quote.createQuote();
                    quote.setquoteDetails();
                    quote.setBoat(quote.Pickboat());
                }

                case "print" -> {
                    if (quote.getKlant() != null) {
                        quote.calculateTotalOfParts();
                        try {
                            quote.printQuoteToFile("offerte.txt");
                        } catch (FileNotFoundException e) {
                            System.out.println("Fout bij het schrijven van de offerte naar bestand: " + e.getMessage());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("u moet eerst een offerte maken om te printen");
                    }
                }

                case "create" -> {
                    boolean run = true;
                    System.out.println("wat wilt u aanmaken?\n<onderdeel><boot><exit>");
                    while (run) {
                        input = scanner.nextLine();
                        switch (input.toLowerCase()) {
                            case "onderdeel" -> {
                                PartList.createPart();
                                run = false;
                            }
                            case "boot" -> {
                                quote.boatList.createBoat();
                                run = false;
                            }
                            case "exit" -> run = false;
                            default -> System.out.println("kies <onderdeel><boot><exit>");
                        }
                    }
                }
                case "add" -> {
                    if(quote.getBoat() != null) {
                        quote.getBoat().addPart(PartList.getParts());
                    }else {
                        System.out.println("kies eerst een boot");
                        quote.setBoat(quote.Pickboat());
                    }
                }
                case "remove" ->{
                    if(quote.getBoat() != null){
                        if(quote.getBoat().selectedParts.size() > 0 ){
                            quote.getBoat().RemovePart(quote.getBoat().selectedParts);
                        }else{
                            System.out.println("u heeft geen onderdelen om te verwijderen");
                        }
                    }else{
                        System.out.println("u heeft geen boot geselecteerd");
                    }
                }

                case "boot" ->  quote.setBoat(quote.Pickboat());

                case "list" -> PartList.displayParts();

                case "select" -> {
                    boolean selection = true;
                    System.out.println("welk onderdeel wilt u selecteren?");
                    while (selection) {
                        input = scanner.nextLine();
                        Part part = PartList.getPart(input);
                        if (input.equalsIgnoreCase("exit")) {
                            System.out.println("u verlaat nu het onderdelen selectie process");
                            selection = false;
                        } else if(part == null) {
                            System.out.println("'" + input + "' bestaat niet.");
                            }else {
                            boolean subselect = true;
                            System.out.println("wat wilt u met '" + input + "' doen?");
                            while (subselect) {
                                input = scanner.nextLine();
                                switch (input) {
                                    case "korting" -> {
                                        System.out.println("geef de gewenste kortings percentage");
                                        part.setEcoDiscount(scanner.nextDouble());
                                        scanner.nextLine();
                                        System.out.println("het onderdeel " + part.getName() + " heeft nu " + part.getEcoDiscount() + "% korting");
                                        selection = false;
                                        subselect = false;
                                    }
                                    case "beschrijving" -> {
                                        if (part.getDescription() == null) {
                                            System.out.println("er is geen beschrijving beschikbaar");
                                        } else {
                                            System.out.println(part.getDescription());
                                        }
                                    }
                                    case "vernietig" -> PartList.deletePart(part);
                                    case "exit" -> {
                                        selection = false;
                                        subselect = false;
                                    }
                                    case "help" -> {
                                        System.out.println("<KORTING>      'plaats korting naar keuze op geselecteerd onderdeel'");
                                        System.out.println("<BESCHRIJVING> 'toont beschrijving onderdeel indien mogelijk'");
                                        System.out.println("<VERNIETIG>    'verwijdert de onderdeel'");
                                        System.out.println("<EXIT>         'verlaat het selectie process'");
                                    }
                                    default -> System.out.println("ongeldige input 'help' voor hulp");
                                }
                            }
                        }
                    }
                }
                case "finalize" ->{
                    System.out.println("finalizing before shutting down");
                    quote.setTransportKosten();
                    quote.printQuote();
                    shell = false;
                }
                case "exit" -> shell = false;

                case "help" -> {
                    System.out.println("<OFFERTE>  'voor het maken van een offerte'");
                    System.out.println("<PRINT>    'print de offerte zoals die er op het moment uit ziet'");
                    System.out.println("<CREATE>   'voor het aanmaken van een onderdeel'");
                    System.out.println("<ADD>      'voegt een bestaand onderdeel toe'");
                    System.out.println("<REMOVE>   'verwijdert onderdeel van de boot'");
                    System.out.println("<LIST>     'toont een lijst van beschikbare onderdelen'");
                    System.out.println("<FINALIZE> 'slaat de offerte op en sluit het programma af'");
                    System.out.println("<EXIT>     'sluit het programma af'");
                    System.out.println("<SELECT>   'selecteerd een specifiek onderdeel'");
                    System.out.println("<BOOT>     'geeft optie om boot te selecteren'");
                }

                default -> System.out.println("please use a valid input use 'help' for help");
            }
        }
    }
}

class quote extends MaakOp {
    Scanner scanner = new Scanner(System.in);
    boatList boatList = new boatList();
    private Klant klant;
    private String orderNumber;
    private Boat boat;
    private double btwPercentage;
    private double transportKosten;

    quote() {
    }
    public void printQuoteToFile(String filePath) throws IOException {
        OnthoudenVanNumbers onthoudenVanNumbers = this.calculateTotalOfParts();
        double totaalprijs = this.calculateTotal() * ((100 - klant.getKlantentype().getKorting()) / 100);

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("de volgende offerte is een simpele opmaak voor een boot");
            writer.println("dit is niet per se een definitieve versie\n");
            writer.println("informatie van de klant");
            writer.printf("clientname: ", klant.getNaam()+"\n");
            writer.printf( "ordernummer: ", getOrderNumber()+"\n");
            writer.printf("klantentype: ", klant.getKlantentype().getNaam()+"\n");

            writer.println("\ninformatie van de boot");
            writer.printf( "boottype: ", boat.getType()+"\n");
            writer.printf( "bootnaam: ", boat.getName()+"\n");
            writer.printf("serienummer: ", boat.getSerialNumber()+"\n");
            writer.println("\nbasis prijs van de boot");
            PrijzenOpmaken( "Boot start prijs:", boat.getBasePrice());

            writer.println("\nkosten onderdelen: ");
            if (boat.selectedParts != null) {
                for (Part part : boat.selectedParts) {
                    MaakOpOnderdeel(part, "list");
                    kortingOpmaken( part.getName(), part.getEcoDiscount());
                }
            } else {
                tekstOpmaken( "onderdelen:", "<niets geselecteerd>");
            }
            PrijzenOpmaken("Transport kosten:", this.transportKosten);
            writer.println();

            PrijzenOpmaken( "Totale kosten onderdelen:", onthoudenVanNumbers.totaalzonderkorting());
            PrijzenOpmaken( "Totale kosten onderdelen met korting:", onthoudenVanNumbers.totaalmetkorting());
            PrijzenOpmaken( "Totale hoeveelheid korting:", onthoudenVanNumbers.korting());
            PrijzenOpmaken( "BTW (" + this.btwPercentage + "%):", (boat.getBasePrice() * this.btwPercentage / 100));
            PrijzenOpmaken( "Totale prijs:", totaalprijs);
        }
    }



    quote(Klant klant) {
        this.klant = klant;
    }

    public static quote createQuote() {
        Klant klant = new Klant();
        return new quote(klant);
    }

    public void setquoteDetails() {
        setOrderNumber();
        PickCustomer();
        setBtwPercentage();
    }

    public void setBtwPercentage() {
        System.out.println("wat wordt het  btw percentage voor deze klant?");
        this.btwPercentage = scanner.nextDouble();
        scanner.nextLine();
    }

    public void setTransportKosten() {
        System.out.println("wat worden de transport kosten?");
        this.transportKosten = scanner.nextDouble();
        scanner.nextLine();
    }


    public void PickCustomer() {
        boolean run = true;
        System.out.println("voor welk klantentype wilt u een offerte maken?");
        System.out.println("'bedrijf' 'overheid' 'particulier' 'nieuw' (nieuw klantentype)");
        while (run) {
            String input = scanner.nextLine().strip();
            switch (input.toLowerCase()) {
                case "bedrijf" -> {
                    Bedrijf bedrijf = new Bedrijf("Bedrijf");
                    klant.setKlantentype(bedrijf);
                    run = false;
                }
                case "overheid" -> {
                    Overheid overheid = new Overheid("Overheid");
                    klant.setKlantentype(overheid);
                    run = false;
                }
                case "particulier" -> {
                    Particulier particulier = new Particulier("Particulier");
                    klant.setKlantentype(particulier);
                    run = false;
                }
                case "nieuw" -> {
                    System.out.println("wat is de naam van het nieuwe klantentype?");
                    String naamklantentype = scanner.nextLine();
                    System.out.println("wat is de hoeveelheid korting voor dit klantentype?");
                    double korting = scanner.nextDouble();
                    scanner.nextLine();
                    NieuwKlantentype nieuwKlantentype = new NieuwKlantentype(naamklantentype, korting);
                    klant.setKlantentype(nieuwKlantentype);
                    run = false;
                }
                default -> System.out.println("kies een geldige optie\n'bedrijf' 'overheid' 'particulier' 'nieuw'");
            }
        }

    }

    public Boat Pickboat() {
        boatList.displayBoats();
        System.out.println("welke boot(naam) wilt u?");
        while (true) {
            String Name = scanner.nextLine().strip();
            if (Name.equalsIgnoreCase("exit")) {
                break;
            } else if (boatList.selectBoat(Name) == null) {
                System.out.println("boot bestaat niet");
            } else {
                return boatList.selectBoat(Name);
            }
        }
        System.out.println("u verlaat de selectie ZONDER boot!");
        return null;
    }

    public void printQuote(){
        OnthoudenVanNumbers onthoudenVanNumbers = this.calculateTotalOfParts();
        double totaalprijs = this.calculateTotal() * ((100 - klant.getKlantentype().getKorting()) / 100);

        System.out.println("de volgende offerte is een simpele opmaak voor een boot");
        System.out.println("dit is niet per se een definitieve versie\n");
        System.out.println("informatie van de klant");
        tekstOpmaken("clientname: ", klant.getNaam());
        tekstOpmaken("ordernummer: ",getOrderNumber());
        tekstOpmaken("klantentype: ", klant.getKlantentype().getNaam());

        System.out.println("\ninformatie van de boot");
        tekstOpmaken("boottype: ", boat.getType());
        tekstOpmaken("bootnaam: ", boat.getName());
        tekstOpmaken("serienummer: ", boat.getSerialNumber());
        System.out.println("\nbasis prijs van de boot");
        PrijzenOpmaken("Boot start prijs:", boat.getBasePrice());

        System.out.println("\nkosten onderdelen: ");
        if(boat.selectedParts != null) {
            for (Part part : boat.selectedParts) {
                MaakOpOnderdeel(part, "list");
                kortingOpmaken(part.getName(), part.getEcoDiscount());
            }
        }else{tekstOpmaken("onderdelen:", "<niets geselecteerd>");}
        PrijzenOpmaken("Transport kosten:" , this.transportKosten);
        System.out.println();

        PrijzenOpmaken("Totale kosten onderdelen:", onthoudenVanNumbers.totaalzonderkorting());
        PrijzenOpmaken("Totale kosten onderdelen met korting:", onthoudenVanNumbers.totaalmetkorting());
        PrijzenOpmaken("Totale hoeveelheid korting:", onthoudenVanNumbers.korting());
        PrijzenOpmaken("BTW (" + this.btwPercentage + "%):" , (boat.getBasePrice() * this.btwPercentage / 100));
        PrijzenOpmaken("Totale prijs:" , totaalprijs);
    }
    public double calculateTotal(){
        double vatAmount = boat.totalPrice()*this.btwPercentage/100;
        return boat.totalPrice() + vatAmount + this.transportKosten;
    }


    public OnthoudenVanNumbers calculateTotalOfParts(){ //je kunt dit gebruiken als berekening van de prijs van de onderdelen
        double totaalzonderkorting = 0; // maak eerst een nieuw Onthoudenvannummers object aan en stel hem gelijk aan je quote.calculateTotalOfParts
        double totaalmetkorting = 0; //dan kun je getter gebruiken om de gegeven de accessen
        double korting;
        for(Part selectedpart : boat.selectedParts) {
            totaalzonderkorting += selectedpart.getPrice();
            totaalmetkorting += selectedpart.getPrice() * ((100-selectedpart.getEcoDiscount())/100);
        }
        korting = totaalzonderkorting - totaalmetkorting;
        return new OnthoudenVanNumbers(totaalzonderkorting, totaalmetkorting, korting);
    }
    public Klant getKlant() {
        return klant;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber() {
        System.out.print("voer gewenste ordernummer in: ");
        orderNumber = scanner.nextLine();
    }
    public Boat getBoat() {
        return this.boat;
    }
    public void setBoat(Boat boat) {
        this.boat = boat;
    }
}
class Klantentype{
    private double hoeveelheidkorting;
    private final String Naam;
    Klantentype(String naam){
        this.Naam = naam;
    }
    Klantentype(String naam, double hoeveelheidkorting){
        this(naam);
        this.hoeveelheidkorting = hoeveelheidkorting;
    }
    public String getNaam() {
        return this.Naam;
    }

    public double getKorting(){
        return hoeveelheidkorting;
    }

}
class Particulier extends Klantentype{ Particulier(String naam){
    super(naam);}
}
class Bedrijf extends Klantentype{
    Bedrijf(String naam){
        super(naam);
    }
}
class Overheid extends Klantentype{
    Overheid(String naam){
        super(naam);
    }
}
class NieuwKlantentype extends Klantentype{
    NieuwKlantentype(String naam, double hoeveelheidkorting){
        super(naam, hoeveelheidkorting);
    }
}
class Klant{
    Scanner scanner = new Scanner(System.in);
    private String naam;
    private Klantentype klantentype;

    public Klant(){setNaam();}
    public Klant(String naam){
        this.naam = naam;
    }
    public Klant(String name, Klantentype klantentype){
        this(name);
        this.klantentype = klantentype;
    }
    public String getNaam() {
        return naam;
    }
    public void setNaam(){
        System.out.print("Klant naam: ");
        this.naam = scanner.nextLine().strip();
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setKlantentype(Klantentype klantentype) {
        if (klantentype instanceof Particulier) {
            System.out.println("Particuliere klant toegevoegd.");
        } else if (klantentype instanceof Bedrijf) {
            System.out.println("Bedrijfsklant toegevoegd.");
        } else if (klantentype instanceof Overheid) {
            System.out.println("Overheidsklant toegevoegd.");
        } else {
            System.out.println("Onbekend klanttype toegevoegd.");
        }
        this.klantentype = klantentype;
    }
    public Klantentype getKlantentype() {
        return klantentype;
    }
}
abstract class MaakOp{
    DecimalFormat df = new DecimalFormat("###,###,##0.000000");
    DecimalFormat df2 = new DecimalFormat("##0.00");
    public void tekstOpmaken(String input, String variable){

        System.out.printf("%-17s %20s\n",input,variable);
    }
    public void PrijzenOpmaken(String input, double getal){
        System.out.printf("%-40s %15s\n",input,"€"+df.format(getal));

    }
    public void kortingOpmaken(String input, double getal){
        System.out.printf("%-40S %15s\n",input+" korting:","%"+df2.format(getal));

    }
    public void MaakOpOnderdeel(Part part, String type){
        switch(type) {
            case "list" -> System.out.printf("\t%-17S> %33s\n", part.getName(), ">€" + df.format(part.getPrice()));
            case"individual" -> System.out.printf("%-20S %5s %15s\n",part.getName(),"(essentieel)","prijs: "+df.format(part.getPrice()));
        }
    }
    public void MaakOpBoot(Boat boat){
        System.out.printf("Type: %-17S Naam: %-15s startprijs: %10s\n", boat.getType(),boat.getName(),"€"+boat.getBasePrice());
    }

}
class Boat{
    Scanner scanner = new Scanner(System.in);
    private final String type;
    private final String name;
    private final String serialNumber;
    private final double basePrice;
    public ArrayList<Part> selectedParts = new ArrayList<>();

    Boat(String type, String name, String serialNumber, double basePrice){
        this.type = type;
        this.name = name;
        this.serialNumber = serialNumber;
        this.basePrice = basePrice;
    }
    public double getBasePrice() {
        return basePrice;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void addPart(ArrayList<Part> parts){
        System.out.print("Welke onderdeel wilt u toevoegen aan de boot? ");
        String optieNaam = scanner.nextLine();
        Part part = SearchInBoat(optieNaam, parts);
        if(part != null) {
            selectedParts.add(part);
            System.out.println("onderdeel '" + part.getName() + "' is succesvol toegevoegd");
        }else{
            System.out.println("er is geen '" + optieNaam + "' in de boot gevonden");
        }

    }

    public void RemovePart(ArrayList<Part> parts){
        System.out.print("Welke onderdeel wilt u verwijderen? ");
        String optieNaam = scanner.nextLine();
        Part part = SearchInBoat(optieNaam, parts);
        if(part != null) {
            selectedParts.remove(part);
            System.out.println("onderdeel '" + part.getName() + "' is succesvol verwijdert");
        }else{
            System.out.println("er is geen '" + optieNaam + "' in de boot gevonden");
        }
    }

    public Part SearchInBoat(String input, ArrayList<Part> parts){
        if(parts!=null) {
            for (Part part : parts) {
                if (part.getName().equalsIgnoreCase(input)) {
                    return part;
                }
            }
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Type: %s\n", this.type));
        sb.append(String.format("Naam: %s\n", this.name));
        sb.append(String.format("Serienummer: %s\n", this.serialNumber));
        sb.append(String.format("Basiskosten: €%.2f\n", this.basePrice));
        if(this.selectedParts.size() > 0) {
            sb.append("Geselecteerde onderdelen:\n");
            for (Part part : this.selectedParts) {
                sb.append(String.format("- %s (€%.2f)\n", part.getName(), part.getPrice()));
            }
        }
        sb.append(String.format("Totaalkosten: €%.2f\n", this.totalPrice()));
        return sb.toString();
    }


    public double totalPrice(){
        double totalprice =this.basePrice;
        if(this.selectedParts != null) {
            for (Part part : selectedParts) {
                totalprice += (part.getPrice() *((100-part.getEcoDiscount())/100));
            }
        }
        return totalprice;
    }
}
class boatList extends MaakOp{
    Scanner scanner = new Scanner(System.in);
    public ArrayList<Boat> boats = new ArrayList<>();

    Boat brabus = new Boat("speedboat","brabus" ,"qpjwswu2", 100001.0001 );
    Boat lamboat = new Boat("speedboat", "lamboat", "w1qrz6",250000.0002);
    Boat seabaru = new Boat("raceboat","seabaru","ql0p7za",1000000.00004);

    public boatList(){
        boats.add(brabus);
        boats.add(lamboat);
        boats.add(seabaru);
    }

    public void createBoat() {
        System.out.println("wat voor type boot is het? ");
        String type = scanner.nextLine();
        System.out.println("wat is de naam van de boot? ");
        String name = scanner.nextLine();
        System.out.println("wat is het serienummer? ");
        String serialnumber = scanner.nextLine();
        System.out.println("wat is de basis prijs? ");
        double basePrice = scanner.nextDouble();
        Boat boat = new Boat(type,name,serialnumber, basePrice);
        boats.add(boat);
    }

    public void displayBoats(){
        System.out.println("huidig beschikbare boten: ");
        for(Boat boat: boats){
            MaakOpBoot(boat);

        }
    }

    public Boat selectBoat(String boatName){
        for(Boat boat: boats){
            if(boatName.equalsIgnoreCase(boat.getName())){
                return boat;
            }
        }
        return null;
    }

}

record OnthoudenVanNumbers(double totaalzonderkorting, double totaalmetkorting, double korting) {
}

/* this is a program that produces quotations complying to the conditions of the client "bedrijf 42"
it should run in a while loop, and we intend to work with the basis of a template */
public class Main {
    public static void main(String[] args) {
        shell shell = new shell();
        shell.run();

        }
    }