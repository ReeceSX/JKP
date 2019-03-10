package sx.reece.javakit.logger;

import sx.reece.javakit.csharp.BitConverter;
import sx.reece.javakit.modern.ModernFile;
import sx.reece.javakit.utils.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 | Author: Reece Wilson (Reece.SX @not_rsx)
 | Type: Work in progress
 | License:
 |  Copyright (C) Reece.SX - MIT
 */
public class Logger {
    public static boolean useColor = true;
    public static boolean disableDebug = false;
    public static boolean disableError = false;
    public static boolean disableLog = false;
    public static boolean disableWarn = false;
    public static boolean useFileLogs = true;
    private static StringBuilder sb = new StringBuilder();

    private synchronized static void send(Object in, int color, String format, Object... args) {
        String instr = parseObject(in);
        String destroyColor = !useColor ? "" : (char) 27 + "[0m";                                                       //reset
        String strColor = !useColor ? "" : String.format("%s[%sm", (char) 27, color);                                   //color tag
        String prefix = strColor + format;                                                                              //append the color to the start of each line
        String message = String.format(instr, in.toString().contains("{}") ? toArrStr(args) : args);                    //parse args

        message = prefix + message.replaceAll("\r", "").replaceAll("\n", destroyColor + "\r\n" + prefix);               //replace new lines with the prefix

        System.out.println(message + destroyColor);                                                                     //print

        if (!useFileLogs) return;

        if (sb.length() > Integer.MAX_VALUE / 2) {
            System.out.println("WARN: BUFFER FULL. (resetting...)");
            sb = new StringBuilder();
        }

        if (useColor)
            sb.append(message.replace(strColor, "").replace(destroyColor, "")).append("\r\n");                          //strip down the color stuff and throw it in the log
        else
            sb.append(message);
    }

    //Sometimes Object->toString isn't enough
    private static String parseObject(Object object) {
        if (object instanceof Byte[])
            return BitConverter.toString(ArrayUtils.toPrimitive((Byte[]) object));

        if (object instanceof byte[])
            return BitConverter.toString((byte[]) object);

        if (object instanceof Socket)
            return ((Socket) object).getRemoteSocketAddress().toString() + " > " + ((Socket) object).getLocalAddress().toString();

        if (object instanceof File)
            return ((File) object).getAbsolutePath();

        return object.toString().replace("{}", "%s");
    }

    private static String[] toArrStr(Object[] obj) {
        String[] ret = new String[obj.length];
        for (int i = 0; i < obj.length; i++)
            ret[i] = parseObject(obj[i]);
        return ret;
    }

    public static void log(Object in, Object... args) {
        if (disableLog) return;
        send(in, 32, "[Log]    " + getTime() + "> ", args);
    }

    public static void warn(Object in, Object... args) {
        if (disableWarn) return;
        send(in, 31, "[Warn]   " + getTime() + "> ", args);
    }

    public static void error(Object in, Object... args) {
        if (disableError) return;
        send(in, 31, "[ERROR]  " + getTime() + "> ", args);
    }

    public static void debug(Object in, Object... args) {
        if (System.getProperty("nodebug") != null) return;
        if (disableDebug) return;
        send(in, 33, "[Debug]  " + getTime() + "> ", args);
    }

    private static String getTime() {
        return new SimpleDateFormat("hh:mm:ss").format(new Date());
    }

    private static String getDate() {
        return new SimpleDateFormat("dd MM - hh mm ss").format(new Date());
    }

    static {
        if (useFileLogs)
            Runtime.getRuntime().addShutdownHook(new Thread(() -> new ModernFile("./logs/" + getDate() + ".txt").writeAllString(sb.toString())));
    }

    static {
        System.setErr(new PrintStream(new OutputStream() {
            private StringBuilder bb = new StringBuilder();
            @Override
            public void write(int b) throws IOException {
                char ch = (char) b;
                if (bb.length() == 0 && ch == '\r') {
                    System.out.print("\r");
                    return;
                }
                if (ch == '\n') {
                    Logger.error(bb);
                    bb = new StringBuilder();
                } else {
                    bb.append(ch);
                }
            }
        }));
    }

    static {
		// 2018 update: this was used to prevent the hotspot from attempting to load JNA, if it is not present. 
        boolean windows = System.getProperty("os.name").contains("dows");
        if (windows)
            try {
            Class<?> clazz;
                if (null != (clazz = Class.forName("com.sun.jna.platform.win32.Kernel32")))
                    Class.forName("sx.reece.javakit.logger.WinANSI").getMethods()[0].invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    static {
        JavaLogger.install();
    }
}