package com.siberteam.edu.zernest.dgame.interfaces;

public interface ILogger {
    default void log(String message) {
        System.out.println(message);
    }

    default void logError(String message) {
        System.err.println(message);
    }
}
