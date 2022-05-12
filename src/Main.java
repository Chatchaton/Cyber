import java.security.*;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws Exception {
        //Accepting text from user
        Scanner sc = new Scanner(System.in);
        System.out.println("validate");
        System.out.println("sign");

        Integer opt = sc.nextInt();
        switch(opt) {
            case 1:
                // VALIDATION //
            case 2:
                // SIGNING //
            default:
                System.out.println("wrong command");
        }

        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");

        //Initializing the key pair generator
        keyPairGen.initialize(2048);

        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();

        //Getting the private key from the key pair
        PrivateKey privKey = pair.getPrivate();

        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withDSA");

        //Initialize the signature
        sign.initSign(privKey);
        byte[] bytes = "msg".getBytes();

        //Adding data to the signature
        sign.update(bytes);

        //Calculating the signature
        byte[] signature = sign.sign();

        //Printing the signature
        System.out.println("Digital signature for given text: "+new String(signature, "UTF8"));
    }
}
