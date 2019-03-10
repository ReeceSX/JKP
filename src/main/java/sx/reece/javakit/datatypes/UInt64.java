package sx.reece.javakit.datatypes;

import java.math.BigInteger;

import static sx.reece.javakit.utils.DataUtils.reverse;

/**
 | Author: Reece Wilson (Reece.SX @not_rsx)
 | Type: Work in progress
 | License:
 |  Copyright (C) Reece.SX - MIT
 */
public class UInt64 implements DataTypeEntity {
    private BigInteger data;
    public static final BigInteger MAX_VALUE = new BigInteger("18446744073709551615");
    public static final BigInteger MIN_VALUE = new BigInteger("0");
    public static final long BYTES = 8;

    public UInt64() {}

    public UInt64(BigInteger v) {
        this.data = v;
    }
    public UInt64(String v) {
        this.data = new BigInteger(v);
    }
    public UInt64(Long v) {this.data = BigInteger.valueOf(v);}
    public UInt64(Integer v) {this.data = BigInteger.valueOf(v);}
    public UInt64(long v) {this.data = BigInteger.valueOf(v);}
    public UInt64(int v) {this.data = BigInteger.valueOf(v);}
    public UInt64(short v) {this.data = BigInteger.valueOf(v);}
    public UInt64(Int16 v) {this.data = BigInteger.valueOf(v.getValue());}
    public UInt64(Int32 v) {this.data = BigInteger.valueOf(v.getValue());}
    public UInt64(Int64 v) {this.data = BigInteger.valueOf(v.getValue());}
    public UInt64(UInt16 v) {this.data = BigInteger.valueOf(v.getValue());}
    public UInt64(UInt32 v) {this.data = BigInteger.valueOf(v.getValue());}
    public UInt64(byte[] v) {
        if (v.length < BYTES) throw new IllegalArgumentException("Byte array must contain at least " + BYTES + " bytes");
        this.data = new BigInteger(1, v);
        if (this.data.equals(new BigInteger("-1")))
            this.data = MAX_VALUE;
    }

    public BigInteger getValue() {
        return this.data;
    }

    public BigInteger setValue(BigInteger data) {
        return this.data = data;
    }

    public byte[] asBytes() {
        return asBytes(data, true);
    }

    public static byte[] asBytes(BigInteger data, boolean isLe) {
        byte[] b = new byte[8];
        BigInteger bigInt = new BigInteger("255");
        for (int i = 0; i < 8; i++) {
            b[i] = (data.shiftRight((7 - i) * 8).and(bigInt).byteValue());
        }
        return isLe ? reverse(b) : b;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }

    public UInt64 subtract(int val) {
        return new UInt64(this.data.subtract(BigInteger.valueOf(val)));
    }

    public UInt64 add(int val) {
        return new UInt64(this.data.add(BigInteger.valueOf(val)));
    }

    public UInt64 divide(int val) {
        return new UInt64(this.data.divide(BigInteger.valueOf(val)));
    }

    public UInt64 multiply(int val) {
        return new UInt64(this.data.multiply(BigInteger.valueOf(val)));
    }

    public UInt64 subtract(long val) {
        return new UInt64(this.data.subtract(BigInteger.valueOf(val)));
    }

    public UInt64 add(long val) {
        return new UInt64(this.data.add(BigInteger.valueOf(val)));
    }

    public UInt64 divide(long val) {
        return new UInt64(this.data.divide(BigInteger.valueOf(val)));
    }

    public UInt64 multiply(long val) {
        return new UInt64(this.data.multiply(BigInteger.valueOf(val)));
    }

    public UInt64 subtract(BigInteger val) {
        return new UInt64(this.data.subtract(val));
    }

    public UInt64 add(BigInteger val) {
        return new UInt64(this.data.add(val));
    }

    public UInt64 divide(BigInteger val) {
        return new UInt64(this.data.divide(val));
    }

    public UInt64 multiply(BigInteger val) {
        return new UInt64(this.data.multiply(val));
    }

    public UInt64 subtract(UInt64 val) {
        return new UInt64(this.data.subtract(val.getValue()));
    }

    public UInt64 add(UInt64 val) {
        return new UInt64(this.data.add(val.getValue()));
    }

    public UInt64 divide(UInt64 val) {
        return new UInt64(this.data.divide(val.getValue()));
    }

    public UInt64 multiply(UInt64 val) {
        return new UInt64(this.data.multiply(val.getValue()));
    }

    public UInt64 subtract(UInt32 val) {
        return new UInt64(this.data.subtract(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 add(UInt32 val) {
        return new UInt64(this.data.add(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 divide(UInt32 val) {
        return new UInt64(this.data.divide(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 multiply(UInt32 val) {
        return new UInt64(this.data.multiply(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 subtract(UInt16 val) {
        return new UInt64(this.data.subtract(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 add(UInt16 val) {
        return new UInt64(this.data.add(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 divide(UInt16 val) {
        return new UInt64(this.data.divide(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 multiply(UInt16 val) {
        return new UInt64(this.data.multiply(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 subtract(Int64 val) {
        return new UInt64(this.data.subtract(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 add(Int64 val) {
        return new UInt64(this.data.add(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 divide(Int64 val) {
        return new UInt64(this.data.divide(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 multiply(Int64 val) {
        return new UInt64(this.data.multiply(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 subtract(Int32 val) {
        return new UInt64(this.data.subtract(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 add(Int32 val) {
        return new UInt64(this.data.add(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 divide(Int32 val) {
        return new UInt64(this.data.divide(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 multiply(Int32 val) {
        return new UInt64(this.data.multiply(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 subtract(Int16 val) {
        return new UInt64(this.data.subtract(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 add(Int16 val) {
        return new UInt64(this.data.add(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 divide(Int16 val) {
        return new UInt64(this.data.divide(BigInteger.valueOf(val.getValue())));
    }

    public UInt64 multiply(Int16 val) {
        return new UInt64(this.data.multiply(BigInteger.valueOf(val.getValue())));
    }
}
