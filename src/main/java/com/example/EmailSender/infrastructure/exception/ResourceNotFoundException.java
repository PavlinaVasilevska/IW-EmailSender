package com.example.EmailSender.infrastructure.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super();
    }
    private static final long serialVersionUID = 1L;


    public ResourceNotFoundException(String message){
        super(message);
    }

}
