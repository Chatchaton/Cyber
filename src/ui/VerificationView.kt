package ui

import javafx.beans.property.SimpleObjectProperty
import signature.SignatureFile
import tornadofx.*
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.security.PublicKey

class VerificationView : View() {

    val signatureController: SignatureController by inject()
    val fileChooserController: FileChooserController by inject()
    val publicKeyController: PublicKeyController by inject()

    val fileProperty = SimpleObjectProperty<File?>()
    var file by fileProperty

    val signatureProperty = SimpleObjectProperty<File?>()
    var signature by signatureProperty

    val publicKeyProperty = SimpleObjectProperty<Pair<String, PublicKey>?>()
    var publicKey by publicKeyProperty

    override val root = vbox {
        form {
            fieldset {
                field("File:") {
                    text(fileProperty.stringBinding { it?.name })
                    button("choose") {
                        action {
                            fileChooserController.chooseFile("Choose file to authenticate").firstOrNull().let {
                                file = it
                            }
                        }
                    }
                }
                field("Signature:") {
                    text(signatureProperty.stringBinding { it?.name })
                    button("choose") {
                        action {
                            fileChooserController.chooseFile("Choose signature").firstOrNull().let {
                                signature = it
                            }
                        }
                    }
                }
                field("Public key:") {
                    combobox(publicKeyProperty) {
                        itemsProperty().bind(publicKeyController.keysProperty.objectBinding { map ->
                            map?.entries?.map { it.toPair() }?.toList()?.asObservable()
                        })
                        cellFormat {
                            text = it?.first
                        }
                    }
                    button("open keys directory") {
                        action {
                            app.hostServices.showDocument(publicKeyController.keysDirectory.toUri().toString())
                        }
                    }
                }
                button("verify") {
                    enableWhen {
                        fileProperty.isNotNull and signatureProperty.isNotNull and publicKeyProperty.isNotNull
                    }
                    action {
                        try {
                            val signatureFile = ObjectInputStream(FileInputStream(signature!!)).use {
                                it.readObject() as SignatureFile
                            }
                            signatureController.publicKey = publicKey!!.second
                            val result = signatureController.verifySignature(signatureFile, file!!.readBytes())
                            if (result) information("Verification successful")
                            else error("Verification failed")
                        } catch (ex: java.lang.Exception) {
                            error("Exception", content = ex.message)
                        }
                    }
                }
            }
        }
    }
}
