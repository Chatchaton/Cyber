import menuComponents.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import signature.*;

public class Main {
    public static void main(String[] args) throws Exception {


        DigitalSignature digitalSignature = new DigitalSignature("SHA256withRSA");
        //digitalSignature.modifyUserMessage("Hello World");
        File dummyfile = new File("src\\msg.txt");
        digitalSignature.calculateMessageBytes(Path.of(dummyfile.getAbsolutePath()));
        digitalSignature.calculateSignature(true);
        digitalSignature.print_signature();


        //chosen file to be verified
        File dummyfile2 = new File("src\\msg.txt");
        digitalSignature.calculateMessageBytes(Path.of(dummyfile2.getAbsolutePath()));
       // digitalSignature.calculateSignature(false);


        //chosen signature is gonna be user input from file
        File dummySign = new File("src\\digital_signature");
        digitalSignature.verifySignature(Path.of(dummySign.getAbsolutePath()));

    }
}
