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
public class ModernOutputStreamToBuffer extends OutputStream {
    private ModernBuffer buffer = new ModernBuffer();

    @Override
    public void write(int b) throws IOException {
        buffer.pushBytesSilently(new byte[]{(byte)b});
    }

    public ModernBuffer getBuffer() {
        return buffer;
    }
}
