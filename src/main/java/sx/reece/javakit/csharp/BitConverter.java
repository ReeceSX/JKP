package sx.reece.javakit.csharp;
/*
    | Author: http://github.com/itsGhost | @itsNotRSX
    | Type: Work in progress
    | License: 
    |  Copyright (C) itsghost.me - MIT
*/

import sx.reece.javakit.datatypes.DataTypeEntity;
import sx.reece.javakit.datatypes.Int16;
import sx.reece.javakit.datatypes.Int32;
import sx.reece.javakit.modern.ModernString;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static sx.reece.javakit.utils.DataUtils.reverse;

public class BitConverter {
    public static boolean IS_LE = true; //C# doesn't allow you to change this but we do

    /**
     * Get bytes from data
     * @param data input data
     * @return
     */
    public static byte[] getBytes(int data, boolean le) {
        return Int32.asBytes(data, le);
    }

    /**
     * Get bytes from data
     * @param data input data
     * @return
     */
    public static byte[] getBytes(int data) {
        return getBytes(data, IS_LE);
    }

    /**
     * Get bytes from float
     * @param data input data
     * @return
     */
    public static byte[] getBytes(float data, boolean isLe) {
        return ByteBuffer.allocate(4).order(IS_LE ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN).putFloat(data).array();
    }

    /**
     * Get bytes from float
     * @param data input data
     * @return
     */
    public static byte[] getBytes(float data) {
        return getBytes(data, IS_LE);
    }

    /**
     * Get bytes from short
     * @param data input data
     * @return
     */
    public static byte[] getBytes(short data, boolean isLe) {
        return Int16.asBytes(data, isLe);
    }

    /**
     * Get bytes from short
     * @param data input data
     * @return
     */
    public static byte[] getBytes(short data) {
        return getBytes(data, IS_LE);
    }

    /**
     * Get bytes from long
     * @param data input data
     * @return
     */
    public static byte[] getBytes(long data, boolean isLe) {
        return ByteBuffer.allocate(8).order(IS_LE ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN).putLong(data).array();
    }

    /**
     * Get bytes from double
     * @param data input data
     * @return
     */
    public static byte[] getBytes(long data) {
        return getBytes(data, IS_LE);
    }

    /**
     * Get bytes from double
     * @param data input data
     * @return
     */
    public static byte[] getBytes(double data, boolean isLe) {
        return ByteBuffer.allocate(4).order(isLe ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN).putDouble(data).array();
    }

    /**
     * Get bytes from double
     * @param data input data
     * @return
     */
    public static byte[] getBytes(double data) {
        return getBytes(data, IS_LE);
    }

    /**
     * Get bytes from unsigned data type (object)
     * @param data input data
     * @return
     */
    public static byte[] getBytes(DataTypeEntity data) {
        return IS_LE ? data.asBytes() :reverse(data.asBytes());
    }

    /**
     * Support C# copypasta
     * @param str input data
     * @return
     */
    public static byte[] getBytes(String str) {
        return str.getBytes();
    }


    /**
     * Support C# copypasta
     * @param data input data
     * @return
     */
    @Deprecated
    public static byte[] GetBytes(int data) {
        return getBytes(data);
    }

    /**
     * Support C# copypasta
     * @param data input data
     * @return
     */
    @Deprecated
    public static byte[] GetBytes(float data) {
        return getBytes(data);
    }

    /**
     * Support C# copypasta
     * @param data input data
     * @return
     */
    @Deprecated
    public static byte[] GetBytes(short data) {
        return getBytes(data);
    }

    /**
     * Support C# copypasta
     * @param data input data
     * @return
     */
    @Deprecated
    public static byte[] GetBytes(double data) {
        return getBytes(data);
    }

    /**
     * Support C# copypasta
     * @param data input data
     * @return
     */
    @Deprecated
    public static byte[] GetBytes(long data) {
        return getBytes(data);
    }

    /**
     * Support C# copypasta
     * @param data input byte array
     * @return hex
     */
    @Deprecated
    public static String ToString(byte[] data) {
        return toString(data);
    }

    /**
     * Convert buffer to hex
     * @param data input byte array
     * @return hex
     */
    public static String toString(byte[] data) {
        return ModernString.join("-", data).toString();
    }
}
