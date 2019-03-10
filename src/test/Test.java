import sx.reece.javakit.csharp.Console;
import sx.reece.javakit.datatypes.*;
import sx.reece.javakit.logger.JavaLogger;
import sx.reece.javakit.logger.Logger;
import sx.reece.javakit.logger.WinANSI;
import sx.reece.javakit.modern.ModernBuffer;
import sx.reece.javakit.modern.ModernFile;
import sx.reece.javakit.stream.ModernInputStreamReader;
import sx.reece.javakit.stream.ModernInputStreamReaderFromBuffer;
import sx.reece.javakit.utils.ArrayUtils;
import sx.reece.javakit.utils.DataUtils;
import sx.reece.javakit.utils.Mutex;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;

import static org.junit.Assert.assertArrayEquals;
import static sx.reece.javakit.utils.GeneralUtils.count;
import static sx.reece.javakit.utils.GeneralUtils.safeSleep;
import static org.junit.Assert.assertEquals;

/**
 * Created by RSX on 23/10/2016.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        start();
    }

    public static void assertEqualsBytes(byte[] a, byte[] b) throws Exception
    {
        if (!Arrays.equals(a, b))
        throw new Exception("Byte array doesn't match!");
    }
    public static void start() throws Exception {
        new ModernFile("test/test/danko/test/heyy.bin").outputStream().flush();

        //Array testing
        Logger.log(new Byte[]{0x21, 0x23, 0x69});
        Logger.log(new byte[]{0x21, 0x23, 0x69});
        Logger.log(ModernBuffer.fromBytes(ArrayUtils.toPrimitive(new Byte[]{0x21, 0x23, 0x69})));
        Logger.log(ModernBuffer.fromBytes(ArrayUtils.toPrimitive(ArrayUtils.toObject(ArrayUtils.toPrimitive(new Byte[]{0x21, 0x23, 0x69})))));

        //Logger testing
        JavaLogger.getLogger("").log(Level.SEVERE, "ERROR!!");
        JavaLogger.getLogger("jkit").log(Level.SEVERE, "ERROR!!");

        //Create file tests
        Logger.log(ModernFile.getFileOrCreateNew(String.format("test%sfile", File.separator)));
        Logger.log(ModernFile.getDirOrCreateNew(String.format("test%sdir%stest%sdir%s", File.separator, File.separator, File.separator, File.separator)));

        //Create file tests (Use linux separator on win)
        Logger.log(ModernFile.getFileOrCreateNew(String.format("test%sfileb", "/")));
        Logger.log(ModernFile.getDirOrCreateNew(String.format("test%sdirb%stest%sdir%s", "/", "/", "/", "/")));

        //stderr redirection
        new Exception().printStackTrace();

        //Logger stuff
        Logger.debug("Debug enabled!");
        Logger.warn(ModernFile.get("hello"));
        Logger.warn(new Socket("google.com", 80));

        //Mutex
        Mutex mutex = new Mutex();
        count(4, (int index) -> {
            Logger.log("dealing with thread %s", String.valueOf(index));
            new Thread(() -> {
                mutex.runSafely(() -> {
                    Logger.log("Mutex locked");
                    safeSleep(500);
                    Logger.log("Mutex unlocked");
                });
            }).start();
        });

        //ArrayUtils
        assertArrayEquals(new byte[]{0x01, 0x02, 0x03}, ArrayUtils.toPrimitive(new Byte[]{0x01, 0x02, 0x03}));
        assertArrayEquals(new Byte[]{0x01, 0x02, 0x03}, ArrayUtils.toObject(new byte[]{0x01, 0x02, 0x03}));
        assertArrayEquals(new int[]{1, 2, 3}, ArrayUtils.toPrimitive(new Integer[]{1, 2, 3}));
        assertArrayEquals(new Integer[]{1, 2, 3}, ArrayUtils.toObject(new int[]{1, 2, 3}));
        assertArrayEquals(new long[]{1, 2, 3}, ArrayUtils.toPrimitive(new Long[]{1L, 2L, 3L}));
        assertArrayEquals(new Long[]{1L, 2L, 3L}, ArrayUtils.toObject(new long[]{1, 2, 3}));
        assertArrayEquals(new short[]{1, 2, 3}, ArrayUtils.toPrimitive(new Short[]{1, 2, 3}));
        assertArrayEquals(new Short[]{1, 2, 3}, ArrayUtils.toObject(new short[]{1, 2, 3}));
        //assertArrayEquals(new double[]{1.3, 2.4, 3.5}, ArrayUtils.toPrimitive(new Double[]{1.3, 2.4, 3.5}));
        assertArrayEquals(new Double[]{1.3, 2.4, 3.5}, ArrayUtils.toObject(new double[]{1.3, 2.4, 3.5}));


        //Buffer
        Logger.log(ModernBuffer.fromHex("EA-FF-FF-FF-FF-FF-FF-FF").readUInt64LE());
        ModernBuffer bb = write(ModernBuffer.newInstance()).setIndex(0);
        Logger.log("AA: {}", bb);
        assertEquals(bb.readInt16BE(), 33);
        assertEquals(bb.readInt16LE(), 33);
        assertEquals(bb.readInt32BE(), 33);
        assertEquals(bb.readInt32LE(), 33);
        assertEquals(bb.readFloatBE(), Float.MAX_VALUE - 40, 0);
        assertEquals(bb.readFloatLE(), Float.MAX_VALUE - 40, 0);
        assertEquals(bb.readDoubleBE(), 50.70, 0);
        assertEquals(bb.readDoubleLE(), 50.70, 0);
        assertEquals(bb.readUInt32BE().getValue(), 4294967295L);
        assertEquals(bb.readUInt32LE().getValue(), 4294967295L);
        assertEquals(bb.readUInt32BE().getValue(), 4294967295L - 5);
        assertEquals(bb.readUInt32LE().getValue(), 4294967295L - 5);
        assertEquals(bb.readUInt16BE().getValue(), UInt16.MAX_VALUE);
        assertEquals(bb.readUInt16LE().getValue(), UInt16.MAX_VALUE - 5);
        assertEquals(bb.readUInt64LE().getValue(), UInt64.MAX_VALUE.subtract(BigInteger.TEN).subtract(BigInteger.TEN).subtract(BigInteger.ONE));
        assertEquals(bb.readUInt64LE().getValue(), UInt64.MIN_VALUE);
        assertEquals(bb.readUInt64LE().getValue(), UInt64.MAX_VALUE);

        ModernInputStreamReader MISR = new ModernInputStreamReader(new ModernInputStreamReaderFromBuffer(bb.setIndex(0)));
        assertEquals(MISR.readInt16BE(), 33);
        assertEquals(MISR.readInt16LE(), 33);
        assertEquals(MISR.readInt32BE(), 33);
        assertEquals(MISR.readInt32LE(), 33);
        //bah it works

        //UTF16 testing
        ModernBuffer utf16test = new ModernBuffer();
        utf16test.writeCStringUTF16LE("Hello worldЇЇ");
        utf16test.writeByteLenPrefixedStringUTF16LE("hi");
        utf16test.writeByteLenPrefixedString("hi");
        utf16test.writeLString("test utf8");
        utf16test.writeLStringUTF16LE("Testing ЇЇ utf ЇЇ 16");
        utf16test.setIndex(0);
        String hw = utf16test.readCStringUTF16(true);
        assertEquals(hw, "Hello worldЇЇ");
        assertEquals(hw.length(), "Hello worldЇЇ".length());
        assertEquals(utf16test.getIndex(), "Hello worldЇЇ".length() * 2 + 2);
        assertEquals(utf16test.readByteLenPrefixedStringUTF16LE(), "hi");
        assertEquals(utf16test.readByteLenPrefixedString(), "hi");
        assertEquals(utf16test.readLString(), "test utf8");
        assertEquals(utf16test.readLStringUTF16LE(), "Testing ЇЇ utf ЇЇ 16");
        Logger.debug(utf16test);

        //Test some socket stuff
        Logger.debug(new ModernInputStreamReader(new Socket("smtp.gmail.com", 25).getInputStream()).readString(18));


        assertEqualsBytes(new UInt32(3453466263L).asBytes(), DataUtils.getBytes("97-b6-d7-cd"));
        assertEquals(new UInt32(DataUtils.getBytes("97-b6-d7-cd"), true).getValue(), 3453466263L);

        //Int32 tests
        assertEqualsBytes(new Int32(345346623).asBytes(), DataUtils.getBytes("3f-92-95-14"));
        assertEquals(new Int32(DataUtils.getBytes("3f-92-95-14"), true).getValue(), 345346623);
        assertEqualsBytes(new Int32(-345346623).asBytes(), DataUtils.getBytes("c1-6d-6a-eb"));
        assertEquals(new Int32(DataUtils.getBytes("c1-6d-6a-eb"), true).getValue(), -345346623);


        //Int16 tests
        assertEqualsBytes(new Int16(-32761).asBytes(), DataUtils.getBytes("07-80"));
        assertEquals(new Int16(DataUtils.getBytes("07-80"), true).getValue(), -32761);
        assertEqualsBytes(new Int16(32761).asBytes(), DataUtils.getBytes("f9-7f"));
        assertEquals(new Int16(DataUtils.getBytes("f9-7f"), true).getValue(), 32761);

        //TODO: math tests. more getBytes & fromBytes tests

        Logger.log("... {}", new UInt64("18446744073709551611").asBytes() );
     }

    public static ModernBuffer write(ModernBuffer bb) {
        return bb.writeInt16BE((short)33)
                .writeInt16LE((short)33)
                .writeInt32BE(33)
                .writeInt32LE(33)
                .writeFloatBE(Float.MAX_VALUE - 40)
                .writeFloatLE(Float.MAX_VALUE - 40)
                .writeDoubleBE(50.70)
                .writeDoubleLE(50.70)
                .writeUInt32BE(4294967295L)
                .writeUInt32LE(4294967295L)
                .writeUInt32BE(4294967295L - 5)
                .writeUInt32LE(4294967295L - 5)
                .writeUInt16LE(UInt16.MAX_VALUE)
                .writeUInt16LE(UInt16.MAX_VALUE - 5)
                .writeUInt64LE(UInt64.MAX_VALUE.subtract(BigInteger.TEN).subtract(BigInteger.TEN).subtract(BigInteger.ONE))
                .writeUInt64LE(UInt64.MIN_VALUE)
                .writeUInt64LE(UInt64.MAX_VALUE);
    }
}
