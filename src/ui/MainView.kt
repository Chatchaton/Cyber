package ui

import tornadofx.*

class MainView : View("Cyber") {
    override val root = tabpane {
        minWidth = 400.0

        tab("Sign") {
            isClosable = false

            this += find<SignView>()
        }

        tab("Authenticate") {
            isClosable = false

            this += find<AuthenticateView>()
        }

        tab("Keys") {
            isClosable = false

            this += find<KeyView>()
        }

    }
}
