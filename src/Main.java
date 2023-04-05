import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;

class Option{

    private String name;
    private double price;
    private boolean essential;
    private String description;

    Option(String name, double price, boolean essential ,String description){
        this.name = name;
        this.price = price;
        this.essential = essential;
        this.description = description;
    }
    Option(String name, double price,boolean essential){
        this.name = name;
        this.price = price;
        this.essential = essential;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice(){
        return this.price;
    }
    public String getName() {
        return this.name;
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


    public void displayOptions() {
        System.out.println("Essential Options:");
        for (Option option : essentialOptions) {
            System.out.printf("\t%-17S> %15s\n", option.getName(),"€"+df.format(option.getPrice()));
        }

        System.out.println("\nExtra Options:");
        for (Option option : extraOptions) {
            System.out.printf("\t%-17S> %15s\n", option.getName(),"€"+df.format(option.getPrice()));
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

}

class shell{
    Scanner scanner = new Scanner(System.in);
    boat boat = new boat();
    OptionList optionList = new OptionList();


    public quote createQuote(){
        boolean shell = true;
        Klant klant = new Klant();
        System.out.print("customer type ");
        String customerType = scanner.nextLine().strip();
        System.out.print("date? (dd-mm-yy) ");
        String date = scanner.nextLine();
        System.out.print("ordernummer? ");
        String orderNumber = scanner.nextLine();

        quote quote = new quote(klant, date, orderNumber);
        quote.setQuoteDetails();
        while(shell) {
            String input = scanner.nextLine().strip();
            switch (input) {
                case "create" -> {
                    Option optiontest = this.optionList.createOption();

                }
                case "add" -> {     // for adding parts to the ship being built

                }
                case "list" -> {
                    optionList.displayOptions();

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
        return quote;
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
    private double milieuKorting;

    quote(){}


    quote(Klant klant, String date, String orderNumber){
        this.klant = klant;
        this.date = date;
        this.orderNumber = orderNumber;
    }


    public void setQuoteDetails(){
        System.out.print("Enter the base price of the boat: ");
        this.bootPrijs = scanner.nextDouble();
        System.out.print("Enter the environmental discount in %: ");
        this.milieuKorting = scanner.nextDouble();
        System.out.print("Enter the VAT-percentage: ");
        this.btwPercentage = scanner.nextDouble();
        System.out.print("Enter the transportation cost: ");
        this.transportKosten = scanner.nextDouble();
    }
    public double calculateTotal(){
        double vatAmount = this.bootPrijs*this.btwPercentage/100;
        double enviromentalDiscount = this.bootPrijs*this.milieuKorting/100;
        this.totaalprijs = bootPrijs+enviromentalDiscount+vatAmount+this.transportKosten;
        return this.totaalprijs;
    }

    public void printQuote(){
        System.out.println("de volgende offerte is een simpele opmaak voor een boot");
        System.out.println("dit is niet per se een definitieve versie\n");

        opmaak.tekstOpmaken("clientname: ", klant.getNaam());

        System.out.println("\nPrice quotation for the base off the boat");
        opmaak.PrijzenOpmaken("Boat frame price:", this.bootPrijs);
        opmaak.PrijzenOpmaken("Environmental Discount (" + this.milieuKorting + "%): ", (this.bootPrijs * this.milieuKorting / 100));
        opmaak.PrijzenOpmaken("VAT (" + Double.toString(this.btwPercentage) + "%):" , (this.bootPrijs * this.btwPercentage / 100));
        opmaak.PrijzenOpmaken("Transport costs:" , this.transportKosten);
        opmaak.PrijzenOpmaken("Total Discount (" + Double.toString(this.milieuKorting) + "%):" , (this.bootPrijs * this.milieuKorting / 100));
        opmaak.PrijzenOpmaken("\nTotal Price: " , this.calculateTotal());
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
        quote quote = new quote();
        while (run) {
            String input = scanner.nextLine(); //first version of inputting into console
            //switch case for a bar-bones version of commands and results, classes and methods have yet to be added.
            switch (input) {
                case "exit":
                    run = false;
                    break;
                case "print":
                    quote.printQuote();
                    break;
                case "finalize":
                    System.out.println("printing and storing quota before shutting down");
                    run = false;
                    break;
                case "create":

                    System.out.println("creating quote");
                    quote = shell.createQuote();
                    break;
                default:
                    System.out.println("please choose an available option");

            }

        }
    }
}