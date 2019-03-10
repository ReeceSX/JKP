package sx.reece.javakit.stream;

import java.io.IOException;

/**
 * Created by RSX on 26/02/2017.
 */
public class RuntimeIOException extends RuntimeException {
    public RuntimeIOException(String s) {
        super(s);
    }
    public RuntimeIOException(Exception s) {
        super(s);
    }
}
