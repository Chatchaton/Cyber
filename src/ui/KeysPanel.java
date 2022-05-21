package ui;

import ui.property.ValueChangedEvent;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;

public class KeysPanel extends VBox {
    private static final String[] KEYGEN_ALGORITHMS = {"DiffieHellman", "DSA", "RSA", "EC"};
    private static final String[] SIGNING_ALGORITHMS = {" SHA256withRSA", "SHA384withRSA", "SHA512withRSA"};
    private final JButton signButton;
    private File file;

    public KeysPanel() {

        var fileInput = buildFileInput();
        var algorithmSelect = buildAlgorithmSelect();


        this.signButton = new JButton("Sign");
        signButton.addActionListener(e -> this.onSign());
        this.updateSignButton();

        this.setChildren(fileInput, algorithmSelect, signButton);

    }

    private JPanel buildFileInput() {
        var panel = new JPanel();
        var layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        var fileLabel = new JLabel("File:");
        var chooser = new FileChooser();
        chooser.fileProperty().addListener(this::onFileChanged);

        var fileInputHorizontalGroup = layout.createSequentialGroup()
            .addComponent(fileLabel)
            .addComponent(chooser.getFilenameLabel())
            .addComponent(chooser.getChooseFileButton());
        var fileInputVerticalGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(fileLabel)
            .addComponent(chooser.getFilenameLabel())
            .addComponent(chooser.getChooseFileButton());
        layout.setHorizontalGroup(fileInputHorizontalGroup);
        layout.setVerticalGroup(fileInputVerticalGroup);

        return panel;
    }

    private JPanel buildAlgorithmSelect() {
        var panel = new JPanel();
        var layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

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
        layout.setHorizontalGroup(algorithmHorizontalGroup);
        layout.setVerticalGroup(algorithmVerticalGroup);

        return panel;
    }


    private void onSign() {

    }


    private void onFileChanged(ValueChangedEvent<File> event) {
        this.file = event.newValue();
        updateSignButton();
    }

    private void updateSignButton() {
        signButton.setEnabled(this.file != null);
    }
}
