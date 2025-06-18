package com.semenihin.exceptions;

public class LBDaoCrashException extends RuntimeException {
    public LBDaoCrashException(String message) {
        super(message);
    }

    public LBDaoCrashException(Throwable cause) {
        super(cause);
    }
}
