import menuComponents.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window implements ActionListener {

    final private boolean GUI_MODE = false; // turn ON/OFF GUI //
    final private int WINDOW_WIDTH = 500;
    final private int WINDOW_HEIGHT = 400;
    final private int WINDOW_LOCATION_X = 100;
    final private int WINDOW_LOCATION_Y = 100;
    final private String WINDOW_TITLE = "WDC Project App";

    private MenuList menu;
    private JFrame jFrame;
    private JMenuBar jMenuBar;
    private KeysView keysView;
    private SignView signView;
    private ValidateView validateView;

    public Window()
    {
        jFrame = new JFrame(WINDOW_TITLE);
        jFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        jFrame.setLocation(WINDOW_LOCATION_X, WINDOW_LOCATION_Y);
        jFrame.setLayout(new GridLayout(2, 1));

        jMenuBar = new JMenuBar();
        menu = new MenuList();
        jMenuBar.add(menu);
        jFrame.setJMenuBar(jMenuBar);

        keysView = new KeysView();
        jFrame.add(keysView);

        signView = new SignView();
        jFrame.add(signView);

        validateView = new ValidateView();

        jFrame.setVisible(GUI_MODE);
    }

    public MenuOptionsList.MenuOption operateMenu() { // only for text user interface //
        return menu.operateMenu();
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("- action in Window -");
        menu.actionPerformed(e);
        MenuOptionsList.MenuOption choice = menu.getLastChoice();
        switch (choice)
        {
            case SIGN -> {
                this.displaySignView();
            }
            case VALIDATE -> {
                this.displayValidateView();
            }
            case GEN_PAIR -> {
                this.displayGenPairView();
            }
        }
    }

    private void displaySignView()
    {
        jFrame.getContentPane().removeAll();
        jFrame.add(keysView);
        jFrame.add(signView);
        jFrame.repaint();
    }
    private void displayValidateView()
    {
        jFrame.getContentPane().removeAll();
        jFrame.add(keysView);
        jFrame.add(validateView);
        jFrame.repaint();
    }
    private void displayGenPairView() {}
}
