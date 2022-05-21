package ui;

import ui.property.ValueChangedEvent;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;

public class SigningPanel extends VBox {
    private static final String[] KEYGEN_ALGORITHMS = {"DiffieHellman", "DSA", "RSA", "EC"};
    private static final String[] SIGNING_ALGORITHMS = {"SHA256withRSA", "SHA384withRSA", "SHA512withRSA"};
    private final JButton signButton;
    private File file;

    public SigningPanel() {

        var fileInput = buildFileInput();
        var algorithmSelect = buildAlgorithmSelect();

        this.signButton = new JButton("Sign");
        signButton.addActionListener(e -> this.onSign());
        this.updateSignButton();

        setChildren(fileInput, algorithmSelect, signButton);

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
        var select = new RadioButtonSelect<String>();
        select.addOptions(
            "Algorithm",
            Arrays.stream(SIGNING_ALGORITHMS).map(algorithm -> new RadioButtonSelect.Option<>(algorithm, algorithm)).toList()
        );

        return select;
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
