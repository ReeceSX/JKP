package sx.reece.javakit.stream;

import sx.reece.javakit.modern.ModernBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 | Author: Reece Wilson (Reece.SX @not_rsx)
 | Type: Final
 | License:
 |  Copyright (C) Reece.SX - MIT
 */
public class ModernInputStreamReader extends ModernBuffer {
    private InputStream is;

    public ModernInputStreamReader(InputStream is) {
        this.is = is;
    }

    public void close() throws IOException {
        is.close();
    }

    public InputStream getIs() {
        return is;
    }
}
