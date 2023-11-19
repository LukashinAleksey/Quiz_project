package com.lukashin.quiz.Exeption;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String massage){
        super(massage);
    }
}
