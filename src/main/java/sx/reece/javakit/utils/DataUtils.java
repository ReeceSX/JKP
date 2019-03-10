package sx.reece.javakit.utils;
/*
    | Author: http://github.com/itsGhost | @itsNotRSX
    | Type: Work in progress
    | License: 
    |  Copyright (C) itsghost.me - MIT
*/

import javax.xml.bind.DatatypeConverter;

public class DataUtils {
    public static String getHex(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

    public static byte[] getBytes(String hex) {
        return DatatypeConverter.parseHexBinary(hex.replace(":", "").replace("-", "").replaceAll(" ", ""));
    }

    @Deprecated
    public static byte[] reverse(byte[] data) {
        return ArrayUtils.reverse(data);
    }

    @Deprecated
    public static Object[] reverse(Object[] data) {
        return ArrayUtils.reverse(data);
    }
}