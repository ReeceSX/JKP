package sx.reece.javakit.csharp;
/*
    | Author: http://github.com/itsGhost | @itsNotRSX
    | Type: Work in progress
    | License: 
    |  Copyright (C) itsghost.me - MIT
*/

import sx.reece.javakit.logger.WinANSI;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Console {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Write line to console
     * @param str data to print
     * @return
     */
    public static void printLine(String str) {
        System.out.println(str);
    }

    /**
     * Write to console
     * @param str data to print
     * @return
     */
    public static void print(String str) {
        System.out.print(str);
    }

    /**
     * Write hex dump to console
     * @param data bytes
     * @return
     */
    public static void print(byte[] data) {
        System.out.print(BitConverter.toString(data));
    }

    /**
     * Write hex dump to console (and return)
     * @param data bytes
     * @return
     */
    public static void printLine(byte[] data) {
        System.out.println(BitConverter.toString(data));
    }

    /**
     * Safely read line from the console (nothing if failed)
     * @return
     */
    public static String readLine() {
        try {
            return br.readLine();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Support C# copypasta
     * @return
     */
    @Deprecated
    public static void WriteLine(String str) {
        printLine(str);
    }

    /**
     * Support C# copypasta
     * @return
     */
    @Deprecated
    public static void Write(String str) {
        print(str);
    }

    /**
     * Support C# copypasta
     * @return
     */
    @Deprecated
    public static void Print(String str) {
        print(str);
    }

    /**
     * Support C# copypasta
     * @return
     */
    @Deprecated
    public static String ReadLine(String str) {
        return readLine();
    }

}
