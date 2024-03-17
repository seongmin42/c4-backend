package com.hanwha.backend.exception.auth;

public class PasswordNotMatchException extends Exception{
    public PasswordNotMatchException(){
        super("Password not matches");
    }
}
