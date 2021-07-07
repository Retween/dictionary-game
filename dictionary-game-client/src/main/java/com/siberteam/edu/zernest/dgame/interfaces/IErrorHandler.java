package com.siberteam.edu.zernest.dgame.interfaces;


public interface IErrorHandler extends ILogger {
    default void handleException(Exception e) {
        logError(e + "\n" + e.getCause());
        System.exit(1);
    }
}
