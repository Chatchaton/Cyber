package ui;

import javax.swing.*;
import java.io.File;

public class AuthenticatingPanel extends JPanel {
    private final JButton authenticateButton;
    private File file;
    private File signature;

    public AuthenticatingPanel() {
        var layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        var fileLabel = new JLabel("File:");
        var fileChooser = new FileChooser();
        fileChooser.addListener(this::onFileChanged);

        var signatureLabel = new JLabel("Signature:");
        var signatureChooser = new FileChooser();
        signatureChooser.addListener(this::onSignatureChanged);


        this.authenticateButton = new JButton("Authenticate");
        this.authenticateButton.addActionListener(e -> this.authenticate());

        layout.setHorizontalGroup(
            layout.createParallelGroup().addGroup(
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
                                .addComponent(signatureChooser.getChooseFileButton()))))
                .addComponent(this.authenticateButton)
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
                .addComponent(this.authenticateButton)
        );
    }

    private void authenticate() {

    }

    private void onFileChanged(FileChooser.FileChangedEvent event) {
        this.file = event.file();
        updateAuthenticateButton();
    }

    private void onSignatureChanged(FileChooser.FileChangedEvent event) {
        this.signature = event.file();
        updateAuthenticateButton();
    }

    private void updateAuthenticateButton() {
        authenticateButton.setEnabled(file != null && signature != null);
    }
}
