package ui

import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

class FileChooserController : Controller() {

    var lastDirectory: File? = null

    fun chooseFile(
        title: String,
        mode: FileChooserMode = FileChooserMode.Single,
        op: FileChooser.() -> Unit = {}
    ): List<File> {
        val files = chooseFile(title, emptyArray(), lastDirectory, mode = mode, op = op)
        files.firstOrNull()?.let {
            lastDirectory = it.parentFile
        }
        return files
    }

}