package signature;

import java.io.Serial;
import java.io.Serializable;

public class SignatureFile implements Serializable {
    @Serial
    private static final long serialVersionUID = 8343748639666251755L;

    private byte[] signature;
    private String author;
    private String hashAlgorithm;

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }
}