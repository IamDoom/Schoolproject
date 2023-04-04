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
        System.out.print("Is deze optie essentieel? ");
        boolean essential = scanner.nextBoolean();

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
            System.out.printf("\t%-17S> %15s\n", option.getName(),"€"+option.getPrice());
        }

        System.out.println("\nExtra Options:");
        for (Option option : extraOptions) {
            System.out.printf("\t%-17S> %15s\n", option.getName(),"€"+option.getPrice());
        }
    }
}

class shell{
    Scanner scanner = new Scanner(System.in);
    boat boat = new boat();
    OptionList optionList = new OptionList();


    public quote createQuote(){
        boolean shell = true;
        System.out.print("client Name? ");
        String clientName = scanner.nextLine().strip();
        Klant klant = new Klant(clientName);
        System.out.print("customer type ");
        String customerType = scanner.nextLine().strip();
        System.out.print("date? (dd-mm-yy) ");
        String date = scanner.nextLine();
        System.out.print("ordernummer? ");
        String orderNumber = scanner.nextLine();

        quote quote = new quote(klant, date, orderNumber);
        quote.setQuoteDetails();
        String input = scanner.nextLine().strip();
        while(shell) {
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
                    return quote;
                }
            }
        }
        System.out.println("Voeg nieuwe opties toe? (ja/nee)");
        String answer = scanner.nextLine();
        while (answer.equalsIgnoreCase("ja")) {
            Option option = this.optionList.createOption();
            option.setDescription("Beschrijving van " + option.getName());
            System.out.println("Voeg nog een optie toe? (ja/nee)");
            answer = scanner.nextLine();
        }
        return quote;
    }
}
class Klant{
    private String naam;
    private Klantentype klantentype;
    public Klant(String name){
        this.naam = naam;
    }
    public Klant(String name, Klantentype klantentype){
        this(name);
        this.klantentype = klantentype;
    }


    public String getNaam() {
        return naam;
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
    public void printOfferte(){

    }
    public void MaakOp(){

    }
}



class quote{
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
        double total = bootPrijs+enviromentalDiscount+vatAmount+this.transportKosten;

        return total;
    }

    public void printQuote(){
        DecimalFormat df = new DecimalFormat("#.00");

        System.out.println("Price quotation for the base off the boat:");
        System.out.println("Boat frame price: €" + df.format(this.bootPrijs));
        System.out.println("Environmental Discount (" + this.milieuKorting + "%):€ "+ df.format(this.bootPrijs * this.milieuKorting / 100));
        System.out.println("VAT (" + this.btwPercentage + "%): €" + df.format(this.bootPrijs * this.btwPercentage / 100));
        System.out.println("Transport costs: €" + df.format(this.transportKosten));
        System.out.println("Total Discount (" + this.milieuKorting + "%):€ " + df.format(this.bootPrijs * this.milieuKorting / 100));
        System.out.println("Total Price: €" + df.format(this.totaalprijs));
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
        //Main main = new Main();

        while (run) {
            String input = scanner.nextLine(); //first version of inputting into console
            //switch case for a bar-bones version of commands and results, classes and methods have yet to be added.
            switch (input) {
                case "exit":
                    run = false;
                    break;
                case "print":
                    System.out.println("printing current quota");
                    break;
                case "finalize":
                    System.out.println("printing and storing quota before shutting down");
                    run = false;
                    break;
                case "create":
                    shell shell = new shell();
                    System.out.println("creating quote");
                    quote quote = shell.createQuote();
                    break;
                default:
                    System.out.println("please choose an available option");

            }

        }
    }
}