package ui;

import services.ServiceCollection;
import signature.DigitalSignature;
import signature.KeyLoader;
import ui.property.ReadonlyProperty;
import ui.property.SimpleProperty;
import ui.property.ValueChangedEvent;
import ui.utils.FileInputHelper;
import ui.utils.Result;

import javax.swing.*;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeysPanel extends VBox {
    private static final String[] KEYGEN_ALGORITHMS = {"DiffieHellman", "DSA", "RSA", "EC"};
    private static final String[] SIGNING_ALGORITHMS = {"SHA256withRSA", "SHA384withRSA", "SHA512withRSA"};
    private final DigitalSignature digitalSignature;
    private final ServiceCollection serviceCollection;

    private final SimpleProperty<Result<PublicKey>> _publicKeyProperty = new SimpleProperty<>();
    private final SimpleProperty<Result<PrivateKey>> _privateKeyProperty = new SimpleProperty<>();
    private final boolean silentFileChange = false;
    private FileChooser publicKeyChooser;
    private FileChooser privateKeyChooser;

    public KeysPanel(ServiceCollection serviceCollection) {
        this.digitalSignature = serviceCollection.getInstance(DigitalSignature.class);
        this.serviceCollection = serviceCollection;
        var fileInput = buildFileInput();

        _publicKeyProperty.addListener(this::onPublicKeyChanged);
        _privateKeyProperty.addListener(this::onPrivateKeyChanged);

        var generateButton = new JButton("Generate Keys");
        generateButton.addActionListener(e -> this.generate());

        this.setChildren(fileInput, generateButton);
    }

    private void generate() {
        var window = SwingUtilities.getWindowAncestor(this);
        var dialog = new GenerateKeysDialog(window, this.serviceCollection);
        dialog.onSavedEvent().addListener(this::onKeysSaved);
        dialog.setVisible(true);
    }

    private void onKeysSaved(GenerateKeysDialog.SavedEventArg arg) {
        _publicKeyProperty.forceSetLocalValue(Result.success(arg.publicKey()));
        _privateKeyProperty.forceSetLocalValue(Result.success(arg.privateKey()));
        publicKeyChooser.fileProperty().forceSetLocalValue(arg.publicKeyFile());
        privateKeyChooser.fileProperty().forceSetLocalValue(arg.privateKeyFile());
    }

    public ReadonlyProperty<Result<PublicKey>> publicKeyProperty() {
        return _publicKeyProperty;
    }

    public ReadonlyProperty<Result<PrivateKey>> privateKeyProperty() {
        return _privateKeyProperty;
    }

    private void onPublicKeyChanged(ValueChangedEvent<Result<PublicKey>> resultValueChangedEvent) {
        if (silentFileChange) return;
        if (resultValueChangedEvent.newValue() instanceof Result.Success<PublicKey> success) {
            digitalSignature.setPublicKey(success.value());
        } else if (resultValueChangedEvent.newValue() instanceof Result.Failure failure) {
            JOptionPane.showMessageDialog(
                this,
                failure.exception().getMessage(),
                "Failed to load the public key",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onPrivateKeyChanged(ValueChangedEvent<Result<PrivateKey>> resultValueChangedEvent) {
        if (silentFileChange) return;
        if (resultValueChangedEvent.newValue() instanceof Result.Success<PrivateKey> success) {
            digitalSignature.setPrivateKey(success.value());
        } else if (resultValueChangedEvent.newValue() instanceof Result.Failure failure) {
            JOptionPane.showMessageDialog(
                this,
                failure.exception().getMessage(),
                "Failed to load the private key",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel buildFileInput() {
        var panel = new JPanel();

        this.publicKeyChooser = new FileChooser();
        _publicKeyProperty.bind(publicKeyChooser.fileProperty(), file -> Result.run(() -> KeyLoader.readPublicKey(file)));

        this.privateKeyChooser = new FileChooser();
        _privateKeyProperty.bind(privateKeyChooser.fileProperty(), file -> Result.run(() -> KeyLoader.readPrivateKey(file)));

        new FileInputHelper()
            .add("Public Key:", publicKeyChooser)
            .add("Private Key:", privateKeyChooser)
            .buildLayout(panel);

        return panel;
    }

}
