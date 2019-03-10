package sx.reece.javakit.logger;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;

@Deprecated
public class WinANSI {
    private static boolean COMPLETED = false;
    public static void setup() {
        if (!(System.getProperty("os.name").contains("dows"))) return;
        if (COMPLETED) return;
        Kernel32 kernel = Kernel32.INSTANCE;
        IntByReference intRef = new IntByReference();

        WinNT.HANDLE hStdOut = kernel.GetStdHandle(-11);
        kernel.GetConsoleMode(hStdOut, intRef);
        kernel.SetConsoleMode(hStdOut, intRef.getValue() | 0x0004);

        COMPLETED = true;
    }
}
