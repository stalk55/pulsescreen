package com.netflix.demo.exception;

public class InvalidRoleException extends RuntimeException{

    public InvalidRoleException(String message){
        super(message);
    }

}
