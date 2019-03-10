package sx.reece.javakit.stream;

import sx.reece.javakit.modern.ModernBuffer;

import java.io.IOException;
import java.io.InputStream;

/**
 | Author: Reece Wilson (Reece.SX @not_rsx)
 | Type: Final
 | License:
 |  Copyright (C) Reece.SX - MIT
 */
public class ModernInputStreamReaderFromBuffer extends InputStream {
    private int index = -1;
    private ModernBuffer buffer = new ModernBuffer();

    public ModernInputStreamReaderFromBuffer(ModernBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public int read() throws IOException {
        byte[] buf = buffer.getBytes();
        index ++;
        while (index > buf.length) {
            buf = buffer.getBytes();
        }
        int ret = buf[index];
        return ret;
    }

    public ModernBuffer getBuffer() {
        return buffer;
    }
}
