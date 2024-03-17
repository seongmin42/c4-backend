package com.hanwha.backend.exception.api;

public class IdNotFoundException extends Exception{
    public IdNotFoundException() {
        super("User's id is not found.");
    }
}
