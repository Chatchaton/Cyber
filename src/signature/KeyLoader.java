package signature;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.InputMismatchException;

public class KeyLoader {

    private static String substringBetween(String source, String begin, String end) {
        var beginIndex = source.indexOf(begin);
        if (beginIndex == -1) {
            throw new InputMismatchException("Begin string not found: \"" + begin + "\"");
        }
        var contentIndex = beginIndex + begin.length();
        var endIndex = source.indexOf(end, contentIndex);
        if (endIndex == -1) {
            throw new InputMismatchException("End string not found: \"" + end + "\"");
        }
        return source.substring(contentIndex, endIndex);
    }

    public static PublicKey readPublicKey(File file) {
        try {
            var allBytes = readAllBytes(file);
            var str = new String(allBytes);
            var stripped = substringBetween(str, "-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----");
            var bytes = Base64.getDecoder().decode(stripped);
            var spec = new X509EncodedKeySpec(bytes);
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey readPrivateKey(File file) {
        try {
            var allBytes = readAllBytes(file);
            var str = new String(allBytes);
            var stripped = substringBetween(str, "-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----");
            var bytes = Base64.getDecoder().decode(stripped);
            var spec = new PKCS8EncodedKeySpec(bytes);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] readAllBytes(File file) throws IOException {
        try (var stream = new FileInputStream(file)) {
            return stream.readAllBytes();
        }
    }

}
