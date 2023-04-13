
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;

class Part {
    private String name;
    private double price;
    private boolean essential;
    private String description;
    private double discount;

    Part(String name, double price, boolean essential, String description) {
        this.name = name;
        this.price = price;
        this.essential = essential;
        this.description = description;
        this.discount = 0.0;
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
    public void setEcoDiscount(double discount) {
        this.discount = discount;
    }
    public double getPrice() {
        return this.price ;
    }
    public String getName() {
        return this.name;
    }
    public double applyDiscount(){
        double inverted = 100-this.discount;
        return ((inverted/100)*price);
    }

    public double getDiscount() {
        return discount;
    }

    public boolean getEssential() {
        return this.essential;
    }
}

class PartList extends MaakOp {
    Scanner scanner = new Scanner(System.in);
    DecimalFormat df = new DecimalFormat("#0.00");
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
}
class shell{
    Scanner scanner = new Scanner(System.in);
    PartList PartList = new PartList();
    quote quote = new quote();

    public quote createQuote() {
        Klant klant = new Klant();
        System.out.println("voor welk klantentype wilt u een offerte maken?");
        System.out.println("1. Bedrijf, 2. Overheid, 3. Particulier, 4. Nieuw klantentype");
        String klantentypeNummber = scanner.nextLine().strip();

        switch (klantentypeNummber){
            case "1" ->{
                Bedrijf bedrijf = new Bedrijf("Bedrijf");
                klant.setKlantentype(bedrijf);
            }
            case "2" ->{
                Overheid overheid = new Overheid("Overheid");
                klant.setKlantentype(overheid);
            }
            case "3"->{
                Particulier particulier = new Particulier("Particulier");
                klant.setKlantentype(particulier);
            }
            case "4" ->{
                System.out.println("wat is de naam van het nieuwe klantentype?");
                String naamklantentype = scanner.nextLine();
                System.out.println("wat is de hoeveelheid korting voor dit klantentype?");
                double korting = scanner.nextDouble();
                NieuwKlantentype nieuwKlantentype = new NieuwKlantentype(naamklantentype, korting);
                klant.setKlantentype(nieuwKlantentype);
                scanner.nextLine();
            }

        }
        Date date = new Date();
        System.out.print("Order nummer? ");
        String orderNumber = scanner.nextLine();
        ArrayList<Part> preselectedparts = quote.partList();
        quote quote = new quote(klant, date, orderNumber,preselectedparts);
        quote.setQuoteDetails();
        return quote;
    }

    public void run(){
        boolean shell = true;
        while(shell) {
            String input = scanner.nextLine().strip();
            switch (input) {
                case "setup" -> quote = createQuote();

                case "print" -> quote.printQuote();

                case "create" -> PartList.createPart();

                case "add" -> quote.addPart(PartList.getParts());

                case "discount" -> PartList.setOptionDiscount();

                case "list" -> PartList.displayParts();

                case "finalize" ->{
                    System.out.println("finalizing before shutting down");
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
                }

                default -> System.out.println("please use a valid input use 'help' for help");
            }
        }
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
    @Override
    public void setHoeveelheidkorting(double hoeveelheidkorting) {
        super.setHoeveelheidkorting(hoeveelheidkorting);
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
    DecimalFormat df = new DecimalFormat("#0.00");
    public void tekstOpmaken(String input, String variable){
        System.out.print(input);
        System.out.printf("%20s\n",variable);
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

}
class quote extends MaakOp{
    Scanner scanner = new Scanner(System.in);

    private Klant klant;
    private Date date;
    private String orderNumber;
    private boat boat;
    private double bootPrijs;
    private double btwPercentage;
    private double transportKosten;
    private double totaalprijs;
    private ArrayList<Part> selectedParts = new ArrayList<>();

    quote(){}


    quote(Klant klant, Date date, String orderNumber, ArrayList<Part> preSelectedParts){
        this.klant = klant;
        this.date = date;
        this.orderNumber = orderNumber;
        this.selectedParts = preSelectedParts;
    }

    public void setQuoteDetails(){
        System.out.print("Voer de basis prijs van een boot in: ");
        this.bootPrijs = scanner.nextDouble();
        System.out.print("Voer de BTW-percentage in : ");
        this.btwPercentage = scanner.nextDouble();
        System.out.print("Voer de transport kosten in : ");
        this.transportKosten = scanner.nextDouble();
        scanner.nextLine();
    }
    public double calculateTotal(){
        double vatAmount = this.bootPrijs*this.btwPercentage/100;
        this.totaalprijs = bootPrijs+vatAmount+this.transportKosten;
        return this.totaalprijs;
    }
    public OnthoudenVanNumbers calculateTotalOfParts(){ //je kunt dit gebruiken als berekening van de prijs van de onderdelen
        double totaalzonderkorting = 0; // maak eerst een nieuw Onthoudenvannummers object aan en stel hem gelijk aan je quote.calculateTotalOfParts
        double totaalmetkorting = 0; //dan kun je getter gebruiken om de gegeven te accessen
        double korting = 0;
        for(Part selectedpart : selectedParts) {
            totaalzonderkorting += selectedpart.getPrice();
            if (selectedpart.getDiscount() != 0) {
                totaalmetkorting += selectedpart.getPrice() * selectedpart.getDiscount();

            }
            else{
                totaalmetkorting += selectedpart.getPrice();

            }

        }
        korting = totaalzonderkorting - totaalmetkorting;
        OnthoudenVanNumbers onthoudenVanNumbers = new OnthoudenVanNumbers(totaalzonderkorting, totaalmetkorting, korting);
        return onthoudenVanNumbers;

    }

    public void printQuote(){

        System.out.println("de volgende offerte is een simpele opmaak voor een boot");
        System.out.println("dit is niet per se een definitieve versie\n");

        tekstOpmaken("clientname: ", klant.getNaam());
        tekstOpmaken("ordernummer: ",getOrderNumber());
        tekstOpmaken("klantentype: ", klant.getKlantentype().getNaam());

        System.out.println("\nofferte basis prijs van een boot");
        PrijzenOpmaken("Boat frame price:", this.bootPrijs);
        for(Part part: selectedParts){
            MaakOpOnderdeel(part,"list");
        }
        PrijzenOpmaken("Transport kosten:" , this.transportKosten);
        PrijzenOpmaken("BTW (" + this.btwPercentage + "%):" , (this.bootPrijs * this.btwPercentage / 100));
        PrijzenOpmaken("Totale prijs:" , this.calculateTotal());
    }
    public ArrayList<Part> partList(){
        return selectedParts;
    }

    public void addPart(ArrayList<Part> parts){
        System.out.print("Welke onderdeel wilt u toevoegen aan de boot? ");
        String optieNaam = scanner.nextLine();
        for(Part part : parts){
            if (part.getName().equalsIgnoreCase(optieNaam)){
                selectedParts.add(part);
                System.out.println("onderdeel " + part.getName() + " is succesvol toegevoegd");
            }

        }
    }
    public void RemovePart(ArrayList<Part> parts){
        System.out.print("Welke onderdeel wilt u verwijderen? ");
        String optieNaam = scanner.nextLine();
        for(Part part : parts){
            if (part.getName().equalsIgnoreCase(optieNaam)){
                selectedParts.remove(part);
                System.out.println("onderdeel " + part.getName() + " is succesvol verwijderd");
            }

        }
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

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    public boat getBoat() {
        return boat;
    }
    public void setBoat(boat boat) {
        this.boat = boat;
    }
}

class boatList{
}

class boat{
    private String type;
    private String name;
    private String serialNumber;
    private String customerType;
    private ArrayList<Part> parts;

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
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
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