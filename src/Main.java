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

    public boolean isEssential() {
        return essential;
    }
}


class OptionList {
    Scanner scanner = new Scanner(System.in);
    DecimalFormat df = new DecimalFormat("#0.00");

    private ArrayList<Option> essentialOptions;
    private ArrayList<Option> extraOptions;

    Option Hull = new Option("hull", 2.0,true);
    Option HullFrame = new Option("hull frame", 2.0,true);
    Option deck = new Option("deck", 2.0,true);
    Option cabin = new Option("cabin", 2.0,true);
    Option LifeBuoys = new Option("life buoys", 2.0,true);
    Option radio = new Option("radio", 2.0,true);
    Option radars = new Option("radars", 2.0,true);
    Option towerCranes = new Option("tower cranes", 2.0,true);
    Option flagDecor = new Option("flags decoration", 2.0,true);

    public OptionList() {

        essentialOptions = new ArrayList<Option>();
        essentialOptions.add(Hull);
        essentialOptions.add(HullFrame);
        essentialOptions.add(deck);
        essentialOptions.add(cabin);

        extraOptions = new ArrayList<Option>();
        extraOptions.add(LifeBuoys);
        extraOptions.add(radio);
        extraOptions.add(radars);
        extraOptions.add(towerCranes);
        extraOptions.add(flagDecor);

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

    public Option createOption() {
        System.out.print("Optienaam? ");
        String name = scanner.nextLine().strip();
        System.out.print("Prijs? ");
        double price = scanner.nextDouble();
        scanner.nextLine(); //to prevent nextdouble from eating;
        boolean essential = essentialOrNot();
        Option option = new Option(name, price, essential);
        if (essential) {
            essentialOptions.add(option);
        } else {
            extraOptions.add(option);
        }
        return option;
    }

    public Option addOption(){
        System.out.print("Welke onderdeel wilt u toevoegen aan de boot? ");
        String optieNaam = scanner.nextLine();
        for(Option option : essentialOptions){
                if (option.getName().equals(optieNaam)){
                    System.out.println("onderdeel" + option.getName() + "is succesvol toegevoegd");
                    return option;
                }

        }
        for(Option option : extraOptions){
                if (option.getName().equals(optieNaam)){
                    System.out.println("onderdeel " + option.getName() + " is  succesvol toegevoegd");
                    return option;
                }

        }
        return null;
    }






    public void displayOptions() {
        System.out.println("Essential Options:");
        for (Option option : essentialOptions) {
            System.out.printf("\t%-17S> %15s\n", option.getName(), "€" + df.format(option.getPrice()));
        }
        System.out.println("\nExtra Options:");
        for (Option option : extraOptions) {
            System.out.printf("\t%-17S> %15s\n", option.getName(), "€" + df.format(option.getPrice()));
        }
    }

    private boolean essentialOrNot() {

        while (true) {
            System.out.print("Is deze optie essentieel? ");
            String input = scanner.nextLine().strip().toLowerCase();
            switch (input) {
                case "yes" -> {
                    return true;
                }
                case "no" -> {
                    return false;
                }
                default -> System.out.println("illegal input please enter 'yes' or 'no'");
            }
        }
    }
    public void setOptionDiscount() {
        System.out.print("To which option do you want to make a change? ");
        String name = scanner.nextLine().strip();
        for (Option option : essentialOptions) {
            if (option.getName().equalsIgnoreCase(name)) {
                System.out.print("New eco-discount? ");
                double discount = scanner.nextDouble();
                scanner.nextLine();
                option.setEcoDiscount(discount);
                System.out.println("Eco-discount changed!");
                return;
            }
        }
        for (Option option : extraOptions) {
            if (option.getName().equalsIgnoreCase(name)) {
                System.out.print("New eco-discount? ");
                double ecoDiscount = scanner.nextDouble();
                scanner.nextLine();
                option.setEcoDiscount(ecoDiscount);
                System.out.println("Eco-discount changed!");
                return;
            }
        }
        System.out.println("Option unavailable!");
    }


}

class shell{
    Scanner scanner = new Scanner(System.in);
    boat boat = new boat();
    OptionList optionList = new OptionList();
    quote quote = new quote();
    ArrayList<Option> optielijst = new ArrayList<Option>();

    public quote createQuote() {
        Klant klant = new Klant();
        System.out.print("customer type ");
        String customerType = scanner.nextLine().strip();
        System.out.print("date? (dd-mm-yy) ");
        String date = scanner.nextLine();
        System.out.print("order number? ");
        String orderNumber = scanner.nextLine();
        quote quote = new quote(klant, date, orderNumber);
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
                    Option optiontest = this.optionList.createOption();
                }
                case "add" -> {     // for adding parts to the ship being built
                    Option optiontest1 = optionList.addOption();
                    if(optiontest1 != null) {
                        optielijst.add(optiontest1);
                    }
                }
                case "discount" -> {
                    optionList.setOptionDiscount();
                }
                case "list" -> {
                    optionList.displayOptions();
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
        System.out.print("Client name: ");
        this.naam = scanner.nextLine().strip();
    }

