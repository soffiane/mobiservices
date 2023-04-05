package com.transdev.mobiservices.exception;

public class BusNotFoundException extends RuntimeException{
    public BusNotFoundException(Long id) {
        super("Could not find bus "+id);
    }
}
