package signature;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;

public interface DigitalSignatureInterface {
    void verifySignature(Path path) throws InvalidKeyException, SignatureException, IOException;

    void type_format(Key key);
    void print_signature();

    void createSignature(PrivateKey key, String signature)throws NoSuchAlgorithmException, InvalidKeyException;

    void generateKeyPair() throws NoSuchAlgorithmException;

    void updateSignature() throws SignatureException;

    void calculateSignature(boolean save) throws SignatureException, IOException;



    void calculateMessageBytes(Path path) throws IOException;

    void setSignature(Signature signature);

    void setPublicKey(PublicKey publicKey);

    void setPrivateKey(PrivateKey privateKey);
    void setSignBytes(byte[] signatureBytes);
    void setKeyPair(KeyPair keyPair);

    void setMsgBytes(byte[] msgBytes);

    void setKeyPairGen(KeyPairGenerator keyPairGen);

    PublicKey getPublicKey();
    PrivateKey getPrivateKey();
    Signature getSignature();
    byte[] getSignBytes();
    byte[] getMsgBytes();
    KeyPair getKeyPair();
    KeyPairGenerator getKeyPairGenerator();
}
