package com.semenihin.exceptions;

public class LBMapperException extends RuntimeException {
    public LBMapperException(String message) {
        super(message);
    }

    public LBMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public LBMapperException(Throwable cause) {
        super(cause);
    }

    public LBMapperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LBMapperException() {
    }
}
