package menuComponents;

import javax.swing.*;
import java.awt.*;

public class KeysView extends Container {

    private JLabel pubKeyLabel;
    private JLabel privKeyLabel;
    private JTextField pubKeyField;
    private JTextField privKeyField;
    private String pubKeyString;
    private String privKeyString;

    public KeysView()
    {
        this.setLayout(null);

        pubKeyLabel = new JLabel("Public Key:");
        pubKeyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        pubKeyLabel.setSize(100, 20);
        pubKeyLabel.setLocation(100, 100);
        this.add(pubKeyLabel);

        pubKeyField = new JTextField("");
        pubKeyField.setFont(new Font("Arial", Font.PLAIN, 15));
        pubKeyField.setSize(190, 20);
        pubKeyField.setLocation(200, 100);
        this.add(pubKeyField);

        privKeyLabel = new JLabel("Private Key:");
        privKeyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        privKeyLabel.setSize(100, 20);
        privKeyLabel.setLocation(100, 150);
        this.add(privKeyLabel);

        privKeyField = new JTextField("");
        privKeyField.setFont(new Font("Arial", Font.PLAIN, 15));
        privKeyField.setSize(150, 20);
        privKeyField.setLocation(200, 150);
        this.add(privKeyField);

        pubKeyString = pubKeyField.getText();
        privKeyString = privKeyField.getText();
    }


    public void updateData()
    {
        pubKeyString = pubKeyField.getText();
        privKeyString = privKeyField.getText();
    }
}
