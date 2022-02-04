package com.cinamidea.natour_2022.util;

public final class ResponseHTTP {

    private String message;

    public ResponseHTTP() {
    }

    public ResponseHTTP(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
