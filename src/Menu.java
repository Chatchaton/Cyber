import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;

public class Menu implements ActionListener {

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
    final private boolean GUI_MODE = false; // turn ON/OFF GUI //
    final private int WINDOW_WIDTH = 300;
    final private int WINDOW_HEIGHT = 300;
    final private int WINDOW_LOCATION_X = 100;
    final private int WINDOW_LOCATION_Y = 100;


    final private Scanner scanner = new Scanner(System.in);
    private MenuOption choice = MenuOption.DEFAULT;
    private JMenu menu;
    private JFrame jFrame;
    private JMenuBar jMenuBar;
    private JMenuItem jMenuItemArr[] = new JMenuItem[menuOptionNames.length];

    public Menu() {
        jFrame = new JFrame("WDC Project App");
        jMenuBar = new JMenuBar();
        menu = new JMenu("Menu");

        for(int i=0; i< menuOptionNames.length; i++)
        {
            jMenuItemArr[i] = new JMenuItem(menuOptionNames[i]);
            jMenuItemArr[i].addActionListener(this);
            menu.add(jMenuItemArr[i]);
        }
        jMenuBar.add(menu);
        jFrame.setJMenuBar(jMenuBar);
        jFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        jFrame.setLocation(WINDOW_LOCATION_X, WINDOW_LOCATION_Y);
        jFrame.setLayout(null);
        jFrame.setVisible(GUI_MODE);
    }


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

    @Override
    public void actionPerformed(ActionEvent e)
    {
        for(int i=0; i< menuOptionNames.length; i++)
        {
            if(e.getSource() == jMenuItemArr[i])
            {
                this.choice = MenuOption.values()[i];
            }
        }
    }
}
