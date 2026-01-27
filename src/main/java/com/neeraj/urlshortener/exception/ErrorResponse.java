package com.neeraj.urlshortener.exception;
import java.time.LocalDateTime;

public class ErrorResponse {

    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
