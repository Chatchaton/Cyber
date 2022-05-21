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

    private final SimpleProperty<Result<PublicKey>> _publicKeyProperty = new SimpleProperty<>();
    private final SimpleProperty<Result<PrivateKey>> _privateKeyProperty = new SimpleProperty<>();

    public KeysPanel(ServiceCollection serviceCollection) {
        this.digitalSignature = serviceCollection.getInstance(DigitalSignature.class);
        var fileInput = buildFileInput();


        _publicKeyProperty.addListener(this::onPublicKeyChanged);

        this.setChildren(fileInput);

    }

    public ReadonlyProperty<Result<PublicKey>> publicKeyProperty() {
        return _publicKeyProperty;
    }

    public ReadonlyProperty<Result<PrivateKey>> privateKeyProperty() {
        return _privateKeyProperty;
    }

    private void onPublicKeyChanged(ValueChangedEvent<Result<PublicKey>> resultValueChangedEvent) {
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

    private JPanel buildFileInput() {
        var panel = new JPanel();

        var publicKeyChooser = new FileChooser();
        _publicKeyProperty.bind(publicKeyChooser.fileProperty(), file -> Result.Run(() -> KeyLoader.readPublicKey(file)));

        var privateKeyChooser = new FileChooser();
        _privateKeyProperty.bind(privateKeyChooser.fileProperty(), file -> Result.Run(() -> KeyLoader.readPrivateKey(file)));

        new FileInputHelper()
            .add("Public Key:", publicKeyChooser)
            .add("Private Key:", privateKeyChooser)
            .buildLayout(panel);

        return panel;
    }

}
