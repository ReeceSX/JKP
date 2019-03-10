package sx.reece.javakit.datatypes;

import java.nio.ByteOrder;

import static sx.reece.javakit.utils.ArrayUtils.reverse;

/**
 | Author: Reece Wilson (Reece.SX @not_rsx)
 | Type: Work in progress
 | License:
 |  Copyright (C) Reece.SX - MIT
 */
public class UInt16 implements DataTypeEntity {
    private int value;
    public static final int MAX_VALUE = 65535;
    public static final int MIN_VALUE = 0;
    public static final long BYTES = 2;

    public UInt16() {}

    public UInt16(int v) {
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt16 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt16 underflow!");
        this.value = v;
    }
    public UInt16(long v) {
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt16 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt16 underflow!");
        this.value = (int) v;
    }
    public UInt16(short v) {
        this.value = (int) v;
    }
    public UInt16(Int32 dtype) {
        long v = dtype.getValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt16 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt16 underflow!");
        this.value = (int) v;
    }
    public UInt16(Int16 dtype) {
        long v = dtype.getValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt16 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt16 underflow!");
        this.value = (int) v;
    }
    public UInt16(Int64 dtype) {
        long v = dtype.getValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt16 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt16 underflow!");
        this.value = (int) v;
    }
    public UInt16(UInt32 dtype) {
        long v = dtype.getValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt16 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt16 underflow!");
        this.value = (int) v;
    }
    public UInt16(UInt16 dtype) {
        long v = dtype.getValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt16 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt16 underflow!");
        this.value = (int) v;
    }
    public UInt16(UInt64 dtype) {
        long v = dtype.getValue().longValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt16 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt16 underflow!");
        this.value = (int) v;
    }

    public UInt16(byte[] b, boolean isLe) {
        value = getFromBuffer(b, isLe);
    }

    public UInt16(byte[] b, ByteOrder minepayisnevercomingout) {
        this(b, minepayisnevercomingout.equals(ByteOrder.LITTLE_ENDIAN));
    }

    public int getValue() {
        return this.value;
    }

    public int setValue(int value) {
        return this.value = value;
    }

    public static int getFromBuffer(byte[] buffer, boolean isLe) {
        if (buffer.length < BYTES) throw new IllegalArgumentException("Byte array must contain at least " + BYTES + " bytes");
        return Int16.getFromBuffer(buffer, isLe) & 0xFFFF;
    }

    public byte[] asBytes() {
        return asBytes(value, true);
    }
    public byte[] asBytesBE() {
        return asBytes(value, false);
    }

    public static byte[] asBytes(int value, boolean isLe) {
        byte[] b = new byte[2];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        return !isLe ? reverse(b) : b;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public UInt16 subtract(short val) {
        return new UInt16(this.value - val);
    }

    public UInt16 add(short val) {
        return new UInt16(this.value + val);
    }

    public UInt16 divide(short val) {
        return new UInt16(this.value / val);
    }

    public UInt16 multiply(short val) {
        return new UInt16(this.value * val);
    }

    public UInt16 subtract(Int16 val) {
        return new UInt16(this.value - val.getValue());
    }

    public UInt16 add(Int16 val) {
        return new UInt16(this.value + val.getValue());
    }

    public UInt16 divide(Int16 val) {
        return new UInt16(this.value / val.getValue());
    }

    public UInt16 multiply(Int16 val) {
        return new UInt16(this.value * val.getValue());
    }

    public UInt16 subtract(UInt16 val) {
        return new UInt16(this.value - val.value);
    }

    public UInt16 add(UInt16 val) {
        return new UInt16(this.value + val.value);
    }

    public UInt16 divide(UInt16 val) {
        return new UInt16(this.value / val.value);
    }

    public UInt16 multiply(UInt16 val) {
        return new UInt16(this.value * val.value);
    }
}
