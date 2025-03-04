package org.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private static Logger getLogger() {
        String className = new Throwable().getStackTrace()[2].getClassName();
        return LoggerFactory.getLogger(className);
    }

    public static void debug(String msg, Object... args) {
        getLogger().debug(msg, args);
    }

    public static void info(String msg, Object... args) {
        getLogger().info(msg, args);
    }

    public static void warn(String msg, Object... args) {
        getLogger().warn(msg, args);
    }

    public static void error(String msg, Object... args) {
        getLogger().error(msg, args);
    }
}