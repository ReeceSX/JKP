package sx.reece.javakit.stream;

import sx.reece.javakit.modern.ModernBuffer;

import java.io.IOException;
import java.io.OutputStream;

/**
 | Author: Reece Wilson (Reece.SX @not_rsx)
 | Type: Final
 | License:
 |  Copyright (C) Reece.SX - MIT
 */
public class ModernOutputStreamWriter extends ModernBuffer {
    private OutputStream os;
    public ModernOutputStreamWriter(OutputStream os) {
        this.os = os;
    }

    public void close() throws IOException {
        os.close();
    }

    public OutputStream getOs() {
        return os;
    }
}
