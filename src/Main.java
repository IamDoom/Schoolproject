import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;

class option{
    private String name;
    private double price;
    private String description;

    option(String name, double price, String description){
        this.name = name;
        this.price = price;
        this.description = description;
    }
    option(String name, double price){
        this.name = name;
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
class OptionList {
    private ArrayList<String> essentialOptions;
    private ArrayList<String> extraOptions;

    public OptionList() {
        essentialOptions = new ArrayList<String>();
        essentialOptions.add("hull");
        essentialOptions.add("hull frame");
        essentialOptions.add("deck");
        essentialOptions.add("cabin");

        extraOptions = new ArrayList<String>();
        extraOptions.add("lifebuoys");
        extraOptions.add("radios");
        extraOptions.add("radars");
        extraOptions.add("tower cranes");
        extraOptions.add("flags and hull decoration");
    }

    public void displayOptions() {
        System.out.println("Essential Options:");
        for (String option : essentialOptions) {
            System.out.println("- " + option);
        }

        System.out.println("\nExtra Options:");
        for (String option : extraOptions) {
            System.out.println("- " + option);
        }
    }
}

class shell{
    Scanner scanner = new Scanner(System.in);
    public quote createQuote(){
        boolean shell = true;
        System.out.println("client Name?");
        String clientName = scanner.nextLine();
        System.out.println("date? (dd-mm-yy)");
        String date = scanner.nextLine();
        System.out.println("ordernummer?");
        String orderNumber = scanner.nextLine();
        quote quote = new quote(clientName, 0.0, date, orderNumber);

        while(shell){

        }

    //romp, rompframe, dek, kajuit
    //Reddingsboeien, radio's, radars, torenkranen, vlaggen en rompverfraaiing


        return quote;
    }
}

class quote{
    private String clientName;
    private double price;
    private String date;
    private String orderNumber;
    private boat boat;

    quote(String clientName, double price, String date, String orderNumber){
        clientName = this.clientName;
        price = this.price;
        date = this.date;
        orderNumber = this.orderNumber;
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
}
/*needs to be further modified but works now as well.
~pratik
*/
class Offerte {
    private double bootPrijs;
    private double btwPercentage;
    private double transportKosten;
    private double totaalPrijs;
    private double milieuKorting;

    public Offerte(double bootPrijs, double milieuKorting, double btwPercentage, double transportKosten) {
        this.bootPrijs = bootPrijs;
        this.btwPercentage = btwPercentage;
        this.transportKosten = transportKosten;
        this.milieuKorting = milieuKorting;
        this.totaalPrijs = berekenTotaalPrijs();
    }

    public double berekenTotaalPrijs() {
        double btwBedrag = bootPrijs * btwPercentage / 100;
        double MilieuKorting = bootPrijs * milieuKorting / 100;
        double totaalPrijs = bootPrijs + MilieuKorting + btwBedrag + transportKosten;
        return totaalPrijs;
    }

    public void printOfferte() {
        DecimalFormat df = new DecimalFormat("#.00");

        System.out.println("Price quotation for the base off the boat:");
        System.out.println("Boat frame price: €" + df.format(bootPrijs));
        System.out.println("Environmental Discount (" + milieuKorting + "%):€ "+ df.format(bootPrijs * milieuKorting / 100));
        System.out.println("VAT (" + btwPercentage + "%): €" + df.format(bootPrijs * btwPercentage / 100));
        System.out.println("Transport costs: €" + df.format(transportKosten));
        System.out.println("Total Discount (" + milieuKorting + "%):€ " + df.format(bootPrijs * milieuKorting / 100));
        System.out.println("Total Price: €" + df.format(totaalPrijs));
    }
}


/* this is a program that produces quotations complying to the conditions of the client "bedrijf 42"
it should run in a while loop, and we intend to work with the basis of a template */


public class Main {
    public static void main(String[] args) {
        shell shell = new shell();
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
/*
~Pratik needs to be further modified but it works now as well.
*/
        System.out.print("Enter the base price of the boat: ");
        double bootPrijs = scanner.nextDouble();

        System.out.println("Enter the environmental discount in %: ");
        double milieuKorting = scanner.nextDouble();

        System.out.print("Enter the VAT-percentage: ");
        double btwPercentage = scanner.nextDouble();

        System.out.print("Enter the transportation cost: ");
        double transportKosten = scanner.nextDouble();

        Offerte offerte = new Offerte(bootPrijs, milieuKorting, btwPercentage, transportKosten);
        offerte.printOfferte();

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