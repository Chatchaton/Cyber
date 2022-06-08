package ui

import javafx.beans.property.ReadOnlyObjectProperty
import javafx.beans.property.SimpleObjectProperty
import signature.DigitalSignature
import signature.DigitalSignatureInterface
import tornadofx.*
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey

class SignatureController(val digitalSignature: DigitalSignatureInterface = DigitalSignature("SHA256withRSA")) :
    Controller(),
    DigitalSignatureInterface by digitalSignature {

    private val _publicKeyProperty = SimpleObjectProperty<PublicKey?>()
    val publicKeyProperty: ReadOnlyObjectProperty<PublicKey?>
        get() = _publicKeyProperty
    private val _privateKeyProperty = SimpleObjectProperty<PrivateKey?>()
    val privateKeyProperty: ReadOnlyObjectProperty<PrivateKey?>
        get() = _privateKeyProperty


    override fun generateKeyPair() {
        digitalSignature.generateKeyPair()
        _publicKeyProperty.set(digitalSignature.publicKey)
        _privateKeyProperty.set(digitalSignature.privateKey)
    }

    override fun setPublicKey(publicKey: PublicKey?) {
        digitalSignature.publicKey = publicKey
        _publicKeyProperty.set(publicKey)
    }

    override fun setPrivateKey(privateKey: PrivateKey?) {
        digitalSignature.privateKey = privateKey
        _privateKeyProperty.set(privateKey)
    }

    override fun setKeyPair(keyPair: KeyPair?) {
        digitalSignature.keyPair = keyPair
        _publicKeyProperty.set(keyPair?.public)
        _privateKeyProperty.set(keyPair?.private)
    }

}