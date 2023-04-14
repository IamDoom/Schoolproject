import java.io.*;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;

class Part {
    private String name;
    private double price;
    private boolean essential;
    private String description;
    private double EcoDiscount;

    Part(){}

    Part(String name, double price, boolean essential, String description) {
        this.name = name;
        this.price = price;
        this.essential = essential;
        this.description = description;
        this.EcoDiscount = 0.0;
    }
    Part(String name, double price, boolean essential) {
        this(name, price, essential, null);
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription(){
        return this.description;
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
    public double applyDiscount(){
        double inverted = 100-this.EcoDiscount;
        return ((inverted/100)*price);
    }

    public double getEcoDiscount(){return EcoDiscount;}

    public boolean getEssential() {
        return this.essential;
    }
}

class PartList extends MaakOp {
    Scanner scanner = new Scanner(System.in);
    DecimalFormat df = new DecimalFormat("###,###,##0.000000");
    private ArrayList<Part> Parts;

    Part Hull = new Part("hull", 2.0,true);
    Part HullFrame = new Part("hull frame", 2.0,true);
    Part deck = new Part("deck", 2.0,true);
    Part cabin = new Part("cabin", 2.0,true);
    Part LifeBuoys = new Part("life buoys", 2.0,false);
    Part radio = new Part("radio", 2.0,false);
    Part radars = new Part("radars", 2.0,false);
    Part towerCranes = new Part("tower cranes", 2.0,false);
    Part flagDecor = new Part("flags decoration", 2.0,false);

    public PartList() {

       Parts = new ArrayList<>();
       Parts.add(Hull);
       Parts.add(HullFrame);
       Parts.add(deck);
       Parts.add(cabin);

        Parts.add(LifeBuoys);
        Parts.add(radio);
        Parts.add(radars);
        Parts.add(towerCranes);
        Parts.add(flagDecor);

        Hull.setEcoDiscount(0.1);
        HullFrame.setEcoDiscount(0.1);
        deck.setEcoDiscount(0.1);
        cabin.setEcoDiscount(0.1);
        LifeBuoys.setEcoDiscount(0.1);
        radio.setEcoDiscount(0.1);
        radars.setEcoDiscount(0.1);
        towerCranes.setEcoDiscount(0.1);
        flagDecor.setEcoDiscount(0.1);
    }

    public ArrayList<Part> getParts(){
        return Parts;
    }

    public void createPart() {
        System.out.print("Onderdeel naam? ");
        String name = scanner.nextLine().strip();
        System.out.print("Prijs? ");
        double price = scanner.nextDouble();
        scanner.nextLine(); //to prevent nextdouble from eating;
        boolean essential = essentialOrNot();
        Part option = new Part(name, price, essential);
        Parts.add(option);
    }

    public void deletePart(){
        System.out.print("welk onderdeel wilt u vernietigen?:");
        String name = scanner.nextLine().strip();
        for(Part part: Parts){
            if (name.equalsIgnoreCase(part.getName())) {
                Parts.remove(part);
            }else{
                System.out.println("onderdeel niet gevonden");
            }

        }
    }

    public void displayParts() {
        System.out.println("essentiële opties:");
        for(Part part: Parts)
            if(part.getEssential()){
                MaakOpOnderdeel(part,"list"); //dit moet
            }
        System.out.println("extra opties:");
        for(Part part: Parts){
            if(!part.getEssential()){
                MaakOpOnderdeel(part,"list");
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
    public void setOptionDiscount() {
        System.out.print("Welke optie krijgt een nieuwe milieukorting?");
        String name = scanner.nextLine().strip();
        for (Part part : Parts) {
            if (part.getName().equalsIgnoreCase(name)) {
                System.out.print("Voer het nieuwe percentage in: ");
                double discount = scanner.nextDouble();
                scanner.nextLine();
                part.setEcoDiscount(discount);
                System.out.println("Milieukorting veranderd!");
                return;
            }
        }
        for (Part part : Parts) {
            if (part.getName().equalsIgnoreCase(name)) {
                System.out.print("Voer het nieuwe percentage in: ");
                double ecoDiscount = scanner.nextDouble();
                scanner.nextLine();
                part.setEcoDiscount(ecoDiscount);
                System.out.println("Milieukorting veranderd!");
                return;
            }
        }
        System.out.println("Deze optie is niet beschikbaar!");
    }
    public void selectPart(){

    }
}
class shell{
    Scanner scanner = new Scanner(System.in);
    PartList PartList = new PartList();
    boatList boatList = new boatList();
    quote quote = new quote();
    private Boat boat;


    public void run(){
        boolean shell = true;
        while(shell) {
            String input = scanner.nextLine().strip();
            switch (input.toLowerCase()) {
                case "setup" ->{
                    quote = quote.createQuote();
                    quote.setQuoteDetails();
                }

                case "print" -> {
                    if(quote.getKlant() != null) {
                        quote.printQuote();
                    }else{
                        System.out.println("u moet eerst een offerte maken om te printen");
                    }
                }
                case "create" -> {
                    System.out.println("wat wilt u maken?");
                    input = scanner.nextLine();
                    switch (input.toLowerCase()){
                        case "onderdeel" -> PartList.createPart();
                        case "boot" -> boatList.createBoat();
                        case "exit"->{break;}
                        default -> System.out.println("<onderdeel><boot><exit>");
                    }
                }
                case "add" -> {
                    boat = quote.getBoat();
                    boat.addPart(PartList.getParts());
                }
                case "remove" -> boat.RemovePart(PartList.getParts());

                case "discount" -> PartList.setOptionDiscount();

                case "list" -> PartList.displayParts();

                case "destroy" -> PartList.deletePart();

                case "select" -> PartList.selectPart();

                case "finalize" ->{
                    System.out.println("finalizing before shutting down");
                    quote.printQuote();
                    shell = false;
                }
                case "exit" -> shell = false;

                case "help" -> {
                    System.out.println("<SETUP>    'voor het maken van een offerte'");
                    System.out.println("<PRINT>    'print de offerte zoals die er op het moment uit ziet'");
                    System.out.println("<CREATE>   'voor het aanmaken van een onderdeel'");
                    System.out.println("<ADD>      'voegt een bestaand onderdeel toe'");
                    System.out.println("<LIST>     'toont een lijst van beschikbare onderdelen'");
                    System.out.println("<FINALIZE> 'slaat de offerte op en sluit het programma af'");
                    System.out.println("<EXIT>     'sluit het programma af'");
                    System.out.println("<DISCOUNT> 'voegt korting toe aan een onderdeel");
                    System.out.println("<DESTROY>  'vernietigd een beschikbaar onderdeel'");
                    System.out.println("<SELECT>   'selecteerd een specifiek onderdeel'");
                }

                default -> System.out.println("please use a valid input use 'help' for help");
            }
        }
    }
}

class quote extends MaakOp{
    Scanner scanner = new Scanner(System.in);
    boatList boatList = new boatList();
    private Klant klant;
    private Date date;
    private String orderNumber;
    private Boat boat;
    private double btwPercentage;
    private double transportKosten;
    private double totaalprijs;

    quote(){}


    quote(Klant klant, Date date){
        this.klant = klant;
        this.date = date;
    }
    public static quote createQuote() {
        Klant klant = new Klant();
        Date date = new Date();
        quote newquote = new quote(klant, date);
        return newquote;
    }

    public void setQuoteDetails(){
        System.out.println("wat wilt u in de offerte invoeren?");
        boolean shell = true;
        while(shell) {
            String input = scanner.nextLine().strip();
            switch (input.toLowerCase()) {
                case "ordernummer" -> setOrderNumber();
                case "klanttype" -> PickCustomer();
                case "boot" -> this.boat = Pickboat();
                case "exit" -> shell = false;
                case "help" -> {
                    System.out.println("<ordernummer> 'zet de ordernummer vast'");
                    System.out.println("<klanttype> 'selecteerd klanttype'");
                    System.out.println("<boot> 'selecteerd gewenste boot'");
                    System.out.println("<exit> u keer terug naar het basis menu");
                }
                default -> System.out.println("voer een geldige keuze in, 'help' voor help");

            }
        }
    }

    public void PickCustomer() {
        System.out.println("voor welk klantentype wilt u een offerte maken?");
        System.out.println("'bedrijf' 'overheid' 'particulier' 'nieuw' (nieuw klantentype)");
        String klantentype = scanner.nextLine().strip();

        boolean ValidInput = false;
        while(!ValidInput) {
            if (klantentype.equalsIgnoreCase("BEDRIJF")) {
                Bedrijf bedrijf = new Bedrijf("Bedrijf");
                klant.setKlantentype(bedrijf);
                ValidInput = true;
            }
            else if (klantentype.equalsIgnoreCase("OVERHEID")) {
                Overheid overheid = new Overheid("Overheid");
                klant.setKlantentype(overheid);
                ValidInput = true;
            }
            else if (klantentype.equalsIgnoreCase("PARTICULIER")) {
                Particulier particulier = new Particulier("Particulier");
                klant.setKlantentype(particulier);
                ValidInput = true;
            }
            else if (klantentype.equalsIgnoreCase("NIEUW")) {
                System.out.println("wat is de naam van het nieuwe klantentype?");
                String naamklantentype = scanner.nextLine();
                System.out.println("wat is de hoeveelheid korting voor dit klantentype?");
                double korting = scanner.nextDouble();
                NieuwKlantentype nieuwKlantentype = new NieuwKlantentype(naamklantentype, korting);
                klant.setKlantentype(nieuwKlantentype);
                scanner.nextLine();
                ValidInput = true;
            }
            else {
                System.out.println("kies een geldige optie\n'bedrijf' 'overheid' 'particulier' 'nieuw'");
                break;
            }
        }
    }
    public Boat Pickboat(){
        boatList.displayBoats();
        System.out.println("welke boot(naam) wilt u?");
        String Name = scanner.nextLine();
        return boatList.selectBoat(Name);
    }


    public void printQuote(){
        OnthoudenVanNumbers onthoudenVanNumbers = this.calculateTotalOfParts();
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
            }
        }else{tekstOpmaken("onderdelen:", "<niets geselecteerd>");}
        PrijzenOpmaken("Transport kosten:" , this.transportKosten);
        System.out.println();

        PrijzenOpmaken("Totale kosten onderdelen:", onthoudenVanNumbers.totaalzonderkorting());
        PrijzenOpmaken("Totale kosten onderdelen met korting:", onthoudenVanNumbers.totaalmetkorting());
        PrijzenOpmaken("Totale hoeveelheid korting:", onthoudenVanNumbers.korting());
        PrijzenOpmaken("BTW (" + this.btwPercentage + "%):" , (boat.getBasePrice() * this.btwPercentage / 100));
        PrijzenOpmaken("Totale prijs:" , this.calculateTotal());
    }
    public double calculateTotal(){
        double vatAmount = boat.totalPrice()*this.btwPercentage/100;
        this.totaalprijs = boat.totalPrice()+vatAmount+this.transportKosten;
        return this.totaalprijs;
    }


    public OnthoudenVanNumbers calculateTotalOfParts(){ //je kunt dit gebruiken als berekening van de prijs van de onderdelen
        double totaalzonderkorting = 0; // maak eerst een nieuw Onthoudenvannummers object aan en stel hem gelijk aan je quote.calculateTotalOfParts
        double totaalmetkorting = 0; //dan kun je getter gebruiken om de gegeven te accessen
        double korting;
        for(Part selectedpart : boat.selectedParts) {
            totaalzonderkorting += selectedpart.getPrice();
            if (selectedpart.getEcoDiscount() != 0) {
                totaalmetkorting += selectedpart.getPrice() -(selectedpart.getPrice() * selectedpart.getEcoDiscount());

            }
            else{
                totaalmetkorting += selectedpart.getPrice();

            }

        }
        korting = totaalzonderkorting - totaalmetkorting;
        OnthoudenVanNumbers onthoudenVanNumbers = new OnthoudenVanNumbers(totaalzonderkorting, totaalmetkorting, korting);
        return onthoudenVanNumbers;

    }
    public Klant getKlant() {
        return klant;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
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
        this.klantentype = klantentype;
    }

    public Klantentype getKlantentype() {
        return klantentype;
    }
}
abstract class Klantentype{

    private double hoeveelheidkorting;
    private String Naam;

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

    public void setNaam(String naam){
        this.Naam = naam;
    }
    public double getKorting(){
        return hoeveelheidkorting;
    }

    public void setHoeveelheidkorting(double hoeveelheidkorting) {
        this.hoeveelheidkorting = hoeveelheidkorting;
    }
}
class Particulier extends Klantentype{
    Particulier(String naam){
        super(naam);

    }
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
    public static NieuwKlantentype nieuwklantentype(){ //dit werkt nog niet goed fix ik volgende keer
        NieuwKlantentype nieuwKlantentype = new NieuwKlantentype("Nieuw", 0.0);
        Scanner scanner = new Scanner(System.in);
        System.out.println("wat is de naam van het nieuwe klantentype?");
        String naamklantentype = scanner.nextLine();
        nieuwKlantentype.setNaam(naamklantentype);
        System.out.println("wat is de hoeveelheid korting voor dit klantentype?");
        double korting = scanner.nextDouble();
        nieuwKlantentype.setHoeveelheidkorting(korting);
        return  nieuwKlantentype;
    }
}
abstract class MaakOp{
    DecimalFormat df = new DecimalFormat("###,###,##0.000000");
    public void tekstOpmaken(String input, String variable){

        System.out.printf("%-17s %20s\n",input,variable);
    }
    public void PrijzenOpmaken(String input, double getal){
        System.out.printf("%-40s %15s\n",input,"€"+df.format(getal));

    }
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
    public void MaakOpBoot(Boat boat){
        System.out.printf("Type: %-17S Naam: %-15s startprijs: %10s\n", boat.getType(),boat.getName(),"€"+boat.getBasePrice());
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

class Boat{
    Scanner scanner = new Scanner(System.in);
    private String type;
    private String name;
    private String serialNumber;
    private double basePrice;
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

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public double totalPrice(){
        double totalprice =this.basePrice;
        if(this.selectedParts != null) {
            for (Part part : selectedParts) {
                totalprice += part.getPrice();
            }
        }
        return totalprice;
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