package ui;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;

public class SigningPanel extends JPanel {
    private static final String[] KEYGEN_ALGORITHMS = {"DiffieHellman", "DSA", "RSA", "EC"};
    private static final String[] SIGNING_ALGORITHMS = {" SHA256withRSA", "SHA384withRSA", "SHA512withRSA"};
    private final JButton signButton;
    private File file;

    public SigningPanel() {

        var layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        var fileLabel = new JLabel("File:");
        var chooser = new FileChooser();
        chooser.addListener(this::onFileChanged);

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
        signButton.addActionListener(e -> this.onSign());
        this.updateSignButton();

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


    private void onSign() {

    }


    private void onFileChanged(FileChooser.FileChangedEvent event) {
        this.file = event.file();
        updateSignButton();
    }

    private void updateSignButton() {
        signButton.setEnabled(this.file != null);
    }
}