    public Klantentype getKlantentype() {
        return klantentype;
    }
}
abstract class Klantentype{
    private String naam;
    private double hoeveelheidkorting;


    public Klantentype(String naam) {
        this.naam = naam;
    }

    public String getNaam(){
        return naam;
    }
    public double getKorting(){
        return hoeveelheidkorting;
    }

}
class Particulier extends Klantentype{


    public Particulier(String naam) {
        super(naam);
    }
}

class Bedrijf extends Klantentype{


    public Bedrijf(String naam) {
        super(naam);
    }
}

class Overheid extends Klantentype{


    public Overheid(String naam) {
        super(naam);
    }
}

class MaakOp{
    DecimalFormat df = new DecimalFormat("#0.00");
    public void tekstOpmaken(String input, String variable){
        System.out.print(input);
        System.out.printf("%12s\n",variable);
    }
    public void PrijzenOpmaken(String input, double getal){
        System.out.printf("%-40s %15s\n",input,"€"+df.format(getal));

    }
    public void MaakOpOnderdelen(ArrayList<Option> onderdelenlijst){
        ArrayList<Option> essentieleOnderdelen = new ArrayList<Option>();
        ArrayList<Option> nietessentieleOnderdelen = new ArrayList<Option>();
        for (Option option : onderdelenlijst){
            if (option.isEssential()){
                essentieleOnderdelen.add(option);
            }
            if (!option.isEssential()){
                nietessentieleOnderdelen.add(option);
            }
        }
        System.out.println("De essentiele onderdelen zijn: ");
        System.out.println();
        for (Option option : essentieleOnderdelen){
            System.out.println("naam: " + option.getName() + " | oude prijs " + option.getPrice() + " | hoeveelheid korting: " + option.getDiscount() + " | nieuwe prijs: " + df.format(option.applyDiscount()));
        }
        System.out.println("De extra onderdelen zijn: ");
        System.out.println();
        for (Option option : nietessentieleOnderdelen){
            System.out.println("naam: " + option.getName() + " | oude prijs " + option.getPrice() + " | hoeveelheid korting: " + option.getDiscount() + " | nieuwe prijs: " + df.format(option.applyDiscount()));
        }

    }
}



class quote{
    MaakOp opmaak = new MaakOp();
    Scanner scanner = new Scanner(System.in);

    private Klant klant;
    private String date;
    private String orderNumber;
    private boat boat;
    private double bootPrijs;
    private double btwPercentage;
    private double transportKosten;
    private double totaalprijs;
    private ArrayList<Option> selectedParts = new ArrayList<>();

    quote(){}


    quote(Klant klant, String date, String orderNumber){
        this.klant = klant;
        this.date = date;
        this.orderNumber = orderNumber;
    }


    public void setQuoteDetails(){
        System.out.print("Enter the base price of the boat: ");
        this.bootPrijs = scanner.nextDouble();
        System.out.print("Enter the VAT-percentage: ");
        this.btwPercentage = scanner.nextDouble();
        System.out.print("Enter the transportation cost: ");
        this.transportKosten = scanner.nextDouble();
    }
    public double calculateTotal(){
        double vatAmount = this.bootPrijs*this.btwPercentage/100;
        this.totaalprijs = bootPrijs+vatAmount+this.transportKosten;
        return this.totaalprijs;
    }

    public void printQuote(){
        System.out.println("de volgende offerte is een simpele opmaak voor een boot");
        System.out.println("dit is niet per se een definitieve versie\n");

        opmaak.tekstOpmaken("clientname: ", klant.getNaam());

        System.out.println("\nPrice quotation for the base off the boat");
        opmaak.PrijzenOpmaken("Boat frame price:", this.bootPrijs);
        opmaak.PrijzenOpmaken("VAT (" + Double.toString(this.btwPercentage) + "%):" , (this.bootPrijs * this.btwPercentage / 100));
        opmaak.PrijzenOpmaken("Transport costs:" , this.transportKosten);
        opmaak.PrijzenOpmaken("\nTotal Price:" , this.calculateTotal());
    }

    public void partList(){

    }

    public Klant getKlant() {
        return klant;
    }



    public void setKlant(Klant klant) {
        this.klant = klant;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
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
    private  OptionList optionlist = new OptionList();
    public static void main(String[] args) {
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        shell shell = new shell();
        shell.run();

        }
    }
