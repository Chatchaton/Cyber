package ui

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.ButtonType
import javafx.scene.control.ToggleGroup
import signature.writePrivateKey
import signature.writePublicKey
import tornadofx.*
import java.io.File

class GenerateKeysView : View("Generate keys") {

    val publicKeyFileProperty = SimpleObjectProperty<File?>()
    var publicKeyFile by publicKeyFileProperty
    val privateKeyFileProperty = SimpleObjectProperty<File?>()
    var privateKeyFile by privateKeyFileProperty

    private val signatureController by inject<SignatureController>()
    val fileChooserController: FileChooserController by inject()

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
                            fileChooserController.chooseFile("Save public key", mode = FileChooserMode.Save) {
                                initialFileName = "key.public"
                            }.firstOrNull()?.let {
                                publicKeyFile = it
                            }
                        }
                    }
                }
                field("Private key:") {
                    text(privateKeyFileProperty.stringBinding { it?.name })
                    button("choose") {
                        action {
                            fileChooserController.chooseFile("Save private key", mode = FileChooserMode.Save) {
                                initialFileName = "key.private"
                            }.firstOrNull()?.let {
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
        val publicExists = publicKeyFile!!.exists()
        val privateExists = privateKeyFile!!.exists()
        if (publicExists || privateExists) {
            val both = publicExists && privateExists
            val header = if (both) "Files already exist" else "File already exists"
            val content =
                if (both) "Both files already exist. Do you want do override them?"
                else "${if (publicExists) "Public" else "Private"} key file already exists. Do you want to override it?"
            confirmation(header, content) {
                if (it != ButtonType.OK) {
                    return@generate
                }
            }
        }

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
        information("Keys generated successfully")
    }
}
