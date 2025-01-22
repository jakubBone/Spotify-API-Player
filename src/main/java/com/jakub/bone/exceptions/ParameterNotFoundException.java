package com.jakub.bone.exceptions;

public class ParameterNotFoundException extends RuntimeException {
    public ParameterNotFoundException(String message) {
        super(message);
    }

    public ParameterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
