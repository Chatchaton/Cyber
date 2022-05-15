package menuComponents;

import menuComponents.MenuOptionsList.MenuOption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;


public class MenuList extends JMenu implements ActionListener {

    final private String MENU_TEXT = "Menu";


    private MenuOptionsList menuOptList = new MenuOptionsList();
    private int menuLength = menuOptList.getLength();
    private MenuOptionsList.MenuOption lastChoice = MenuOption.SIGN;
    private JMenuItem jMenuItemArr[] = new JMenuItem[menuLength];
    private Scanner scanner = new Scanner(System.in);
    private ActionListener window;


    public MenuList(ActionListener window)
    {
        this.window = window;
        this.setText(MENU_TEXT);
        for(int i=0; i< menuLength; i++)
        {
            jMenuItemArr[i] = new JMenuItem(menuOptList.getMenuOptionName(i));
            jMenuItemArr[i].addActionListener(this);
            this.add(jMenuItemArr[i]);
        }
    }

    public void printMenu()
    {
        System.out.println("------------------------");
        for(int i=0; i<menuLength; i++)
        {
            System.out.println(i + " - " + menuOptList.getMenuOptionName(i));
        }
        System.out.println("------------------------");
        System.out.print("> ");
    }

    /**
     prints menu -> gets user input -> returns option enum
     @return selected option as: < MenuOption > enum
     */
    public MenuOptionsList.MenuOption operateMenu() // prints menu and return choice // // only for text user interface //
    {
        boolean validChoice = false;
        int input = 0;

        while(validChoice == false)
        {
            printMenu();
            input = scanner.nextInt();
            if(input >= 0 && input < menuLength)
            {
                validChoice = true;
            }
            else
            {
                System.out.println("- value not supported -");
            }
        }
        this.lastChoice = menuOptList.getMenuOption(input);
        return this.lastChoice;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //System.out.println("- action in MenuList -");
        for(int i=0; i< menuLength; i++)
        {
            if(e.getSource() == jMenuItemArr[i])
            {
                this.lastChoice = menuOptList.getMenuOption(i);
            }
        }
        window.actionPerformed(e);
    }

    public MenuOptionsList.MenuOption getLastChoice()
    {
        return lastChoice;
    }
}
