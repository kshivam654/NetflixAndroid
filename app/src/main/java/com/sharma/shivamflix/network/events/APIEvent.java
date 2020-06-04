package com.sharma.shivamflix.network.events;

public class APIEvent {
    private String message;
    private int errorCode;
    public APIEvent(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
