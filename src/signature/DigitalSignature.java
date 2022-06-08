package signature;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;

public class DigitalSignature implements DigitalSignatureInterface {
    private KeyPairGenerator keyPairGen;
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Signature signature;
    private byte[] signatureBytes;
    private byte[] msgBytes;
    private final String signatureType;


    public DigitalSignature(String signatureType) throws NoSuchAlgorithmException, InvalidKeyException {
        this.signatureType = signatureType;
        generateKeyPair();
        var privateKey = getPrivateKey();
        createSignature(privateKey, signatureType);
    }

    @Override
    public void createSignature(PrivateKey key, String signature) throws NoSuchAlgorithmException, InvalidKeyException {
        //Creating a Signature object
        setSignature(Signature.getInstance(signature));
        getSignature().initSign(key);
    }

    @Override
    public void initializeSignature() throws NoSuchAlgorithmException, InvalidKeyException {
        createSignature(privateKey, signatureType);
    }

    @Override
    public void generateKeyPair() throws NoSuchAlgorithmException {
        //Creating KeyPair generator object
        //TODO RSA and DSA depending on user input i.e SHA256withXXX
        setKeyPairGen(KeyPairGenerator.getInstance("RSA"));

        //Initializing the key pair generator
        getKeyPairGenerator().initialize(2048);

        //Generate the pair of keys
        setKeyPair( getKeyPairGenerator().generateKeyPair());
        setPrivateKey(getKeyPair().getPrivate());
        setPublicKey(getKeyPair().getPublic());
    }


    @Override
    //Update the data
    public void updateSignature() throws SignatureException {
        getSignature().update(getMsgBytes());
    }

    @Override
    public void calculateSignature() throws SignatureException {
        //Calculating the Signature
        updateSignature();
        setSignBytes(getSignature().sign());
    }

    @Override
    public void saveSignature(Path path) throws IOException {
        //save SignatureMeta
        Files.write(path,getSignBytes());
    }

    @Override
    public void calculateMessageBytes(Path path) throws IOException {
        setMsgBytes(Files.readAllBytes(path));
    }

    @Override
    public boolean verifySignature(Path path) throws InvalidKeyException, SignatureException, IOException {
        getSignature().initVerify(getPublicKey());
        updateSignature();
        byte[] sig = Files.readAllBytes(path);
        return getSignature().verify(sig);
    }


    @Override
    public void type_format(Key key) {
        System.out.println("Key format: " + key.getFormat());
    }

    @Override
    public void print_signature() {
        System.out.println("Digital SignatureMeta for given text: "+new String(getSignBytes(), StandardCharsets.UTF_8));
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
    public KeyPairGenerator getKeyPairGenerator() {
        return this.keyPairGen;
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
    public void setMsgBytes(byte[] msgBytes) {
        this.msgBytes = msgBytes;
    }

    @Override
    public void setKeyPairGen(KeyPairGenerator keyPairGen) {
        this.keyPairGen = keyPairGen;
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
