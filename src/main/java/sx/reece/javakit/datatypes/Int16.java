package sx.reece.javakit.datatypes;

import sx.reece.javakit.utils.DataUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static sx.reece.javakit.utils.DataUtils.reverse;

/**
 | Author: Reece Wilson (Reece.SX @not_rsx)
 | Type: Work in progress
 | License:
 |  Copyright (C) Reece.SX - MIT
 */
public class Int16 implements DataTypeEntity {
    private short value;
    public static final short MAX_VALUE = 32767;
    public static final short MIN_VALUE = -32768;
    public static final long BYTES = 2;

    public Int16() {
    }

    public Int16(short v) {
        this.value = v;
    }

    public Int16(int v) {
        if (v > MAX_VALUE) throw new IllegalArgumentException("Int16 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("Int16 underflow!");
        this.value = (short) v;
    }

    public Int16(byte[] b, boolean isLe) {
        value = ByteBuffer.wrap(b).order(isLe ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN).getShort();
    }

    public Int16(byte[] b, ByteOrder order) {
        this(b, order.equals(ByteOrder.LITTLE_ENDIAN));
    }

    public int getValue() {
        return this.value;
    }

    public int setValue(short value) {
        return this.value = value;
    }

    public static short getFromBuffer(byte[] buffer, boolean isLe) {
        if (buffer.length < BYTES) throw new IllegalArgumentException("Byte array must contain at least " + BYTES + " bytes");
        if (!isLe)
            buffer = DataUtils.reverse(buffer);
        return (short) ((buffer[0] & 0xFF) |
                        (buffer[1] & 0xFF) << 8);
    }

    public byte[] asBytes() {
        return asBytes(value, true);
    }
    public byte[] asBytesBE() {
        return asBytes(value, false);
    }

    public static byte[] asBytes(short value, boolean isLe) {
        byte[] b = new byte[2];
        b[0] = (byte) (value);
        b[1] = (byte) (value >> 8);
        return !isLe ? reverse(b) : b;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public Int16 subtract(short val) {
        return new Int16(this.value - val);
    }

    public Int16 add(short val) {
        return new Int16(this.value + val);
    }

    public Int16 divide(short val) {
        return new Int16(this.value / val);
    }

    public Int16 multiply(short val) {
        return new Int16(this.value * val);
    }

    public Int16 subtract(Int16 val) {
        return new Int16(this.value - val.value);
    }

    public Int16 add(Int16 val) {
        return new Int16(this.value + val.value);
    }

    public Int16 divide(Int16 val) {
        return new Int16(this.value / val.value);
    }

    public Int16 multiply(Int16 val) {
        return new Int16(this.value * val.value);
    }

    public Int16 subtract(UInt16 val) {
        return new Int16(this.value - val.getValue());
    }

    public Int16 add(UInt16 val) {
        return new Int16(this.value + val.getValue());
    }

    public Int16 divide(UInt16 val) {
        return new Int16(this.value / val.getValue());
    }

    public Int16 multiply(UInt16 val) {
        return new Int16(this.value * val.getValue());
    }
}
