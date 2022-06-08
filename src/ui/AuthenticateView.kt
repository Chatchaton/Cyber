package ui

import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import java.io.File

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
                            signatureController.calculateMessageBytes(file!!.toPath())
                            val result = signatureController.verifySignature(signature!!.toPath())
                            if (result) information("Verification failed")
                            else error("Verification successful")
                        } catch (ex: java.lang.Exception) {
                            error("Exception", content = ex.message)
                        }
                    }
                }
            }
        }
    }
}
