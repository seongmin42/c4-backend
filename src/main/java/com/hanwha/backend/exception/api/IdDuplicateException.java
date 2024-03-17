package com.hanwha.backend.exception.api;

public class IdDuplicateException extends Exception{

    public IdDuplicateException(){
        super("User id is duplicated.");
    }
}
