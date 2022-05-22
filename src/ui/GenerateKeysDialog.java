package ui;

import services.ServiceCollection;
import signature.DigitalSignature;
import signature.KeyLoader;
import ui.property.Event;
import ui.property.ReadonlyProperty;
import ui.property.SimpleEvent;
import ui.property.ValueChangedEvent;
import ui.utils.FileInputHelper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

public class GenerateKeysDialog extends JDialog {

    private final SimpleEvent<SavedEventArg> _onSavedEvent = new SimpleEvent<>();
    private final DigitalSignature digitalSignature;
    private final JButton saveButton;
    private final ReadonlyProperty<File> publicKeyFile;
    private final ReadonlyProperty<File> privateKeyFile;

    public GenerateKeysDialog(Window window, ServiceCollection serviceCollection) {
        super(window, ModalityType.APPLICATION_MODAL);
        this.digitalSignature = serviceCollection.getInstance(DigitalSignature.class);

        this.setMinimumSize(new Dimension(400, 100));

        var fileInputPanel = new JPanel();
        var publicKeyChooser = new FileChooser(FileChooser.MODE_SAVE);
        this.publicKeyFile = publicKeyChooser.fileProperty();
        this.publicKeyFile.addListener(this::onPublicKeyFileChanged);
        var privateKeyChooser = new FileChooser(FileChooser.MODE_SAVE);
        this.privateKeyFile = privateKeyChooser.fileProperty();
        this.privateKeyFile.addListener(this::onPrivateKeyFileChanged);
        new FileInputHelper()
            .add("Public Key", publicKeyChooser)
            .add("Private Key", privateKeyChooser)
            .buildLayout(fileInputPanel);

        this.saveButton = new JButton("Save Keys");
        this.saveButton.addActionListener(e -> this.save());

        this.getContentPane().add(
            VBox.of(
                fileInputPanel,
                saveButton
            )
        );
        this.pack();
        this.updateSaveButton();
    }

    public Event<SavedEventArg> onSavedEvent() {
        return _onSavedEvent;
    }

    private void onPublicKeyFileChanged(ValueChangedEvent<File> event) {
        updateSaveButton();
    }

    private void onPrivateKeyFileChanged(ValueChangedEvent<File> event) {
        updateSaveButton();
    }

    private void updateSaveButton() {
        saveButton.setEnabled(privateKeyFile.value() != null && publicKeyFile.value() != null);
    }

    private void save() {
        try {
            digitalSignature.generateKeyPair();
            var publicKey = digitalSignature.getPublicKey();
            var privateKey = digitalSignature.getPrivateKey();
            KeyLoader.writePublicKey(publicKey, publicKeyFile.value());
            KeyLoader.writePrivateKey(privateKey, privateKeyFile.value());
            var arg = new SavedEventArg(publicKey, publicKeyFile.value(), privateKey, privateKeyFile.value());
            _onSavedEvent.notifyListeners(arg);
            JOptionPane.showMessageDialog(this, "Keys saved", "Save succeeded.", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Save failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public record SavedEventArg(PublicKey publicKey, File publicKeyFile, PrivateKey privateKey, File privateKeyFile) {
    }

}
