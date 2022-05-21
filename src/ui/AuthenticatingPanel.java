package ui;

import ui.property.ValueChangedEvent;

import javax.swing.*;
import java.io.File;

public class AuthenticatingPanel extends VBox {
    private JButton authenticateButton;
    private File file;
    private File signature;

    public AuthenticatingPanel() {

        this.authenticateButton = new JButton("Authenticate");
        this.authenticateButton.addActionListener(e -> this.authenticate());

        this.setChildren(
            this.buildFileInput(),
            this.authenticateButton
        );
        updateAuthenticateButton();
    }

    private JPanel buildFileInput() {
        var panel = new JPanel();
        var layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        var fileLabel = new JLabel("File:");
        var fileChooser = new FileChooser();
        fileChooser.fileProperty().addListener(this::onFileChanged);

        var signatureLabel = new JLabel("Signature:");
        var signatureChooser = new FileChooser();
        signatureChooser.fileProperty().addListener(this::onSignatureChanged);


        this.authenticateButton = new JButton("Authenticate");
        this.authenticateButton.addActionListener(e -> this.authenticate());

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(fileLabel)
                    .addComponent(signatureLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileChooser.getFilenameLabel())
                        .addComponent(fileChooser.getChooseFileButton()))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(signatureChooser.getFilenameLabel())
                        .addComponent(signatureChooser.getChooseFileButton())))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(fileLabel)
                    .addComponent(fileChooser.getFilenameLabel())
                    .addComponent(fileChooser.getChooseFileButton()))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(signatureLabel)
                    .addComponent(signatureChooser.getFilenameLabel())
                    .addComponent(signatureChooser.getChooseFileButton()))
        );
        return panel;
    }

    private void authenticate() {

    }

    private void onFileChanged(ValueChangedEvent<File> event) {
        this.file = event.newValue();
        updateAuthenticateButton();
    }

    private void onSignatureChanged(ValueChangedEvent<File> event) {
        this.signature = event.newValue();
        updateAuthenticateButton();
    }

    private void updateAuthenticateButton() {
        authenticateButton.setEnabled(file != null && signature != null);
    }
}
