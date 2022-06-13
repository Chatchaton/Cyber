package ui

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.ToggleGroup
import tornadofx.*
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import kotlin.io.path.name

class SignView : View() {

    val signatureController: SignatureController by inject()
    val fileChooserController: FileChooserController by inject()

    val fileProperty = SimpleObjectProperty<File?>()
    var file by fileProperty
    val filename = fileProperty.stringBinding { it?.name }

    val hashAlgorithmProperty = SimpleStringProperty(signatureController.hashAlgorithm)
    var hashAlgorithm by hashAlgorithmProperty

    val authorProperty = SimpleStringProperty(runCatching { System.getProperty("user.name") }.getOrDefault(""))
    var author by authorProperty

    override val root = vbox {
        form {
            fieldset {
                field("File:") {
                    text(filename)
                    button("choose") {
                        action {
                            fileChooserController.chooseFile("Choose file to sign").firstOrNull().let {
                                file = it
                            }
                        }
                    }
                }
                field("Hash:") {
                    val toggleGroup = ToggleGroup()
                    signatureController.supportedHashAlgorithms.forEach {
                        radiobutton(it, toggleGroup) {
                            isSelected = hashAlgorithm == it
                            action {
                                hashAlgorithm = it
                            }
                        }
                    }
                }
                field("Author:") {
                    textfield(authorProperty)
                }
                button("sign") {
                    enableWhen {
                        fileProperty.isNotNull and signatureController.privateKeyProperty.isNotNull and signatureController.publicKeyProperty.isNotNull
                    }
                    action {
                        try {
                            signatureController.hashAlgorithm = hashAlgorithm
                            signatureController.author = author
                            val signatureFile = signatureController.createSignatureFile(file!!.readBytes())
                            val signaturePath = file!!.toPath().let { it.resolveSibling(it.name + ".sign") }
                            ObjectOutputStream(FileOutputStream(signaturePath.toFile())).use {
                                it.writeObject(signatureFile)
                            }
                            information("Signature created")
                        } catch (ex: java.lang.Exception) {
                            error("Exception", ex.message)
                        }
                    }
                }
            }
        }
    }
}
