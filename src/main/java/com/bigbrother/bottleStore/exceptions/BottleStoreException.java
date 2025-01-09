package com.bigbrother.bottleStore.exceptions;

public class BottleStoreException extends RuntimeException {
    public BottleStoreException(String message) {
        super(message);
    }

    public BottleStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
