package signature;

import java.io.Serializable;
import java.security.Signature;

public class SignatureMeta implements Serializable {
    private Signature digitalSignature;
    private byte[] signatureBytes;
    private String author;

    public Signature getdigitalSignature() {
        return this.digitalSignature;
    }

    public byte[] getSignatureBytes(){
        return this.signatureBytes;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setAuthor(String author){
        this.author = author;
    }
    public void setDigitalSignature(Signature signature){
        this.digitalSignature = signature;
    }

    public void setSignBytes(byte[] signatureBytes){
        this.signatureBytes = signatureBytes;
    }

    public SignatureMeta(Signature sign, byte[] signBytes, String author){
        this.setDigitalSignature(sign);
        this.setSignBytes(signBytes);
        this.setAuthor(author);
    }

}
