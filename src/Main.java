import menuComponents.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import signature.*;

public class Main {
    public static void main(String[] args) throws Exception {


        DigitalSignature digitalSignature = new DigitalSignature("SHA256withRSA");

        File dummyfile = new File("src\\msg.txt");
        digitalSignature.calculateMessageBytes(Path.of(dummyfile.getAbsolutePath()));
        digitalSignature.calculateSignature();
        digitalSignature.print_signature();

        SignatureMeta signatureMeta = new SignatureMeta(digitalSignature.getSignBytes(),"Auth");
        FileOutputStream fileOut = new FileOutputStream("src\\Meta.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(signatureMeta);
        out.close();
        fileOut.close();



        //chosen file to be verified
        File dummyfile2 = new File("src\\msg.txt");
        digitalSignature.calculateMessageBytes(Path.of(dummyfile2.getAbsolutePath()));
       // digitalSignature.calculateSignature(false);


        //chosen Signature is gonna be user input from file
        File dummySign = new File("src\\digital_signature");
        digitalSignature.verifySignature(Path.of(dummySign.getAbsolutePath()));


    }
}
