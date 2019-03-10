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
public class Int64 implements DataTypeEntity {
    private long value;
    public static final long MAX_VALUE = Long.MAX_VALUE;
    public static final long MIN_VALUE = Long.MIN_VALUE;
    public static final long BYTES = 8;

    public Int64() {
    }
    public Int64(long v) {
        this.value = v;
    }
    public Int64(int v) {
        this.value = v;
    }
    public Int64(short v) {
        this.value = v;
    }
    public Int64(Int32 dtype) {
        this.value = dtype.getValue();
    }
    public Int64(Int16 dtype) {
        this.value = dtype.getValue();
    }
    public Int64(Int64 dtype) {
        this.value = dtype.getValue();
    }
    public Int64(UInt32 dtype) {
        this.value = dtype.getValue();
    }
    public Int64(UInt16 dtype) {
        this.value = dtype.getValue();
    }
    public Int64(UInt64 dtype) {
        //TODO: check
        ///long v = dtype.getValue().();
        ///if (v > MAX_VALUE) throw new IllegalArgumentException("UInt16 overflow!");
        ///if (v < MIN_VALUE) throw new IllegalArgumentException("Int64 underflow!");
        this.value = dtype.getValue().longValue();
    }

    public Int64(byte[] b, boolean isLe) {
        value = getFromBuffer(b, isLe);//ByteBuffer.wrap(b).order(isLe ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN).getInt();
    }

    public Int64(byte[] b, ByteOrder order) {
        this(b, order.equals(ByteOrder.LITTLE_ENDIAN));
    }

    public long getValue() {
        return this.value;
    }

    public long setValue(int value) {
        return this.value = value;
    }

    public static long getFromBuffer(byte[] data, boolean isLe) {
        if (data.length < BYTES) throw new IllegalArgumentException("Byte array must contain at least " + BYTES + " bytes");
        if (!isLe)
            data = DataUtils.reverse(data);
        return (data[0] & 0xFF)
                | (((long) data[1] & 0xFF) << 8)
                | (((long) data[2] & 0xFF) << 16)
                | (((long) data[3] & 0xFF) << 24)
                | (((long) data[4] & 0xFF) << 32)
                | (((long) data[5] & 0xFF) << 40)
                | (((long) data[6] & 0xFF) << 48)
                | (((long) data[7] & 0xFF) << 56);
    }

    public byte[] asBytes() {
        return asBytes(value, true);
    }
    public byte[] asBytesBE() {
        return asBytes(value, false);
    }

    public static byte[] asBytes(long value, boolean isLe) {
        byte[] b = new byte[8];
        b[0] = (byte) ((value & 0x00000000000000FFL));
        b[1] = (byte) ((value & 0x000000000000FF00L) >> 8);
        b[2] = (byte) ((value & 0x0000000000FF0000L) >> 16);
        b[3] = (byte) ((value & 0x00000000FF000000L) >> 24);
        b[4] = (byte) ((value & 0x000000FF00000000L) >> 32);
        b[5] = (byte) ((value & 0x0000FF0000000000L) >> 40);
        b[6] = (byte) ((value & 0x00FF000000000000L) >> 48);
        b[7] = (byte) ((value & 0xFF00000000000000L) >> 56);
        return !isLe ? reverse(b) : b;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public Int64 subtract(long val) {
        return new Int64(this.value - val);
    }

    public Int64 add(long val) {
        return new Int64(this.value + val);
    }

    public Int64 divide(long val) {
        return new Int64(this.value / val);
    }

    public Int64 multiply(long val) {
        return new Int64(this.value * val);
    }

    public Int64 subtract(Int16 val) {
        return new Int64(this.value - val.getValue());
    }

    public Int64 add(Int16 val) {
        return new Int64(this.value + val.getValue());
    }

    public Int64 divide(Int16 val) {
        return new Int64(this.value / val.getValue());
    }

    public Int64 multiply(Int16 val) {
        return new Int64(this.value * val.getValue());
    }

    public Int64 subtract(Int32 val) {
        return new Int64(this.value - val.getValue());
    }

    public Int64 add(Int32 val) {
        return new Int64(this.value + val.getValue());
    }

    public Int64 divide(Int32 val) {
        return new Int64(this.value / val.getValue());
    }

    public Int64 multiply(Int32 val) {
        return new Int64(this.value * val.getValue());
    }

    public Int64 subtract(Int64 val) {
        return new Int64(this.value - val.value);
    }

    public Int64 add(Int64 val) {
        return new Int64(this.value + val.value);
    }

    public Int64 divide(Int64 val) {
        return new Int64(this.value / val.value);
    }

    public Int64 multiply(Int64 val) {
        return new Int64(this.value * val.value);
    }
}
