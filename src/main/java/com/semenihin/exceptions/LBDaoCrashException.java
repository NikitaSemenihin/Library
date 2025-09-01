package com.semenihin.exceptions;

public class LBDaoCrashException extends RuntimeException {
    public LBDaoCrashException(String message) {
        super(message);
    }

    public LBDaoCrashException(String message, Throwable cause) {
        super(message, cause);
    }

    public LBDaoCrashException() {
    }

    public LBDaoCrashException(Throwable cause) {
        super(cause);
    }

    public LBDaoCrashException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
