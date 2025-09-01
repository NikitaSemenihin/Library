package com.semenihin.exceptions;

public class LBNotExistException extends RuntimeException {
    public LBNotExistException(String message) {
        super(message);
    }

    public LBNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public LBNotExistException(Throwable cause) {
        super(cause);
    }

    public LBNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LBNotExistException() {
    }
}
