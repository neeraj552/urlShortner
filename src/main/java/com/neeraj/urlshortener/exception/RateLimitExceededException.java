package com.neeraj.urlshortener.exception;

public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String message){
        super(message);
    }

}
