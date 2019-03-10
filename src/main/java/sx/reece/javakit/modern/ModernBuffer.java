package sx.reece.javakit.modern;
/**
 | Author: Reece Wilson (Reece.SX @not_rsx)
 | Type: Work in progress
 | License:
 |  Copyright (C) Reece.SX - MIT
 */

import sx.reece.javakit.csharp.BitConverter;
import sx.reece.javakit.datatypes.*;
import sx.reece.javakit.logger.Logger;
import sx.reece.javakit.stream.ModernInputStreamReader;
import sx.reece.javakit.stream.ModernOutputStreamWriter;
import sx.reece.javakit.stream.RuntimeIOException;
import sx.reece.javakit.utils.DataUtils;
import sx.reece.javakit.utils.Mutex;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import static sx.reece.javakit.utils.DataUtils.reverse;

/**
 * I'm not even going to document this due to the vast amount of functions. Use common sense.
 * @author      Ghost (reece, me@reece.sx)
 * @version     0.1
 */
public class ModernBuffer {
    protected byte[] internalData = new byte[0];
    protected int index = 0;
    private Mutex mutex = new Mutex();

    public ModernBuffer() {
    }

    public ModernBuffer(byte[] data) {
        this.internalData = data;
    }

    public ModernBuffer(byte[] data, boolean toEnd) {
        this.internalData = data;
        this.index = toEnd ? data.length : 0;
    }

    public ModernBuffer(ByteBuffer bb, boolean respectPosition) {
        this.internalData = bb.array();
        this.index = respectPosition ? bb.position() : 0;
    }

    ///////////////////////////////////////////////////////////////////////
    // Stream Stuff
    ///////////////////////////////////////////////////////////////////////

    private boolean isReadonlyStream() {
        return this instanceof ModernInputStreamReader;
    }

    private boolean isWriteOnlyStream() {
        return this instanceof ModernOutputStreamWriter;
    }

    ///////////////////////////////////////////////////////////////////////
    // Buffer Stuff
    ///////////////////////////////////////////////////////////////////////

    /**
     * Get the internal puffer
     */
    public byte[] getBytes() {
        return internalData;
    }


    /**
     * Get a new instance of the Java ByteBuffer with the current bytes
     */
    public ByteBuffer getByteBuffer() {
        if (isReadonlyStream() || isWriteOnlyStream())
            throw new RuntimeException("Can not provide a buffer for streams.");
        return ByteBuffer.wrap(internalData);
    }

    /**
     * Get a new instance of the byte buffer at the current pos
     */
    public ByteBuffer toByteBuffer() {
        if (isReadonlyStream() || isWriteOnlyStream())
            throw new RuntimeException("Can not provide a buffer for streams.");
        int length = remainingBytes();
        byte[] newArray = new byte[length];
        System.arraycopy(internalData, index, newArray, 0, length);
        return ByteBuffer.wrap(newArray);
    }

    /**
     * Slice
     */
    public ModernBuffer slice(int start, int length) {
        if (isReadonlyStream() || isWriteOnlyStream())
            throw new RuntimeException("Can not provide a buffer for streams.");
        byte[] buffer = new byte[length];
        System.arraycopy(internalData, start, buffer, 0, length);
        return ModernBuffer.fromBytes(buffer);
    }

    /**
     * Get the length
     */
    public int getLength() {
        if (isReadonlyStream() || isWriteOnlyStream())
            throw new RuntimeException("Can not provide a buffer for streams.");
        return internalData.length;
    }

    /**
     * Set the index / pos
     */
    public ModernBuffer setIndex(int index) {
        if (isReadonlyStream() || isWriteOnlyStream())
            throw new RuntimeException("Can not provide a buffer for streams.");
        this.index = index;
        return this;
    }

    /**
     * Get the index / pos
     */
    public int getIndex() {
        if (isReadonlyStream() || isWriteOnlyStream())
            throw new RuntimeException("Can not provide a buffer for streams.");
        return index;
    }

    /**
     * Get the remaining bytes in the buffer
     */

    public byte getNextByte() {
        if (isReadonlyStream() || isWriteOnlyStream())
            throw new RuntimeException("Can not provide a buffer for streams.");
        return internalData[index];
    }

