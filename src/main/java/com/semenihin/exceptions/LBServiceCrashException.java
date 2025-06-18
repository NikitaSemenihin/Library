package com.semenihin.exceptions;

public class LBServiceCrashException extends RuntimeException {
    public LBServiceCrashException(String message) {
        super(message);
    }

    public LBServiceCrashException(Throwable cause) {
        super(cause);
    }
}
