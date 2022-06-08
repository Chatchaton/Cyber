package ui

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleObjectProperty
import signature.KeyLoader
import tornadofx.*
import java.io.File
import java.security.PrivateKey
import java.security.PublicKey

class GenerateKeysFragment : Fragment("Generate keys") {

    val publicKeyFileProperty = SimpleObjectProperty<File?>()
    var publicKeyFile by publicKeyFileProperty
    val privateKeyFileProperty = SimpleObjectProperty<File?>()
    var privateKeyFile by privateKeyFileProperty

    val publicKeyProperty = SimpleObjectProperty<PublicKey?>()
    var publicKey by publicKeyProperty
    val privateKeyProperty = SimpleObjectProperty<PrivateKey>()
    var privateKey by privateKeyProperty

    private val signatureController by inject<SignatureController>()

    var result = false

    override val root = vbox {
        minWidth = 400.0
        form {
            fieldset {
                field("Public key:") {
                    text(publicKeyFileProperty.stringBinding { it?.name })
                    button("choose") {
                        action {
                            chooseFile("Save public key", emptyArray(), mode = FileChooserMode.Save).firstOrNull()
                                ?.let {
                                    publicKeyFile = it
                                }
                        }
                    }
                }
                field("Private key:") {
                    text(privateKeyFileProperty.stringBinding { it?.name })
                    button("choose") {
                        action {
                            chooseFile("Save private key", emptyArray(), mode = FileChooserMode.Save).firstOrNull()
                                ?.let {
                                    privateKeyFile = it
                                }
                        }
                    }
                }
                button("generate") {
                    enableWhen(
                        Bindings.and(
                            publicKeyFileProperty.booleanBinding { it != null },
                            privateKeyFileProperty.booleanBinding { it != null })
                    )
                    action {
                        generate()
                    }
                }
            }
        }
    }

    private fun generate() {
        try {
            signatureController.digitalSignature.generateKeyPair()
            publicKey = signatureController.digitalSignature.publicKey
            privateKey = signatureController.digitalSignature.privateKey
        } catch (ex: Exception) {
            error("Failed to generate keys", content = ex.message).showAndWait()
            return
        }
        try {
            KeyLoader.writePublicKey(publicKey, publicKeyFile)
            KeyLoader.writePrivateKey(privateKey, privateKeyFile)
        } catch (ex: Exception) {
            error("Failed to save keys", content = ex.message).showAndWait()
            return
        }
        result = true
        close()
    }
}
