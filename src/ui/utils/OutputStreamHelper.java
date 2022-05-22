package ui.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class OutputStreamHelper {
    private final OutputStream stream;

    public OutputStreamHelper(OutputStream stream) {
        this.stream = stream;
    }

    public OutputStreamHelper write(String str) throws IOException {
        stream.write(str.getBytes(StandardCharsets.UTF_8));
        return this;
    }

    public OutputStreamHelper write(byte[] bytes) throws IOException {
        stream.write(bytes);
        return this;
    }

    public OutputStreamHelper writeBase64(byte[] bytes) throws IOException {
        var encoded = Base64.getEncoder().encode(bytes);
        stream.write(encoded);
        return this;
    }

    public OutputStreamHelper newLine() throws IOException {
        write(System.lineSeparator());
        return this;
    }


}
