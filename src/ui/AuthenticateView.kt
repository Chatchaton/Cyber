package ui

import javafx.beans.property.SimpleObjectProperty
import signature.SignatureFile
import tornadofx.*
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

class AuthenticateView : View() {

    val signatureController: SignatureController by inject()

    val fileProperty = SimpleObjectProperty<File?>()
    var file by fileProperty

    val signatureProperty = SimpleObjectProperty<File?>()
    var signature by signatureProperty

    override val root = vbox {
        form {
            fieldset {
                field("File:") {
                    text(fileProperty.stringBinding { it?.name })
                    button("choose") {
                        action {
                            chooseFile("Choose file to authenticate", emptyArray()).firstOrNull().let {
                                file = it
                            }
                        }
                    }
                }
                field("Signature:") {
                    text(signatureProperty.stringBinding { it?.name })
                    button("choose") {
                        action {
                            chooseFile("Choose signature", emptyArray()).firstOrNull().let {
                                signature = it
                            }
                        }
                    }
                }
                button("verify") {
                    enableWhen {
                        fileProperty.isNotNull and signatureProperty.isNotNull and signatureController.publicKeyProperty.isNotNull
                    }
                    action {
                        try {
                            val signatureFile = ObjectInputStream(FileInputStream(signature!!)).use {
                                it.readObject() as SignatureFile
                            }
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
