import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;

class Option {
    private String name;
    private double price;
    private boolean essential;
    private String description;
    private double discount;

    Option(String name, double price, boolean essential, String description) {
        this.name = name;
        this.price = price;
        this.essential = essential;
        this.description = description;
        this.discount = 0.0;
    }
    Option(String name, double price, boolean essential) {
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
    private ArrayList<Option> Options;

    Option Hull = new Option("hull", 2.0,true);
    Option HullFrame = new Option("hull frame", 2.0,true);
    Option deck = new Option("deck", 2.0,true);
    Option cabin = new Option("cabin", 2.0,true);
    Option LifeBuoys = new Option("life buoys", 2.0,false);
    Option radio = new Option("radio", 2.0,false);
    Option radars = new Option("radars", 2.0,false);
    Option towerCranes = new Option("tower cranes", 2.0,false);
    Option flagDecor = new Option("flags decoration", 2.0,false);

    public PartList() {

       Options = new ArrayList<>();
       Options.add(Hull);
       Options.add(HullFrame);
       Options.add(deck);
       Options.add(cabin);

        Options.add(LifeBuoys);
        Options.add(radio);
        Options.add(radars);
        Options.add(towerCranes);
        Options.add(flagDecor);

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

    public ArrayList<Option> getOptions(){
        return Options;
    }

    public void createOption() {
        System.out.print("Optienaam? ");
        String name = scanner.nextLine().strip();
        System.out.print("Prijs? ");
        double price = scanner.nextDouble();
        scanner.nextLine(); //to prevent nextdouble from eating;
        boolean essential = essentialOrNot();
        Option option = new Option(name, price, essential);
        Options.add(option);
    }



    public void displayOptions() {
        System.out.println("essentiële opties:");
        for(Option option: Options)
            if(option.getEssential()){
                MaakOpOnderdeel(option,"lijst");
            }
        System.out.println("extra opties:");
        for(Option option: Options){
            if(!option.getEssential()){
                MaakOpOnderdeel(option,"lijst");
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
        for (Option option : Options) {
            if (option.getName().equalsIgnoreCase(name)) {
                System.out.print("Voer het nieuwe percentage in: ");
                double discount = scanner.nextDouble();
                scanner.nextLine();
                option.setEcoDiscount(discount);
                System.out.println("Milieukorting veranderd!");
                return;
            }
        }
        for (Option option : Options) {
            if (option.getName().equalsIgnoreCase(name)) {
                System.out.print("Voer het nieuwe percentage in: ");
                double ecoDiscount = scanner.nextDouble();
                scanner.nextLine();
                option.setEcoDiscount(ecoDiscount);
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

        System.out.println("voor welk klantentype wilt u een offerte maken?");
        System.out.println("1. Bedrijf, 2. Overheid, 3. Particulier, 4. Nieuw klantentype");
        String klantentypeNummber = scanner.nextLine();
        Klant klant = new Klant();
        if(klantentypeNummber.equals("1")){
            Bedrijf bedrijf = new Bedrijf("Bedrijf");

            klant.setKlantentype(bedrijf);
        }
        if(klantentypeNummber.equals("2")){
            Overheid overheid = new Overheid("Overheid");

            klant.setKlantentype(overheid);
        }
        if(klantentypeNummber.equals("3")){
            Particulier particulier = new Particulier("Particulier");

            klant.setKlantentype(particulier);
        }
        if(klantentypeNummber.equals("4")){ // deze optie werkt niet goed ik ga dit fixen
            NieuwKlantentype nieuwKlantentype = NieuwKlantentype.nieuwklantentype();

            klant.setKlantentype(nieuwKlantentype);
        }
        Date date = new Date();
        System.out.print("Order nummer? ");
        String orderNumber = scanner.nextLine();
        ArrayList<Option> preselectedparts = quote.partList();
        quote quote = new quote(klant, date, orderNumber,preselectedparts);
        quote.setQuoteDetails();
        return quote;
    }




    public void run(){
        boolean shell = true;
        while(shell) {
            String input = scanner.nextLine().strip();
            switch (input) {
                case "setup" ->{
                    quote = createQuote();
                }
                case "print" ->{
                    quote.printQuote();
                }
                case "create" -> {
                    PartList.createOption();
                }
                case "add" -> {     // for adding parts to the ship being built
                    quote.addOption(PartList.getOptions());
                }
                case "discount" -> {
                    PartList.setOptionDiscount();
                }
                case "list" -> {
                    PartList.displayOptions();
                }
                case "finalize" ->{
                    System.out.println("finalizing before shutting down");
                    shell = false;
                }
                case "exit" -> {
                    shell = false;
                }
                case "help" -> {
                    System.out.println("hier komt een lijst met termen en wellicht een beschrijving");
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

    public Klant(){
        setNaam();
    }
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
        System.out.print("Klant naaam: ");
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

    NieuwKlantentype(String naam){
        super(naam);
    }
    @Override
    public void setHoeveelheidkorting(double hoeveelheidkorting) {
        super.setHoeveelheidkorting(hoeveelheidkorting);
    }
    public static NieuwKlantentype nieuwklantentype(){ //dit werkt nog niet goed fix ik volgende keer
        NieuwKlantentype nieuwKlantentype = new NieuwKlantentype("Nieuw");
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
        System.out.printf("%12s\n",variable);
    }
    public void PrijzenOpmaken(String input, double getal){
        System.out.printf("%-40s %15s\n",input,"€"+df.format(getal));

    }
    public void MaakOpOnderdeel(Option option, String type){
        switch(type) {
            case "list" -> {
                System.out.printf("\t%-17S> %33s\n", option.getName(), ">€" + df.format(option.getPrice()));
            }
            case"individual" ->{
                System.out.printf("%-20S %5s %15s\n",option.getName(),"(essentieel)","prijs: "+df.format(option.getPrice()));
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
    private ArrayList<Option> selectedParts = new ArrayList<>();

    quote(){}


    quote(Klant klant, Date date, String orderNumber, ArrayList<Option> preSelectedParts){
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
        System.out.print("Voer de transportie kosten in : ");
        this.transportKosten = scanner.nextDouble();
        scanner.nextLine();
    }
    public double calculateTotal(){
        double vatAmount = this.bootPrijs*this.btwPercentage/100;
        this.totaalprijs = bootPrijs+vatAmount+this.transportKosten;
        return this.totaalprijs;
    }

    public void printQuote(){
        System.out.println("de volgende offerte is een simpele opmaak voor een boot");
        System.out.println("dit is niet per se een definitieve versie\n");

        tekstOpmaken("clientname: ", klant.getNaam());
        tekstOpmaken("ordernummer: ",getOrderNumber());
        System.out.println("klantentype: " + klant.getKlantentype().getNaam()); //dit is niet juiste formaat maar ik weet niet goed hoe je hebt gedaan hamza
        //misschien kan je het opknappen en ook nog klant.getKlantentype().getKorting() hierbij zetten ergens

        System.out.println("\nofferte basis prijs van een boot");
        PrijzenOpmaken("Boat frame price:", this.bootPrijs);
        for(Option option: selectedParts){
            MaakOpOnderdeel(option,"list");
        }
        PrijzenOpmaken("Transport kosten:" , this.transportKosten);
        PrijzenOpmaken("BTW (" + this.btwPercentage + "%):" , (this.bootPrijs * this.btwPercentage / 100));
        PrijzenOpmaken("Totale prijs:" , this.calculateTotal());
    }

    public ArrayList<Option> partList(){
        return selectedParts;
    }

    public void addOption(ArrayList<Option> options){
        System.out.print("Welke onderdeel wilt u toevoegen aan de boot? ");
        String optieNaam = scanner.nextLine();
        for(Option option : options){
            if (option.getName().equalsIgnoreCase(optieNaam)){
                selectedParts.add(option);
                System.out.println("onderdeel " + option.getName() + " is succesvol toegevoegd");
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
    private ArrayList<Option> parts;

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

/* this is a program that produces quotations complying to the conditions of the client "bedrijf 42"
it should run in a while loop, and we intend to work with the basis of a template */


public class Main {
    public static void main(String[] args) {
        shell shell = new shell();
        shell.run();

        }
    }
