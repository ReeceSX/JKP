package sx.reece.javakit.logger;

import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 | Author: Reece Wilson (Reece.SX @not_rsx)
 | Type: Work in progress
 | License:
 |  Copyright (C) Reece.SX - MIT
 */
public class JavaLogger extends java.util.logging.Logger {

    /**
     * Protected method to construct a logger for a named subsystem.
     * <p>
     * The logger will be initially configured with a null Level
     * and with useParentHandlers set to true.
     *
     * @param name               A name for the logger.  This should
     *                           be a dot-separated name and should normally
     *                           be based on the package name or class name
     *                           of the subsystem, such as java.net
     *                           or javax.swing.  It may be null for anonymous Loggers.
     * @param resourceBundleName name of ResourceBundle to be used for localizing
     *                           messages for this logger.  May be null if none
     *                           of the messages require localization.
     *                                  no corresponding resource can be found.
     */
    protected JavaLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public static void install() {
        LogManager.getLogManager().addLogger(new JavaLogger("jkit", null));
    }

    @Override
    public void log(Level level, String msg, Throwable thrown) {
        log(level, msg);
        thrown.printStackTrace();
    }

    @Override
    public void log(Level level, String msg, Object[] params) {
        if (Level.ALL == level)
            Logger.log(msg, params);
        if (Level.CONFIG == level)
            Logger.log(msg, params);
        if (Level.FINE == level)
            Logger.log(msg, params);
        if (Level.FINER == level)
            Logger.log(msg, params);
        if (Level.FINEST == level)
            Logger.log(msg, params);
        if (Level.INFO == level)
            Logger.log(msg, params);
        if (Level.SEVERE == level)
            Logger.error(msg, params);
        if (Level.WARNING == level)
            Logger.warn(msg, params);
    }

    @Override
    public void log(Level level, String msg, Object param1) {
        log(level, msg, new Object[]{param1});
    }

    @Override
    public void log(Level level, String msg) {
        log(level, msg, new Object[0]);
    }

}
