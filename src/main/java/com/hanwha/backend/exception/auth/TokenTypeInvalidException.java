package com.hanwha.backend.exception.auth;

public class TokenTypeInvalidException extends RuntimeException{
    public TokenTypeInvalidException() {
        super("Token type is invalid.");
    }
}
