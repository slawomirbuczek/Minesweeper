package com.pk.minesweeper.client.model;

public class LoginResponse {

    private final ResponseMessage responseMessage;
    private final String JWT;

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
