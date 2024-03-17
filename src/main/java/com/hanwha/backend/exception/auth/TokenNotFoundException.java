package com.hanwha.backend.exception.auth;

public class TokenNotFoundException extends RuntimeException{
    public TokenNotFoundException(){
        super("Token is not found.");
    }
}
