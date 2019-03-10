package sx.reece.javakit.datatypes;

import java.nio.ByteOrder;

import static sx.reece.javakit.utils.ArrayUtils.reverse;

/**
 | Author: Reece Wilson (Reece.SX @not_rsx)
 | Type: Work in progress
 | License:
 |  Copyright (C) Reece.SX - MIT
 */
public class UInt32 implements DataTypeEntity {
    private long value;
    public static final long MAX_VALUE = 4294967295L;
    public static final long MIN_VALUE = 0L;
    public static final long BYTES = 4;

    public UInt32() {
    }
    public UInt32(short v) {
        this.value = v;
    }
    public UInt32(int v) {
        this.value = v;
    }
    public UInt32(long v) {
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt32 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt32 underflow!");
        this.value = v;
    }
    public UInt32(Int32 dtype) {
        long v = dtype.getValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt32 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt32 underflow!");
        this.value = v;
    }
    public UInt32(Int16 dtype) {
        long v = dtype.getValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt32 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt32 underflow!");
        this.value = v;
    }
    public UInt32(Int64 dtype) {
        long v = dtype.getValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt32 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt32 underflow!");
        this.value = v;
    }
    public UInt32(UInt32 dtype) {
        long v = dtype.getValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt32 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt32 underflow!");
        this.value = v;
    }
    public UInt32(UInt16 dtype) {
        long v = dtype.getValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt32 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt32 underflow!");
        this.value = v;
    }
    public UInt32(UInt64 dtype) {
        long v = dtype.getValue().longValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("UInt32 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("UInt32 underflow!");
        this.value = v;
    }

    public UInt32(byte[] b, boolean isLe) {
        value = getFromBuffer(b, isLe);
    }
    
    public UInt32(byte[] b, ByteOrder byteOrder) {
        this(b, byteOrder.equals(ByteOrder.LITTLE_ENDIAN));
    }

    public long getValue() {
        return this.value;
    }

    public long setValue(long value) {
        return this.value = value;
    }

    public static long getFromBuffer(byte[] buffer, boolean isLe) {
        if (buffer.length < BYTES) throw new IllegalArgumentException("Byte array must contain at least " + BYTES + " bytes");
        return Int32.getFromBuffer(buffer, isLe) & 0xFFFFFFFFL;
    }

    public byte[] asBytes() {
        return asBytes(value, true);
    }
    public byte[] asBytesBE() {
        return asBytes(value, false);
    }

    public static byte[] asBytes(long value, boolean isLe) {
        byte[] b = new byte[4];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        b[2] = (byte) (value >> 16 & 0xff);
        b[3] = (byte) (value >> 24 & 0xff);
        return !isLe ? reverse(b) : b;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public UInt32 subtract(short val) {
        return new UInt32(this.value - val);
    }

    public UInt32 add(short val) {
        return new UInt32(this.value + val);
    }

    public UInt32 divide(short val) {
        return new UInt32(this.value / val);
    }

    public UInt32 multiply(short val) {
        return new UInt32(this.value * val);
    }

    public UInt32 subtract(int val) {
        return new UInt32(this.value - val);
    }

    public UInt32 add(int val) {
        return new UInt32(this.value + val);
    }

    public UInt32 divide(int val) {
        return new UInt32(this.value / val);
    }

    public UInt32 multiply(int val) {
        return new UInt32(this.value * val);
    }

    public UInt32 subtract(Int32 val) {
        return new UInt32(this.value - val.getValue());
    }

    public UInt32 add(Int32 val) {
        return new UInt32(this.value + val.getValue());
    }

    public UInt32 divide(Int32 val) {
        return new UInt32(this.value / val.getValue());
    }

    public UInt32 multiply(Int32 val) {
        return new UInt32(this.value * val.getValue());
    }

    public UInt32 subtract(Int16 val) {
        return new UInt32(this.value - val.getValue());
    }

    public UInt32 add(Int16 val) {
        return new UInt32(this.value + val.getValue());
    }

    public UInt32 divide(Int16 val) {
        return new UInt32(this.value / val.getValue());
    }

    public UInt32 multiply(Int16 val) {
        return new UInt32(this.value * val.getValue());
    }

    public UInt32 subtract(UInt32 val) {
        return new UInt32(this.value - val.getValue());
    }

    public UInt32 add(UInt32 val) {
        return new UInt32(this.value + val.getValue());
    }

    public UInt32 divide(UInt32 val) {
        return new UInt32(this.value / val.getValue());
    }

    public UInt32 multiply(UInt32 val) {
        return new UInt32(this.value * val.getValue());
    }

    public UInt32 subtract(UInt16 val) {
        return new UInt32(this.value - val.getValue());
    }

    public UInt32 add(UInt16 val) {
        return new UInt32(this.value + val.getValue());
    }

    public UInt32 divide(UInt16 val) {
        return new UInt32(this.value / val.getValue());
    }

    public UInt32 multiply(UInt16 val) {
        return new UInt32(this.value * val.getValue());
    }
}
