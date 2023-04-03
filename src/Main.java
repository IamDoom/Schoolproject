import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;

class Option{
    private String name;
    private double price;
    private String description;

    Option(String name, double price, String description){
        this.name = name;
        this.price = price;
        this.description = description;
    }
    Option(String name, double price){
        this.name = name;
        this.price = price;
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
    private ArrayList<Option> essentialOptions;
    private ArrayList<Option> extraOptions;

    Option Hull = new Option("hull", 2.0);
    Option HullFrame = new Option("hull frame", 2.0);
    Option deck = new Option("deck", 2.0);
    Option cabin = new Option("cabin", 2.0);
    Option LifeBuoys = new Option("life buoys", 2.0);
    Option radio = new Option("radio", 2.0);
    Option radars = new Option("radars", 2.0);
    Option towerCranes = new Option("tower cranes", 2.0);
    Option flagDecor = new Option("flags decoration", 2.0);
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
    public void addOption(Option option, boolean isEssential) {
        if (isEssential) {
            essentialOptions.add(option);
        } else {
            extraOptions.add(option);
        }
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
    OptionList optionList;

    public shell() {
        this.optionList = new OptionList();
    }

    public quote createQuote(){
        boolean shell = true;
        System.out.println("client Name?");
        String clientName = scanner.nextLine();
        System.out.println("date? (dd-mm-yy)");
        String date = scanner.nextLine();
        System.out.println("ordernummer?");
        String orderNumber = scanner.nextLine();
        quote quote = new quote(clientName, date, orderNumber);
        quote.setQuoteDetails();

        System.out.println("Voeg nieuwe opties toe? (ja/nee)");
        String answer = scanner.nextLine();
        while (answer.equalsIgnoreCase("ja")) {
            System.out.println("Optienaam?");
            String name = scanner.nextLine();
            System.out.println("Prijs?");
            double price = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Is deze optie essentieel? (ja/nee)");
            boolean isEssential = scanner.nextLine().equalsIgnoreCase("ja");
            Option option = new Option(name, price);
            option.setDescription("Beschrijving van " + name);
            optionList.addOption(option, isEssential);
            System.out.println("Voeg nog een optie toe? (ja/nee)");
            answer = scanner.nextLine();
        }

        System.out.println("Voeg nieuwe klanttype toe? (ja/nee)");
        answer = scanner.nextLine();
        while (answer.equalsIgnoreCase("ja")) {
            System.out.println("Klanttype?");
            String customerType = scanner.nextLine();
            boat.setCustomerType(customerType);
            System.out.println("Voeg nog een klanttype toe? (ja/nee)");
            answer = scanner.nextLine();
        }

        return quote;
    }
}


class quote{
    Scanner scanner = new Scanner(System.in);
    private String clientName;
    private String date;
    private String orderNumber;
    private boat boat;
    private double bootPrijs;
    private double btwPercentage;
    private double transportKosten;
    private double totaalprijs;
    private double milieuKorting;

    quote(String clientName,  String date, String orderNumber){
        this.clientName = clientName;
        this.date = date;
        this.orderNumber = orderNumber;
    }

    public void setQuoteDetails(){
        System.out.print("Enter the base price of the boat: ");
        this.bootPrijs = scanner.nextDouble();
        System.out.println("Enter the environmental discount in %: ");
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

    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
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
        shell shell = new shell();
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        Main main = new Main();
        OptionList optionlist = new OptionList();
        optionlist.displayOptions();
        shell.createQuote();

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
                    System.out.println("creating quote");
                    quote quote = shell.createQuote();
                    break;
                default:
                    System.out.println("please choose an available option");

            }

        }
    }
}