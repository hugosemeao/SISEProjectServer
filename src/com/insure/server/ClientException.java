package com.insure.server;

public class ClientException extends Exception {
    private String message;

    public ClientException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
