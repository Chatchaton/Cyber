package ui

import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import java.io.File
import kotlin.io.path.name

class SignView : View() {

    val signatureController: SignatureController by inject()

    val fileProperty = SimpleObjectProperty<File?>()
    var file by fileProperty
    val filename = fileProperty.stringBinding { it?.name }


    override val root = vbox {
        form {
            fieldset {
                field("File:") {
                    text(filename)
                    button("choose") {
                        action {
                            chooseFile("Choose file to sign", emptyArray()).firstOrNull().let {
                                file = it
                            }
                        }
                    }
                }
                button("sign") {
                    enableWhen {
                        fileProperty.isNotNull and signatureController.privateKeyProperty.isNotNull and signatureController.publicKeyProperty.isNotNull
                    }
                    action {
                        with(signatureController) {
                            initializeSignature()
                            calculateMessageBytes(file!!.toPath())
                            calculateSignature()
                            saveSignature(file!!.toPath().let { it.resolveSibling(it.name + ".sign") })
                        }
                        information("Signature created")
                    }
                }
            }
        }
    }
}
