package signature;

import ui.utils.OutputStreamHelper;
import ui.utils.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    public static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
    public static final String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";
    public static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
    public static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
    public static final String BEGIN_DSA_PRIVATE_KEY = "-----BEGIN DSA PRIVATE KEY-----";
    public static final String END_DSA_PRIVATE_KEY = "-----END DSA PRIVATE KEY-----";

    private static Result<String> substringBetween(String source, String begin, String end) {
        return Result.run(() -> {
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
        });
    }

    @Deprecated
    public static PublicKey readPublicKey(File file) {
        try {
            var allBytes = readAllBytes(file);
            var str = new String(allBytes);
            var spec =
                substringBetween(str, BEGIN_PUBLIC_KEY, END_PUBLIC_KEY)
                    .map(value -> value.replaceAll(System.lineSeparator(), ""))
                    .map(value -> Base64.getDecoder().decode(value))
                    .map(X509EncodedKeySpec::new)
                    .get();
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public static PrivateKey readPrivateKey(File file) {
        try {
            var allBytes = readAllBytes(file);
            var str = new String(allBytes);
            return substringBetween(str, BEGIN_PRIVATE_KEY, END_PRIVATE_KEY)
                .map(value -> value.replaceAll(System.lineSeparator(), ""))
                .map(value -> Base64.getDecoder().decode(value))
                .map(PKCS8EncodedKeySpec::new)
                .map(spec -> KeyFactory.getInstance("RSA").generatePrivate(spec))
                .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public static void writePrivateKey(PrivateKey key, File file) throws IOException {
        try (var stream = new FileOutputStream(file)) {
            new OutputStreamHelper(stream)
                .write(BEGIN_PRIVATE_KEY).newLine()
                .writeBase64(key.getEncoded()).newLine()
                .write(END_PRIVATE_KEY).newLine();
        }
    }

    @Deprecated
    public static void writePublicKey(PublicKey key, File file) throws IOException {
        try (var stream = new FileOutputStream(file)) {
            new OutputStreamHelper(stream)
                .write(BEGIN_PUBLIC_KEY).newLine()
                .writeBase64(key.getEncoded()).newLine()
                .write(END_PUBLIC_KEY).newLine();
        }
    }

    private static byte[] readAllBytes(File file) throws IOException {
        try (var stream = new FileInputStream(file)) {
            return stream.readAllBytes();
        }
    }

}
