package ui

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.ToggleGroup
import signature.writePrivateKey
import signature.writePublicKey
import tornadofx.*
import java.io.File

class GenerateKeysFragment : Fragment("Generate keys") {

    val publicKeyFileProperty = SimpleObjectProperty<File?>()
    var publicKeyFile by publicKeyFileProperty
    val privateKeyFileProperty = SimpleObjectProperty<File?>()
    var privateKeyFile by privateKeyFileProperty

    private val signatureController by inject<SignatureController>()

    val keyAlgorithmProperty = SimpleStringProperty(signatureController.keyAlgorithm)
    var keyAlgorithm by keyAlgorithmProperty

    var result = false

    override val root = vbox {
        minWidth = 400.0
        form {
            fieldset {
                field("Public key:") {
                    text(publicKeyFileProperty.stringBinding { it?.name })
                    button("choose") {
                        action {
                            chooseFile("Save public key", emptyArray(), mode = FileChooserMode.Save) {
                                initialFileName = "key.public"
                            }.firstOrNull()
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
                            chooseFile("Save private key", emptyArray(), mode = FileChooserMode.Save) {
                                initialFileName = "key.private"
                            }.firstOrNull()
                                ?.let {
                                    privateKeyFile = it
                                }
                        }
                    }
                }
                field("Algorithm:") {
                    val toggleGroup = ToggleGroup()
                    signatureController.supportedKeyAlgorithms.forEach {
                        radiobutton(it, toggleGroup) {
                            isSelected = keyAlgorithm == it
                            action {
                                keyAlgorithm = it
                            }
                        }

                    }
                }
                button("generate") {
                    enableWhen {
                        publicKeyFileProperty.isNotNull and privateKeyFileProperty.isNotNull
                    }
                    action {
                        generate()
                    }
                }
            }
        }
    }

    private fun generate() {
        try {
            signatureController.keyAlgorithm = keyAlgorithm
            signatureController.generateKeyPair()
        } catch (ex: Exception) {
            error("Failed to generate keys", content = ex.message)
            return
        }
        try {
            writePublicKey(publicKeyFile!!.toPath(), signatureController.publicKey).getOrThrow()
            writePrivateKey(privateKeyFile!!.toPath(), signatureController.privateKey).getOrThrow()
        } catch (ex: Exception) {
            error("Failed to save keys", content = ex.message)
            return
        }
        result = true
        close()
    }
}
