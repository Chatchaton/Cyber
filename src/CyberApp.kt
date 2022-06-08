import tornadofx.*
import ui.MainView

class CyberApp : App(MainView::class)

fun main(args: Array<String>) {
    launch<CyberApp>(args)
}