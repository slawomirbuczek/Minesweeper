package com.minesweeper.client.model;

public class LoginResponse {

    private ResponseMessage responseMessage;
    private String JWT;

    public LoginResponse(ResponseMessage responseMessage, String JWT) {
        this.responseMessage = responseMessage;
        this.JWT = JWT;
    }

    public ResponseMessage getResponseMessage() {
        return responseMessage;
    }

    public String getJWT() {
        return JWT;
    }
}
