package com.extensivelyscrum.backend.exception;

public class InvalidEmailFormatException extends RuntimeException{
    public InvalidEmailFormatException (String msg) {
        super(msg);
    }
}
