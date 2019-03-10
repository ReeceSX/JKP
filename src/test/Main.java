
import sx.reece.javakit.datatypes.Int64;
import sx.reece.javakit.datatypes.UInt16;
import sx.reece.javakit.datatypes.UInt32;
import sx.reece.javakit.datatypes.UInt64;
import sx.reece.javakit.logger.Logger;
import sx.reece.javakit.modern.ModernBuffer;
import sx.reece.javakit.stream.ModernInputStreamReader;

import java.io.IOException;
import java.net.Socket;


public class Main {

    public static void main(String[] args) throws Exception {
        ModernBuffer buf = ModernBuffer.fromHex("5400 6800 6900 7300 4900 7300 0041 0044 0061 006E 006B 0054 0065 0073 0074 00A3 0022 0024 0022 00A3 002400 00 01");
        Logger.log(buf.readCStringUTF16());
        Logger.log(buf.readUnsignedByte());
        Logger.log(new UInt32(3).add(5).divide(3));
        Test.start();
    }
}
