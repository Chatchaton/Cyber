import java.nio.charset.StandardCharsets;
import java.security.*;

public class DigitalSignature implements DigitalSignatureInterface {
    private KeyPair keyPair;
    private Signature signature;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private byte[] signatureBytes;
    private byte[] msgBytes;


    public DigitalSignature() throws NoSuchAlgorithmException, InvalidKeyException {
        generateKeyPair();
        PrivateKey privateKey = getPrivateKey();
        createSignature(privateKey);
    }

    @Override
    public void generateKeyPair() throws NoSuchAlgorithmException {
        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");

        //Initializing the key pair generator
        keyPairGen.initialize(2048);

        //Generate the pair of keys
        setKeyPair( keyPairGen.generateKeyPair());
        setPrivateKey(getKeyPair().getPrivate());
        setPublicKey(getKeyPair().getPublic());
    }

    @Override
    public void createSignature(PrivateKey key) throws NoSuchAlgorithmException, InvalidKeyException {
        //Creating a Signature object
        setSignature(Signature.getInstance("SHA256withDSA"));
        getSignature().initSign(key);
    }

    @Override
    public void updateSignature() throws SignatureException {
        getSignature().update(getMsgBytes());
    }

    @Override
    public void calculateSignature() throws SignatureException {
        setSignBytes(getSignature().sign());
    }

    @Override
    public void modifyUserMessage(String message) throws SignatureException {
        setMsgBytes(message);
        updateSignature();

    }

    @Override
    public void verifySignature() throws InvalidKeyException, SignatureException {
        getSignature().initVerify(getPublicKey());
        updateSignature();
        boolean bool = getSignature().verify(getSignBytes());
        if(bool) {
            System.out.println("Signature verified");
        } else {
            System.out.println("Signature failed");
        }
    }


    @Override
    public void type_format(Key key) {
        System.out.println("Key format: " + key.getFormat());
    }

    @Override
    public void print_signature() {
        System.out.println("Digital signature for given text: "+new String(getSignBytes(), StandardCharsets.UTF_8));
    }

    @Override
    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    @Override
    public Signature getSignature() {
        return this.signature;
    }

    @Override
    public byte[] getSignBytes() {
        return this.signatureBytes;
    }

    @Override
    public byte[] getMsgBytes() {
        return this.msgBytes;
    }

    @Override
    public KeyPair getKeyPair() {
        return this.keyPair;
    }

    @Override
    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }


    @Override
    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public void setSignBytes(byte[] signatureBytes) {
        this.signatureBytes = signatureBytes;
    }

    @Override
    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @Override
    public void setMsgBytes(String msg) {
        this.msgBytes = msg.getBytes();
    }

    @Override
    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }



}