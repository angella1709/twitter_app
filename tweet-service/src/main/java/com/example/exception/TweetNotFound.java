package com.example.exception;

public class TweetNotFound extends RuntimeException {

    public TweetNotFound(String message) {
        super(message);
    }

}
