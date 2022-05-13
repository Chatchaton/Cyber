import java.security.*;

public interface DigitalSignatureInterface {
    void type_format(Key key);
    void print_signature();

    void generateKeyPair() throws NoSuchAlgorithmException;

    void createSignature(PrivateKey key) throws NoSuchAlgorithmException, InvalidKeyException;

    void updateSignature() throws SignatureException;

    void calculateSignature() throws SignatureException;

    void modifyUserMessage(String message) throws InvalidKeyException, SignatureException;

    void verifySignature() throws InvalidKeyException, SignatureException;

    void setSignature(Signature signature);

    void setPublicKey(PublicKey publicKey);

    void setPrivateKey(PrivateKey privateKey);
    void setSignBytes(byte[] signatureBytes);
    void setKeyPair(KeyPair keyPair);
    void setMsgBytes(String msg);
    void setKeyPairGen(KeyPairGenerator keyPairGen);

    PublicKey getPublicKey();
    PrivateKey getPrivateKey();
    Signature getSignature();
    byte[] getSignBytes();
    byte[] getMsgBytes();
    KeyPair getKeyPair();
    KeyPairGenerator getKeyPairGenerator();
}
