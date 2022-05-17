import menuComponents.MenuOptionsList;
import signature.DigitalSignature;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;

public class Main {
    public static void main(String[] args) throws Exception {

        Window window = new Window();
        MenuOptionsList.MenuOption opt = window.operateMenu();
        System.out.println(opt);

        DigitalSignature digitalSignature = new DigitalSignature();
        digitalSignature.modifyUserMessage("Hello World");
        digitalSignature.calculateSignature();
        digitalSignature.print_signature();

        digitalSignature.modifyUserMessage("Not now");
        digitalSignature.verifySignature();

        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withDSA");

        //Initialize the signature
        PrivateKey privKey = null;
        sign.initSign(privKey);
        byte[] bytes = "msg".getBytes();

        //Adding data to the signature
        sign.update(bytes);

        //Calculating the signature
        byte[] signature = sign.sign();

        //Printing the signature
        System.out.println("Digital signature for given text: "+new String(signature, StandardCharsets.UTF_8));
    }
}
