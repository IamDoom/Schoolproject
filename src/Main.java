import java.util.Scanner;
import java.util.ArrayList;

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

/* this is a program that produces quotations complying to the conditions of the client "bedrijf 42"
it should run in a while loop, and we intend to work with the basis of a template */


public class Main {
    public static void main(String[] args) {
        shell shell = new shell();
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        while(run) {
            String input = scanner.nextLine(); //first version of inputting into console

            //switch case for a bar-bones version of commands and results, classes and methods have yet to be added.
            switch(input){
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