package com.transdev.mobiservices.exception;

public class InsufficentSeatsException extends RuntimeException{

    public InsufficentSeatsException(String message) {
        super(message);
    }
}
