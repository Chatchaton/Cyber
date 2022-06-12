package signature;

import java.io.Serial;
import java.io.Serializable;

public class SignatureMeta implements Serializable {
    @Serial
    private static final long serialVersionUID = 4885590894878948338L;
    private byte[] signatureBytes;
    private String author;
    private String hashAlgorithm;


    public SignatureMeta(byte[] signBytes, String author) {
        this.setSignBytes(signBytes);
        this.setAuthor(author);
    }

    public byte[] getSignatureBytes() {
        return this.signatureBytes;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSignBytes(byte[] signatureBytes) {
        this.signatureBytes = signatureBytes;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }
}
