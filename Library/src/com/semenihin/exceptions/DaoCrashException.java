package com.semenihin.exceptions;

import java.io.FileNotFoundException;

public class DaoCrashException extends Error {
    public DaoCrashException(String message) {
        super(message);
    }

    public DaoCrashException(Throwable cause) {
        super(cause);
    }
}
