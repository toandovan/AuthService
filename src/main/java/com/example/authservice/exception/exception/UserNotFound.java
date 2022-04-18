package com.example.authservice.exception.exception;

public class UserNotFound extends Exception{
    public  UserNotFound() {
        super("No data found");
    }
}
