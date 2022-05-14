package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class SigningPanel extends JPanel implements ActionListener, FileChooser.FileChangedListener {
    private static final String[] KEYGEN_ALGORITHMS = { "DiffieHellman", "DSA", "RSA", "EC" };
    private static final String[] SIGNING_ALGORITHMS = { " SHA256withRSA", "SHA384withRSA", "SHA512withRSA" };
    private static final String SIGN_ACTION_COMMAND = "SIGN_ACTION_COMMAND";
    private final JButton signButton;

    public SigningPanel() {

        var layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        var fileLabel = new JLabel("File:");
        var chooser = new FileChooser();
        chooser.addListener(this);

        var fileInputHorizontalGroup = layout.createSequentialGroup()
                .addComponent(fileLabel)
                .addComponent(chooser.getFilenameLabel())
                .addComponent(chooser.getChooseFileButton());
        var fileInputVerticalGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(fileLabel)
                .addComponent(chooser.getFilenameLabel())
                .addComponent(chooser.getChooseFileButton());


        var algorithmLabel = new JLabel("Algorithm:");
        var radioButtons = Arrays.stream(SIGNING_ALGORITHMS).map(algorithm -> {
            var button = new JRadioButton(algorithm);
            button.setActionCommand(algorithm);
            return button;
        }).toList();
        radioButtons.get(0).setSelected(true);
        var buttonGroup = new ButtonGroup();
        radioButtons.forEach(buttonGroup::add);

        var algorithmHorizontalGroup = layout.createParallelGroup()
                .addComponent(algorithmLabel);
        radioButtons.forEach(algorithmHorizontalGroup::addComponent);
        var algorithmVerticalGroup = layout.createSequentialGroup()
                .addComponent(algorithmLabel);
        radioButtons.forEach(algorithmVerticalGroup::addComponent);


        this.signButton = new JButton("Sign");
        signButton.setActionCommand(SIGN_ACTION_COMMAND);
        signButton.addActionListener(this);
        signButton.setEnabled(false);

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(fileInputHorizontalGroup)
                        .addGroup(algorithmHorizontalGroup)
                        .addComponent(signButton)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(fileInputVerticalGroup)
                        .addGroup(algorithmVerticalGroup)
                        .addComponent(signButton)
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SIGN_ACTION_COMMAND -> {

            }
        }
    }

    @Override
    public void call(FileChooser.FileChangedEvent arg) {
        signButton.setEnabled(arg.file() != null);
    }
}
