package menuComponents;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ValidateView extends JPanel {

    final private int MARGIN = 20;
    final private int VERTICAL_PADDING = 40;
    final private int HORIZONTAL_PADDING = 20;
    final private int UPPER_LEFT_CORNER = 0;
    final private int BOTTOM_RIGHT_CORNER = 0;
    final private int PANEL_WIDTH = 300;
    final private int PANEL_HEIGHT = 100;

    private JLabel pubKeyLabel;
    private JLabel privKeyLabel;
    private JTextField pubKeyField;
    private JTextField privKeyField;
    private String pubKeyString;
    private String privKeyString;
    private JButton button;
    private Border border;

    public ValidateView()
    {
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        this.setLayout(new GridLayout(3, 2));
        border = new EmptyBorder(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING);
        this.setBorder(border);

        pubKeyLabel = new JLabel("File path:");
        pubKeyLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        //pubKeyLabel.setSize(100, 20);
        //pubKeyLabel.setLocation(0, 0);
        this.add(pubKeyLabel);

        pubKeyField = new JTextField("");
        pubKeyField.setFont(new Font("Arial", Font.PLAIN, 13));
        //pubKeyField.setSize(200, 20);
        //pubKeyField.setLocation(100, 0);
        this.add(pubKeyField);

        privKeyLabel = new JLabel("Signature:");
        privKeyLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        //privKeyLabel.setSize(100, 20);
        //privKeyLabel.setLocation(0, 50);
        this.add(privKeyLabel);

        privKeyField = new JTextField("");
        privKeyField.setFont(new Font("Arial", Font.PLAIN, 13));
        //privKeyField.setSize(200, 20);
        //privKeyField.setLocation(100, 50);
        this.add(privKeyField);

        this.add(new JLabel("YES / NO"));

        button = new JButton("Validate");
        button.setFont(new Font("Arial", Font.PLAIN, 17));
        this.add(button);

        pubKeyString = pubKeyField.getText();
        privKeyString = privKeyField.getText();
    }


    public void updateData()
    {
        pubKeyString = pubKeyField.getText();
        privKeyString = privKeyField.getText();
    }
}
