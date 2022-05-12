import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class Menu {

    public enum MenuOption { // hidden options only after visible ones //
        SIGN,
        VALIDATE,
        GEN_PAIR,
        DEFAULT
    }
    final private String menuOptionNames[] = new String[]{ // menu option names for enums //
            "sign file",
            "validate file",
            "generate key pair"
    };


    private MenuOption choice = MenuOption.DEFAULT;
    final private Scanner scanner = new Scanner(System.in);



    public Menu() {}


    public void printMenu()
    {
        System.out.println("------------------------");
        for(int i=0; i<menuOptionNames.length; i++)
        {
            System.out.println(i + " - " + menuOptionNames[i]);
        }
        System.out.println("------------------------");
        System.out.print("> ");
    }


    /**
     prints menu -> gets user input -> returns option enum
     @return selected option as: < MenuOption > enum
    */
    public MenuOption operateMenu() // prints menu and return choice //
    {
        boolean validChoice = false;
        int input = 0;

        while(validChoice == false)
        {
            printMenu();
            input = scanner.nextInt();
            if(input >= 0 && input < menuOptionNames.length)
            {
                validChoice = true;
            }
            else
            {
                System.out.println("- value not supported -");
            }
        }
        this.choice = MenuOption.values()[input];
        return this.choice;
    }

    public void setChoice(MenuOption choice) { this.choice = choice; }
    public MenuOption getChoice() { return this.choice; }

}
