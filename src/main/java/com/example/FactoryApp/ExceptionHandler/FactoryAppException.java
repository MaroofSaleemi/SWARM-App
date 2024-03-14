package com.example.FactoryApp.ExceptionHandler;

public class FactoryAppException extends RuntimeException {
    public FactoryAppException(FactoryAppExceptionEnum exceptions) {
        super(exceptions.name());
    }
}
