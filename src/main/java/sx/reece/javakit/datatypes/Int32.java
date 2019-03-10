package sx.reece.javakit.datatypes;

import sx.reece.javakit.utils.DataUtils;

import java.nio.ByteOrder;

import static sx.reece.javakit.utils.DataUtils.reverse;

/**
 | Author: Reece Wilson (Reece.SX @not_rsx)
 | Type: Work in progress
 | License:
 |  Copyright (C) Reece.SX - MIT
 */
public class Int32 implements DataTypeEntity {
    private int value;
    public static final int MAX_VALUE = 2147483647;
    public static final int MIN_VALUE = -2147483648;
    public static final long BYTES = 4;

    public Int32() {
    }
    public Int32(short v) {
        this.value = v;
    }
    public Int32(int v) {
        this.value = v;
    }
    public Int32(long v) {
        if (v > MAX_VALUE) throw new IllegalArgumentException("Int32 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("Int32 underflow!");
        this.value = (int) v;
    }
    public Int32(Int32 dtype) {
        this.value = dtype.getValue();
    }
    public Int32(Int16 dtype) {
        this.value = dtype.getValue();
    }
    public Int32(Int64 dtype) {
        long v = dtype.getValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("Int32 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("Int32 underflow!");
        this.value = (int) v;
    }
    public Int32(UInt32 dtype) {
        long v = dtype.getValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("Int32 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("Int32 underflow!");
        this.value = (int) v;
    }
    public Int32(UInt16 dtype) {
        this.value = dtype.getValue();
    }
    public Int32(UInt64 dtype) {
        long v = dtype.getValue().longValue();
        if (v > MAX_VALUE) throw new IllegalArgumentException("Int32 overflow!");
        if (v < MIN_VALUE) throw new IllegalArgumentException("Int32 underflow!");
        this.value = (int) v;
    }

    public Int32(byte[] b, boolean isLe) {
        value = getFromBuffer(b, isLe);//ByteBuffer.wrap(b).order(isLe ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN).getInt();
    }

    public Int32(byte[] b, ByteOrder order) {
        this(b, order.equals(ByteOrder.LITTLE_ENDIAN));
    }

    public int getValue() {
        return this.value;
    }

    public int setValue(int value) {
        return this.value = value;
    }

    public static int getFromBuffer(byte[] data, boolean isLe) {
        if (data.length < BYTES) throw new IllegalArgumentException("Byte array must contain at least " + BYTES + " bytes");
        if (!isLe)
            data = DataUtils.reverse(data);
        return (data[0] & 0xFF)
                | ((data[1] & 0xFF) << 8)
                | ((data[2] & 0xFF) << 16)
                | ((data[3] & 0xFF) << 24);
    }

    public byte[] asBytes() {
        return asBytes(value, true);
    }
    public byte[] asBytesBE() {
        return asBytes(value, false);
    }

    public static byte[] asBytes(int value, boolean isLe) {
        byte[] b = new byte[4];
        b[0] = (byte) ((value & 0x000000FF));
        b[1] = (byte) ((value & 0x0000FF00) >> 8);
        b[2] = (byte) ((value & 0x00FF0000) >> 16);
        b[3] = (byte) ((value & 0xFF000000) >> 24);
        return !isLe ? reverse(b) : b;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public Int32 subtract(short val) {
        return new Int32(this.value - val);
    }

    public Int32 add(short val) {
        return new Int32(this.value + val);
    }

    public Int32 divide(short val) {
        return new Int32(this.value / val);
    }

    public Int32 multiply(short val) {
        return new Int32(this.value * val);
    }

    public Int32 subtract(int val) {
        return new Int32(this.value - val);
    }

    public Int32 add(int val) {
        return new Int32(this.value + val);
    }

    public Int32 divide(int val) {
        return new Int32(this.value / val);
    }

    public Int32 multiply(int val) {
        return new Int32(this.value * val);
    }

    public Int32 subtract(Int32 val) {
        return new Int32(this.value - val.value);
    }

    public Int32 add(Int32 val) {
        return new Int32(this.value + val.value);
    }

    public Int32 divide(Int32 val) {
        return new Int32(this.value / val.value);
    }

    public Int32 multiply(Int32 val) {
        return new Int32(this.value * val.value);
    }

    public Int32 subtract(Int16 val) {
        return new Int32(this.value - val.getValue());
    }

    public Int32 add(Int16 val) {
        return new Int32(this.value + val.getValue());
    }

    public Int32 divide(Int16 val) {
        return new Int32(this.value / val.getValue());
    }

    public Int32 multiply(Int16 val) {
        return new Int32(this.value * val.getValue());
    }

    public Int32 subtract(UInt32 val) {
        return new Int32(this.value - val.getValue());
    }

    public Int32 add(UInt32 val) {
        return new Int32(this.value + val.getValue());
    }

    public Int32 divide(UInt32 val) {
        return new Int32(this.value / val.getValue());
    }

    public Int32 multiply(UInt32 val) {
        return new Int32(this.value * val.getValue());
    }

    public Int32 subtract(UInt16 val) {
        return new Int32(this.value - val.getValue());
    }

    public Int32 add(UInt16 val) {
        return new Int32(this.value + val.getValue());
    }

    public Int32 divide(UInt16 val) {
        return new Int32(this.value / val.getValue());
    }

    public Int32 multiply(UInt16 val) {
        return new Int32(this.value * val.getValue());
    }
}
