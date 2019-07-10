package com.github.kazuhito_m.mysample.domain.basic;

public class DataNotExistsException extends RuntimeException {
    public DataNotExistsException(String message) {
        super(message);
    }

    public DataNotExistsException() {
        super();
    }
}
