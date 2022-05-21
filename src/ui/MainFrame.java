package ui;

import services.ServiceCollection;
import signature.DigitalSignature;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final ServiceCollection serviceCollection = new ServiceCollection();

    public MainFrame() throws Exception {
        super("Cyber");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        serviceCollection.setSingleton(DigitalSignature.class, new DigitalSignature("SHA256withRSA"));

        buildComponents();

        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        var frame = new MainFrame();
    }

    private void buildComponents() {

        this.setMinimumSize(new Dimension(800, 600));

        var tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Sign", new SigningPanel(serviceCollection));
        tabbedPane.addTab("Authenticate", new AuthenticatingPanel(serviceCollection));
        tabbedPane.addTab("Keys", new KeysPanel(serviceCollection));
        getContentPane().add(tabbedPane);
        pack();
    }


}


