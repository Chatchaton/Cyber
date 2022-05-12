import jdk.jfr.Description;

import java.util.Scanner;

public class Menu {

    private int choice = 0;
    Scanner scanner = new Scanner(System.in);

    public Menu() {}
    public void printMenu() {
        System.out.println("---------------");
        System.out.println("1 - sign");
        System.out.println("2 - validate");
        System.out.println("---------------");
    }
    /**
     @return choice ID
    */
    @Description("prints menu and returns choice ID")
    public int operateMenu() { // print menu and return choice //
        printMenu();
        this.choice = scanner.nextInt();
        return this.choice;
    }

    public void setChoice(int choice) { this.choice = choice; }
    public int getChoice() { return this.choice; }

}
