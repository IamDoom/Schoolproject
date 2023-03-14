import java.util.Scanner;


/* this is a program that produces quotations complying to the conditions of the client "bedrijf 42"
it should run in a while loop, and we intend to work with the basis of a template */


public class Main {
    public static void main(String[] args) {
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
                case "list":
                    System.out.println("listing possible options with prices and discounts");
                    break;
                case "add":
                    System.out.println("function for adding options");
                    break;
                case "remove":
                    System.out.println("function for removing options");
                    break;
                case "option 1":
                    System.out.println("selecting or de-selecting option 1");
                    break;
                case "option 2":
                    System.out.println("selecting or de-selecting option 2");
                    break;

            }
        }
    }
}