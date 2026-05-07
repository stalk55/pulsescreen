package com.netflix.demo.exception;

public class EmailSendingException extends RuntimeException{

    public EmailSendingException(String message,Throwable cause){
        super(message, cause);
    }

}
