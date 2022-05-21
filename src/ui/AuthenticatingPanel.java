package ui;

import services.ServiceCollection;
import ui.property.ValueChangedEvent;
import ui.utils.FileInputHelper;

import javax.swing.*;
import java.io.File;

public class AuthenticatingPanel extends VBox {
    private JButton authenticateButton;
    private File file;
    private File signature;

    public AuthenticatingPanel(ServiceCollection serviceCollection) {

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

        var fileChooser = new FileChooser();
        fileChooser.fileProperty().addListener(this::onFileChanged);

        var signatureChooser = new FileChooser();
        signatureChooser.fileProperty().addListener(this::onSignatureChanged);


        new FileInputHelper()
            .add("File:", fileChooser)
            .add("Signature:", signatureChooser)
            .buildLayout(panel);

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