    public int remainingBytes() {
        if (isReadonlyStream() || isWriteOnlyStream())
            throw new RuntimeException("Can not provide a buffer for streams.");
        return internalData.length - index;
    }

    private ModernBuffer resize(int len) {
        if (isReadonlyStream() || isWriteOnlyStream())
            throw new RuntimeException("Can not provide a buffer for streams.");
        byte[] newArray = new byte[len + internalData.length];
        System.arraycopy(internalData, 0, newArray, 0, internalData.length);
        internalData = newArray;
        return this;
    }

    public ModernBuffer skip(int skip) {
        if (isReadonlyStream() || isWriteOnlyStream())
            throw new RuntimeException("Can not provide a buffer for streams.");
        index += skip;
        return this;
    }

    public ModernBuffer goBack(int index) {
        if (isReadonlyStream() || isWriteOnlyStream())
            throw new RuntimeException("Can not provide a buffer for streams.");
        skip(-index);
        return this;
    }

    public void reset() {
        if (isReadonlyStream() || isWriteOnlyStream())
            throw new RuntimeException("Can not provide a buffer for streams.");
        this.internalData = new byte[0];
        this.index = 0;
    }

    ///////////////////////////////////////////////////////////////////////
    // Read/write functions for the internal buffer/stream
    ///////////////////////////////////////////////////////////////////////

    public void flush() {
        if (isWriteOnlyStream()) {
            try {
                ((ModernOutputStreamWriter) this).getOs().flush();
            } catch (Exception e) {
                throw new RuntimeIOException(e);
            }
            return;
        }
        this.internalData = readBytes(remainingBytes());
        this.index = 0;
    }

    //read/write
    public ModernBuffer writeBytes(byte[] data) {
        if (isReadonlyStream()) throw new RuntimeIOException("Illegal write request on input stream");
        if (isWriteOnlyStream()) {
            try {
                ((ModernOutputStreamWriter) this).getOs().write(data);
            } catch (Exception e) {
                throw new RuntimeIOException(e);
            }
            return this;
        }

        mutex.lockPerformance();

        if (data.length + index > internalData.length) {
            byte[] newArray = new byte[data.length + index];
            System.arraycopy(internalData, 0, newArray, 0, internalData.length);
            internalData = newArray;
        }

        System.arraycopy(data, 0, internalData, index, data.length);
        index += data.length;

        mutex.unlock();
        return this;
    }


    /**
     * Warning: may throw RuntimeIOException
     *
     * @param length
     * @return
     */
    public byte[] readBytes(int length) {
        InputStream is;
        byte[] buffer = new byte[length];

        mutex.tryLock();
        if (isReadonlyStream()) {
            is = ((ModernInputStreamReader) this).getIs();
            try {
                if (is.read(buffer) == -1) throw new RuntimeIOException("Failed to read bytes from stream.");
            } catch (IOException e) {
                throw new RuntimeIOException(e);
            }
        } else {
            System.arraycopy(internalData, index, buffer, 0, length);
            index += length;
        }

        mutex.unlock();
        return buffer;
    }

    public ModernBuffer pushBytesSilently(byte[] data) {
        resize(data.length);
        System.arraycopy(data, 0, internalData, internalData.length - data.length, data.length);
        return this;
    }

    ///////////////////////////////////////////////////////////////////////
    // Read/write most (if not all) java datatypes and some
    ///////////////////////////////////////////////////////////////////////

    //int32
    public ModernBuffer writeInt32LE(int data) {
        writeBytes(Int32.asBytes(data, true));
        return this;
    }

    public ModernBuffer writeInt32BE(int data) {
        writeBytes(Int32.asBytes(data, false));
        return this;
    }

