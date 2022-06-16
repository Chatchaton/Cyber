package ui

import tornadofx.*

class MainView : View("Cyber") {
    override val root = tabpane {
        minWidth = 400.0

        tab("Sign") {
            isClosable = false

            this += find<SignView>()
        }

        tab("Verification") {
            isClosable = false

            this += find<VerificationView>()
        }

        tab("Keys") {
            isClosable = false

            this += find<GenerateKeysView>()
        }

    }
}
