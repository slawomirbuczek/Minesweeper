package com.pk.minesweeper.client.models;

public class ResponseMessage {

    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage() {
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
