package com.semenihin.exceptions;

public class LBInvalidEntityException extends RuntimeException {
    public LBInvalidEntityException(String message) {
        super(message);
    }

    public LBInvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public LBInvalidEntityException(Throwable cause) {
        super(cause);
    }

    public LBInvalidEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LBInvalidEntityException() {
    }
}
