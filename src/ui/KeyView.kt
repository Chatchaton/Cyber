package ui

import javafx.beans.property.SimpleObjectProperty
import javafx.stage.Modality
import signature.readPrivateKey
import signature.readPublicKey
import tornadofx.*
import java.io.File

class KeyView : View() {

    val signatureController by inject<SignatureController>()

    var publicKey by signatureController::publicKey
    val publicKeyFileProperty = SimpleObjectProperty<File?>()
    var publicKeyFile by publicKeyFileProperty

    var privateKey by signatureController::privateKey
    val privateKeyFileProperty = SimpleObjectProperty<File?>()
    var privateKeyFile by privateKeyFileProperty


    override val root = vbox {
        form {
            fieldset {
                field("Public key:") {
                    text(publicKeyFileProperty.stringBinding { it?.name })
                    button("choose") {
                        action {
                            chooseFile("Choose public key", emptyArray()).firstOrNull()?.let {
                                loadPublicKey(it)
                            }
                        }
                    }
                }
                field("Private key:") {
                    text(privateKeyFileProperty.stringBinding { it?.name })
                    button("choose") {
                        action {
                            chooseFile("Choose private key", emptyArray()).firstOrNull()?.let {
                                loadPrivateKey(it)
                            }
                        }
                    }
                }
                button("generate") {
                    action {
                        find<GenerateKeysFragment>().let {
                            it.openModal(modality = Modality.WINDOW_MODAL, block = true)
                            if (it.result) {
                                publicKeyFile = it.publicKeyFile
                                privateKeyFile = it.privateKeyFile
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadPublicKey(file: File) {
        try {
            publicKey = readPublicKey(file.toPath()).getOrThrow()
            publicKeyFile = file

        } catch (ex: Exception) {
            error("Failed to load the public key.", content = ex.message)
        }
    }

    private fun loadPrivateKey(file: File) {
        try {
            privateKey = readPrivateKey(file.toPath()).getOrThrow()
            privateKeyFile = file

        } catch (ex: Exception) {
            error("Failed to load the private key.", content = ex.message)
        }
    }

}
