package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Cyber");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildComponents();

        setVisible(true);
    }

    public static void main(String[] args) {
        var frame = new MainFrame();
    }

    private void buildComponents() {

        this.setMinimumSize(new Dimension(800, 600));

        var tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Sign", new SigningPanel());
        tabbedPane.addTab("Authenticate", new AuthenticatingPanel());
        getContentPane().add(tabbedPane);
        pack();
    }


}


