package signature;

import java.io.IOException;
import java.nio.file.Path;
import java.security.*;

public interface DigitalSignatureInterface {
    @Deprecated
    boolean verifySignature(Path path) throws InvalidKeyException, SignatureException, IOException;

    boolean verifySignature(SignatureFile file, byte[] message) throws InvalidKeyException, SignatureException, IOException, NoSuchAlgorithmException;

    void type_format(Key key);

    void print_signature();

    void createSignature(PrivateKey key, String signature) throws NoSuchAlgorithmException, InvalidKeyException;

    @Deprecated
    void initializeSignature() throws NoSuchAlgorithmException, InvalidKeyException;

    void generateKeyPair() throws NoSuchAlgorithmException;

    @Deprecated
    void updateSignature() throws SignatureException;

    SignatureFile createSignatureFile(byte[] message) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException;

    @Deprecated
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

    String getAuthor();

    void setAuthor(String author);
}
