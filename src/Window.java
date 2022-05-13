import menuComponents.*;


import javax.swing.*;
import java.awt.event.*;

public class Window implements ActionListener {

    final private boolean GUI_MODE = false; // turn ON/OFF GUI //
    final private int WINDOW_WIDTH = 300;
    final private int WINDOW_HEIGHT = 300;
    final private int WINDOW_LOCATION_X = 100;
    final private int WINDOW_LOCATION_Y = 100;


    private MenuList menu;
    private JFrame jFrame;
    private JMenuBar jMenuBar;
    private KeysView keysView;

    public Window() {
        jFrame = new JFrame("WDC Project App");
        jMenuBar = new JMenuBar();
        menu = new MenuList();
        jMenuBar.add(menu);
        jFrame.setJMenuBar(jMenuBar);
        jFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        jFrame.setLocation(WINDOW_LOCATION_X, WINDOW_LOCATION_Y);
        jFrame.setLayout(null);




        jFrame.setVisible(GUI_MODE);
    }

    public MenuOptionsList.MenuOption operateMenu() {
        return menu.operateMenu();
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        menu.actionPerformed(e);
    }

    private void displayOptionSign() {

    }
}
