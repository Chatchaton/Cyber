package signature;

import java.io.Serializable;
import java.security.Signature;

public class SignatureMeta implements Serializable {
    private byte[] signatureBytes;
    private String author;



    public byte[] getSignatureBytes(){
        return this.signatureBytes;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setSignBytes(byte[] signatureBytes){
        this.signatureBytes = signatureBytes;
    }

    public SignatureMeta(byte[] signBytes, String author){

        this.setSignBytes(signBytes);
        this.setAuthor(author);
    }

}
