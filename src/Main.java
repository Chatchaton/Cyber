

public class Main {
    public static void main(String[] args) throws Exception {

        Menu menu = new Menu();
        menu.printMenu();

        DigitalSignature digitalSignature = new DigitalSignature();
        digitalSignature.modifyUserMessage("Hello World");
        digitalSignature.calculateSignature();
        digitalSignature.print_signature();

        digitalSignature.modifyUserMessage("Not now");
        digitalSignature.verifySignature();

    }
}