    public ModernBuffer writeInt32(ByteOrder order, int data) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            writeInt32LE(data);
        } else {
            writeInt32BE(data);
        }
        return this;
    }

    public int readInt32LE() {
        return Int32.getFromBuffer(readBytes(4), true);
    }

    public int readInt32BE() {
        return Int32.getFromBuffer(readBytes(4), false);
    }

    public int readInt32(ByteOrder order) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            return readInt32LE();
        } else {
            return readInt32BE();
        }
    }

    //bytes
    public int readUnsignedByte() {
        return Byte.toUnsignedInt(readByte());
    }

    public ModernBuffer writeUnsignedByte(int b) {
        writeBytes(new byte[]{(byte) (b & 0xff)});
        return this;
    }

    public byte readByte() {
        return readBytes(1)[0];
    }

    public ModernBuffer writeByte(byte b) {
        writeBytes(new byte[]{b});
        return this;
    }

    public ModernBuffer writeByte(int b) {
        writeBytes(new byte[]{(byte) b});
        return this;
    }

    //uint32
    public ModernBuffer writeUInt32BE(UInt32 data) {
        byte[] toWrite = data.asBytes();
        reverse(toWrite);
        writeBytes(toWrite);
        return this;
    }

    public ModernBuffer writeUInt32LE(UInt32 data) {
        writeBytes(data.asBytes());
        return this;
    }

    public ModernBuffer writeUInt32BE(long data) {
        writeBytes(UInt32.asBytes(data, false));
        return this;
    }

    public ModernBuffer writeUInt32LE(long data) {
        writeBytes(UInt32.asBytes(data, true));
        return this;
    }

    public UInt32 readUInt32LE() {
        return new UInt32(readBytes(4), true);
    }

    public UInt32 readUInt32BE() {
        return new UInt32(readBytes(4), false);
    }

    public ModernBuffer writeUInt32(ByteOrder order, UInt32 data) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            writeUInt32LE(data);
        } else {
            writeUInt32BE(data);
        }
        return this;
    }

    public ModernBuffer writeUInt16(ByteOrder order, long data) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            writeUInt32LE(data);
        } else {
            writeUInt32BE(data);
        }
        return this;
    }

    public UInt32 readUInt32(ByteOrder order) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            return readUInt32LE();
        } else {
            return readUInt32BE();
        }
    }

    //uint16
    public ModernBuffer writeUInt16BE(UInt16 data) {
        byte[] toWrite = data.asBytes();
        reverse(toWrite);
        writeBytes(toWrite);
        return this;
    }

    public ModernBuffer writeUInt16LE(UInt16 data) {
        writeBytes(data.asBytes());
        return this;
    }

    public UInt16 readUInt16LE() {
        return new UInt16(readBytes(2), true);
    }

    public UInt16 readUInt16BE() {
        return new UInt16(readBytes(2), false);
    }

    public ModernBuffer writeUInt16BE(int data) {
        writeBytes(UInt16.asBytes(data, false));
        return this;
    }

    public ModernBuffer writeUInt16LE(int data) {
        writeBytes(UInt16.asBytes(data, true));
        return this;
    }

    public ModernBuffer writeUInt16(ByteOrder order, int data) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            writeUInt16LE(data);
        } else {
            writeUInt16BE(data);
        }
        return this;
    }

    public ModernBuffer writeUInt16(ByteOrder order, UInt16 data) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            writeUInt16LE(data);
        } else {
            writeUInt16BE(data);
        }
        return this;
    }

    public UInt16 readUInt16(ByteOrder order) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            return readUInt16LE();
        } else {
            return readUInt16BE();
        }
    }

    //int16
    public ModernBuffer writeInt16LE(short data) {
        writeBytes(Int16.asBytes(data, true));
        return this;
    }

    public ModernBuffer writeInt16BE(short data) {
        writeBytes(Int16.asBytes(data, false));
        return this;
    }

    public short readInt16LE() {
        return Int16.getFromBuffer(readBytes(2), true);
    }

    public short readInt16BE() {
        return Int16.getFromBuffer(readBytes(2), false);
    }

    public ModernBuffer writeInt16(ByteOrder order, short data) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            writeInt16LE(data);
        } else {
            writeInt16BE(data);
        }
        return this;
    }

    public short readInt16(ByteOrder order) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            return readInt16LE();
        } else {
            return readInt16BE();
        }
    }

    //float
    public ModernBuffer writeFloatLE(float data) {
        writeBytes(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(data).array());
        return this;
    }

    public ModernBuffer writeFloatBE(float data) {
        writeBytes(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putFloat(data).array());
        return this;
    }

    public float readFloatLE() {
        return ByteBuffer.wrap(readBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public float readFloatBE() {
        return ByteBuffer.wrap(readBytes(4)).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    public ModernBuffer writeFloat(ByteOrder order, float data) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            writeFloatLE(data);
        } else {
            writeFloatBE(data);
        }
        return this;
    }

    public float readFloat(ByteOrder order) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            return readFloatLE();
        } else {
            return readFloatBE();
        }
    }

    //double
    public ModernBuffer writeDoubleLE(double data) {
        writeBytes(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(data).array());
        return this;
    }

    public ModernBuffer writeDoubleBE(double data) {
        writeBytes(ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putDouble(data).array());
        return this;
    }

    public double readDoubleLE() {
        return ByteBuffer.wrap(readBytes(8)).order(ByteOrder.LITTLE_ENDIAN).getDouble();
    }

    public double readDoubleBE() {
        return ByteBuffer.wrap(readBytes(8)).order(ByteOrder.BIG_ENDIAN).getDouble();
    }

    public ModernBuffer writeDouble(ByteOrder order, float data) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            writeDoubleLE(data);
        } else {
            writeDoubleBE(data);
        }
        return this;
    }

    public double readDouble(ByteOrder order) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            return readDoubleLE();
        } else {
            return readDoubleBE();
        }
    }

    //uint64
    public ModernBuffer writeUInt64BE(UInt64 data) {
        byte[] toWrite = data.asBytes();
        reverse(toWrite);
        writeBytes(toWrite);
        return this;
    }

    public ModernBuffer writeUInt64LE(UInt64 data) {
        writeBytes(data.asBytes());
        return this;
    }

    public ModernBuffer writeUInt64BE(BigInteger data) {
        writeUInt64BE(new UInt64(data));
        return this;
    }

    public ModernBuffer writeUInt64LE(BigInteger data) {
        writeUInt64LE(new UInt64(data));
        return this;
    }

    public ModernBuffer writeUInt64LE(String data) {
        writeUInt64LE(new UInt64(data));
        return this;
    }

    public ModernBuffer writeUInt64LE(long data) {
        writeUInt64LE(new UInt64(data));
        return this;
    }

    public ModernBuffer writeUInt64BE(String data) {
        writeUInt64BE(new UInt64(data));
        return this;
    }

    public ModernBuffer writeUInt64BE(long data) {
        writeUInt64BE(new UInt64(data));
        return this;
    }

    public UInt64 readUInt64LE() {
        byte[] toRead = readBytes(8);
        reverse(toRead);
        return new UInt64(toRead);
    }

    public UInt64 readUInt64BE() {
        byte[] toRead = readBytes(8);
        return new UInt64(toRead);
    }

    public ModernBuffer writeUInt64(ByteOrder order, UInt64 data) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            writeUInt64LE(data);
        } else {
            writeUInt64BE(data);
        }
        return this;
    }

    public UInt64 readUInt64(ByteOrder order) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            return readUInt64LE();
        } else {
            return readUInt64BE();
        }
    }

    //int64
    public ModernBuffer writeInt64LE(long data) {
        writeBytes(Int64.asBytes(data, true));
        return this;
    }

    public ModernBuffer writeInt64BE(long data) {
        writeBytes(Int64.asBytes(data, false));
        return this;
    }

    public long readInt64LE() {
        return Int64.getFromBuffer(readBytes(8), true);
    }

    public long readInt64BE() {
        return Int64.getFromBuffer(readBytes(8), false);
    }

    public ModernBuffer writeUnt64(ByteOrder order, long data) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            writeInt64LE(data);
        } else {
            writeInt64BE(data);
        }
        return this;
    }

    public long readInt64(ByteOrder order) {
        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            return readInt64LE();
        } else {
            return readInt64BE();
        }
    }

    //string
    public ModernBuffer writeString(String str) {
        writeBytes(str.getBytes());
        return this;
    }

    public ModernBuffer writeStringUTF16BE(String str) {
        writeBytes(str.getBytes(Charset.forName("UTF-16BE")));
        return this;
    }

    public ModernBuffer writeStringUTF16LE(String str) {
        writeBytes(str.getBytes(Charset.forName("UTF-16LE")));
        return this;
    }

    public ModernBuffer writeString(String str, Charset charset) {
        writeBytes(str.getBytes(charset));
        return this;
    }

    public ModernBuffer writeLString(String str) {
        writeLString(str, Charset.forName("utf8"));
        return this;
    }

    public ModernBuffer writeLStringUTF16BE(String str) {
        writeLString(str, Charset.forName("UTF-16BE"));
        return this;
    }

    public ModernBuffer writeLStringUTF16LE(String str) {
        writeLString(str, Charset.forName("UTF-16LE"));
        return this;
    }

    public ModernBuffer writeLString(String str, Charset charset) {
        writeUInt32LE(new UInt32(str.length()));
        writeBytes(str.getBytes(charset));
        return this;
    }

    public String readString(int length) {
        return new String(readBytes(length));
    }

    public String readStringUTF16(int length, boolean le) {
        try {
            return new String(readBytes(length * 2), le ? "UTF-16LE" : "UTF-16BE");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String readLString() {
        return new String(readBytes(readInt32LE()));
    }

    public String readLStringUTF16LE() {
        return readLStringUTF16(true);
    }

    public String readLStringUTF16BE() {
        return readLStringUTF16(false);
    }

    public String readLStringUTF16(boolean le) {
        return readStringUTF16(readInt32LE(), le);
    }

    public ModernBuffer writeCString(String data) {
        writeString(data);
        writeByte(0x00);
        return this;
    }

    public ModernBuffer writeCStringUTF16LE(String data) {
        writeCStringUTF16(data, true);
        return this;
    }

    public ModernBuffer writeCStringUTF16BE(String data) {
        writeCStringUTF16(data, false);
        return this;
    }

    public ModernBuffer writeCStringUTF16(String data, boolean LE) {
        if (LE)
            writeStringUTF16LE(data);
        else
            writeStringUTF16BE(data);
        writeByte(0x00);
        writeByte(0x00);
        return this;
    }

    public String readCString() {
        ModernBuffer bb = newInstance();
        byte cur;
        while ((cur = readBytes(1)[0]) != 0x00) {
            bb.writeByte(cur);
        }
        try {
            return new String(bb.getBytes(), "utf8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public String readByteLenPrefixedString() {
        return readString(readUnsignedByte());
    }

    public ModernBuffer writeByteLenPrefixedString(String str) {
        writeUnsignedByte(str.length());
        writeString(str);
        return this;
    }
    public String readByteLenPrefixedStringUTF16LE() {
        return readStringUTF16(readUnsignedByte(), true);
    }

    public String readByteLenPrefixedStringUTF16BE() {
        return readStringUTF16(readUnsignedByte(), false);
    }

    public ModernBuffer writeByteLenPrefixedStringUTF16LE(String str) {
        writeUnsignedByte(str.length());
        writeStringUTF16LE(str);
        return this;
    }
    public ModernBuffer writeByteLenPrefixedStringUTF16BE(String str) {
        writeUnsignedByte(str.length());
        writeStringUTF16BE(str);
        return this;
    }

    public String readCStringUTF16() {
        return readCStringUTF16(true);
    }
    /**
     * Read utf16-0x00-0x00 from stream
     */
    public String readCStringUTF16(boolean leastsigLE) {
        ModernBuffer buffer = ModernBuffer.newInstance();
        byte cur;
        byte second;
        try {
            while (true) {
                cur = readByte();
                second = readByte();

                if (cur == 0x00 && second == 0x00)
                    break;

                buffer.writeByte(cur);
                buffer.writeByte(second);
            }
        } catch (Exception e) {
            throw new RuntimeIOException(e);
        }
        try {
            return new String(buffer.slice(0, buffer.getLength()).getBytes(), leastsigLE ? "UTF-16LE" : "UTF-16BE");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //misc
    public ModernBuffer writeHex(String eh) {
        writeBytes(DatatypeConverter.parseHexBinary(eh.replaceAll(" ", "").replaceAll("-", "")));
        return this;
    }


    public static ModernBuffer fromHex(String hex) {
        return new ModernBuffer(DataUtils.getBytes(hex));
    }

    public static ModernBuffer fromBytes(byte[] bytes) {
        return new ModernBuffer(bytes);
    }

    public static ModernBuffer newInstance() {
        return new ModernBuffer();
    }


    @Override
    public String toString() {
        return BitConverter.toString(getBytes());
    }
}
