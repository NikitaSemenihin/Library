package com.semenihin.exceptions;

import java.io.IOException;

public class LBFileAccessException extends IOException {
    public LBFileAccessException() {
    }

    public LBFileAccessException(String message) {
        super(message);
    }

    public LBFileAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public LBFileAccessException(Throwable cause) {
        super(cause);
    }
}
