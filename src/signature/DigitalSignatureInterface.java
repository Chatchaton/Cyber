package signature;

import java.io.IOException;
import java.nio.file.Path;
import java.security.*;

public interface DigitalSignatureInterface {
    boolean verifySignature(Path path) throws InvalidKeyException, SignatureException, IOException;

    void type_format(Key key);

    void print_signature();

    void createSignature(PrivateKey key, String signature) throws NoSuchAlgorithmException, InvalidKeyException;

    void initializeSignature() throws NoSuchAlgorithmException, InvalidKeyException;

    void generateKeyPair() throws NoSuchAlgorithmException;

    void updateSignature() throws SignatureException;

    void calculateSignature() throws SignatureException, IOException;

    void saveSignature(Path path) throws IOException;

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

    String getKeyAlgorithm();

    void setKeyAlgorithm(String keyAlgorithm);

    String[] getSupportedKeyAlgorithms();

    String getHashAlgorithm();

    void setHashAlgorithm(String hashAlgorithm);

    String[] getSupportedHashAlgorithms();
}
